package ec.com.ebos.master.web.jsf.bean;

import java.io.IOException;
import java.io.Serializable;
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

import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.util.FacesUtils;

/**
 * ManagedBean que maneja el menuOptions y los dialogs
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = DeskBean.BEAN_NAME)
@SessionScoped
public class DeskBean implements Serializable{

	private static final long serialVersionUID = 7101283517831219515L;

	public static final String BEAN_NAME = "deskBean";
	
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	@Setter
    @ManagedProperty(value = SessionBean.EL_BEAN_NAME)
    private SessionBean sessionBean;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	@Setter
	private HtmlPanelGroup pngFrames; 

	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String FRAME_SUFFIX = "_fra";
	private String WIDGET_PREFIX = "wgtOption_";
	private String FRAME_RESOURCE = "frame.xhtml";
		
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
    	pngFrames = new HtmlPanelGroup();
    }
	
    @SuppressWarnings("el-syntax")
    private void buildMenuModel() { //TODO (epa): Optimizar con Mapas y hacerlo recursivo, con N niveles
    	rolOptionList = sessionBean.getRolOpcionList();
    	//Construye el arbol de opciones
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
                            String actionExpression = String.format("#{%s.openFrame('%d')}",BEAN_NAME,pantallaOp.getId());
                            item.setActionExpression(FacesUtils.createMethodExpression(actionExpression, null, Long.class));
                            item.setOncomplete("jsUpdPngFrame(xhr, status, args)");
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
    	int maxOptions = sessionBean.getUsuario().getMaxOptions();
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
    
    /**
     * Abre un nuevo frame con la opcion seleccionada y la agrega en 
     * un contenedor pngFrame
     * 
     * @param optionId
     * @throws IOException
     */
    public void openFrame(Long optionId) throws IOException{
    	FacesContext context = FacesContext.getCurrentInstance();
    	RequestContext requestContext = RequestContext.getCurrentInstance();
    	
	    //Busca la opcion seleccionada en la lista de opciones del usuario actual
    	Opcion option = null;
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(optionId)){
    			option = opcionTMP;
    			break;
    		}
    	}
    	
    	//Crear y agrega el compositeComponent Frame en su contenedor pngFrame
    	String pngFrameId = getFrameId();
    	if(pngFrameId != null){
    		UIComponent pngFrame = context.getViewRoot().findComponent(pngFrameId);
    		if(pngFrame != null){
    			Map<String, Object> attrs = new TreeMap<String, Object>();
        		attrs.put("widgetVar", WIDGET_PREFIX+pngFrameId);
                attrs.put("src", option.getTarget());
                attrs.put("header", option.getEtiqueta());
        		FacesUtils.includeCompositeComponent(context, pngFrame, COMPONENT_LIBRARY, FRAME_RESOURCE, pngFrameId+FRAME_SUFFIX, attrs);
        		requestContext.addCallbackParam("pngFrameId",pngFrameId);
    		}
    	}
    	
    }
	
	/**
	 * Actualiza el pngFrame del frame actual
	 * @param pngFrameId
	 */
	@SuppressWarnings("rawtypes")
	public void updateFrame(){
		FacesContext context = FacesContext.getCurrentInstance();
		//Obtiene el parametro pngFrameId
	    Map map = context.getExternalContext().getRequestParameterMap();
	    String pngFrameId = (String) map.get("pngFrameId");
		RequestContext.getCurrentInstance().update(pngFrameId);
	}
	
	/**
	 * Elimina el frame del contenedor pngFrame actual 
	 * 
	 * @param event Frame
	 */
	public void closeFrame(CloseEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		String frameId = event.getComponent().getId();
		String pngFrameId = frameId.substring(0, frameId.indexOf(FRAME_SUFFIX));
		UIComponent pngFrame = context.getViewRoot().findComponent(pngFrameId);
		if(pngFrame != null){
			pngFrame.getChildren().clear();
			RequestContext.getCurrentInstance().update(pngFrameId);
		}
    }
	
	/**
	 * Obtiene el identificador de un contenedor pngFrame disponible para
	 * ser usado por un frame de alguna opcion seleccionada
	 *  
	 * @return if pngFrame.children is empty = id pngFrame; else = null  
	 */
	private String getFrameId(){
		for (UIComponent png : pngFrames.getChildren()) {			
			if(png.getChildren().isEmpty()){
				return png.getId();
			}
		}
		sessionBean.putWarning("Solo se pueden abrir "+sessionBean.getUsuario().getMaxOptions()+" opciones a la vez");//TODO (epa): internacionalizar
		return null;
	}
	 
}

