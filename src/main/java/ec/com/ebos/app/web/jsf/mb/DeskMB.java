package ec.com.ebos.app.web.jsf.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import lombok.Setter;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.seguridad.model.RolOpcion;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.type.KeyFrame;

/**
 * ManagedBean que maneja el menuOptions y los dialogs
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = DeskMB.BEAN_NAME)
@SessionScoped
public class DeskMB implements Serializable{

	private static final long serialVersionUID = 7101283517831219515L;

	public static final String BEAN_NAME = "deskMB";
	
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	@Setter
    @ManagedProperty(value = SessionMB.EL_BEAN_NAME)
    private SessionMB sessionMB;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	//private Long activeOptionId;
	
	private List<KeyFrame> pngFrameList;
	
	@Setter
	private HtmlPanelGroup pngFrames; 

	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String FRAME_SUFFIX = "_fra";
	private String WIDGET_PREFIX = "wgtOption_";
	private String FRAME_RESOURCE = "frames.xhtml";
	private String PNGFRAME_SUFFIX = "_png";
		
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
    	pngFrames = new HtmlPanelGroup();
    	pngFrameList = new ArrayList<KeyFrame>();
    }
	
    @SuppressWarnings("el-syntax")
    private void buildMenuModel() { //TODO (epa): Optimizar con Mapas y hacerlo recursivo, con N niveles
    	rolOptionList = sessionMB.getRolOpcionList();
    	//Construye arbol de opciones
        for (RolOpcion rolOpcion : rolOptionList) {
            Submenu submenu = new Submenu();
            Opcion modulo = rolOpcion.getOpcion();
            if (modulo.getPadre() == null) {
                submenu.setLabel(modulo.getEtiqueta());
                String iconoModulo = modulo.getIcono();
                if(iconoModulo != null && !iconoModulo.isEmpty()){
                	submenu.setStyle("background-image:url('resources/images/" + iconoModulo + "') !important;"
                            + "background-repeat: no-repeat;"
                            + "background-position: center left;"
                            + "padding: 0px 0px 0px 20px");                	
                }

                for (RolOpcion pantalla : rolOptionList) {
                    Opcion pantallaOp = pantalla.getOpcion();
                    
                    if (pantallaOp.getPadre() != null) {
                        if (pantallaOp.getPadre() == modulo) {
                            MenuItem item = new MenuItem();                                                                              
                            item.setValue(pantallaOp.getEtiqueta());
                            //item.setOnclick("openOption(([{name:'optionId', value:"+pantallaOp.getId()+"}]));");
                            String actionExpression = String.format("#{%s.openOption('%d')}",BEAN_NAME,pantallaOp.getId());
                            item.setActionExpression(FacesUtils.createMethodExpression(actionExpression, null, Long.class));
                            String iconoPantalla = pantallaOp.getIcono();
                            if(iconoPantalla != null && !iconoPantalla.isEmpty()){
                            	item.setStyle("background-image:url('resources/images/" + iconoPantalla + "') !important;"
                                        + "background-repeat: no-repeat;"
                                        + "background-position: center left;"
                                        + "padding: 0px 0px 0px 20px");
                            }
                                
                            submenu.getChildren().add(item);
                        }
                    }
                }
                menuModel.addSubmenu(submenu);
            }
        }
    }
    
    public MenuModel getMenuModel(){
		if(menuModel.getContents().isEmpty()){
			buildMenuModel();		
		}
    	return menuModel;
	}
    
    private void buildPngFrameList(){
    	pngFrames.getChildren().clear();
    	int maxOptions = sessionMB.getUsuario().getMaxOptions();
    	for (int i = 0; i < maxOptions; i++) {
    		HtmlPanelGroup png = new HtmlPanelGroup();
    		png.setId(FacesUtils.getRandomId());
    		pngFrames.getChildren().add(png);
		}
    }
    
    public HtmlPanelGroup getPngFrames(){
		if(pngFrames.getChildren().isEmpty()){
			buildPngFrameList();	
		}
    	return pngFrames;
	}
    
	//@SuppressWarnings("rawtypes")
	public void openOption(Long optionId) throws IOException{
    	FacesContext context = FacesContext.getCurrentInstance();
    	
//    	//Get parameter optionId from dlgMenu
//	    Map map = context.getExternalContext().getRequestParameterMap();
//	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    
	    //Selection option entity from rolOptionList
    	Opcion option = null;
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(optionId)){
    			option = opcionTMP;
    			break;
    		}
    	}
    	
    	//Create and put CompositeComponente Dialog into #pnlDialogs
    	String pngFrameId = getFrameId();
    	UIComponent pngFrame = context.getViewRoot().findComponent(pngFrameId);
    	if(option != null && pngFrame != null){
    		
    		Map<String, Object> attrs = new TreeMap<String, Object>();
    		//attrs.put("id","dlgOption_"+option.getId());
    		attrs.put("widgetVar", WIDGET_PREFIX+option.getId());
            attrs.put("src", option.getTarget());
            //attrs.put("onHide", "closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            //attrs.put("onShow", "changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            attrs.put("header", option.getEtiqueta());
            attrs.put("update", ":"+pngFrame.getId());
    		FacesUtils.includeCompositeComponent(context, pngFrame, COMPONENT_LIBRARY, FRAME_RESOURCE, pngFrameId+FRAME_SUFFIX, attrs);
    		RequestContext.getCurrentInstance().update(pngFrameId);
    	}
    	
    }
	
	/**
	 * Remove frame from pngFrame parent 
	 * 
	 * @param event Frame
	 */
	public void closeFrame(CloseEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		String frameId = event.getComponent().getId();
		String pngframeId = frameId.substring(0, frameId.indexOf(FRAME_SUFFIX)-1);
		UIComponent pngFrame = context.getViewRoot().findComponent(pngframeId);
		pngFrame.getChildren().clear();
    }
	
	private String getFrameId(){
		for (UIComponent png : pngFrames.getChildren()) {			
			if(png.getChildren().isEmpty()){
				return png.getId();
			}
		}
		sessionMB.putSuccess("Solo se pueden abrir "+sessionMB.getUsuario().getMaxOptions()+" opciones a la vez");//TODO (epa): internacionalizar
		return null;
	}
	 
}

