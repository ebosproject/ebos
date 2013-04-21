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

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

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
	
	@Setter
    @ManagedProperty(value = SessionMB.EL_BEAN_NAME)
    private SessionMB sessionMB;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	private Long activeOptionId;
	
	@Getter @Setter
	private List<KeyFrame> pngFrameList;

	private String PNGFRAMES_ID = ":pngFrames";
	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String FRAME_PREFIX = "fraOption_";
	private String WIDGET_PREFIX = "wgtOption_";
	private String FRAME_RESOURCE = "frames.xhtml";
		
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
    	buildMapPngFrames();    	
    }
	
	public MenuModel getMenuModel(){
		if(menuModel.getContents().isEmpty()){
			buildMenuModel();		
		}
    	return menuModel;
	}
	
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
                            item.setOnclick("openOption(([{name:'optionId', value:"+pantallaOp.getId()+"}]));");
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
    
    private void buildMapPngFrames(){
    	pngFrameList =  new ArrayList<KeyFrame>();
    	int maxOptions = sessionMB.getUsuario().getMaxOptions();
    	for (int i = 0; i < maxOptions; i++) {
			pngFrameList.add(new KeyFrame(FacesUtils.getRandomId(), false));
		}
    }
    
	@SuppressWarnings("rawtypes")
	public void openOption() throws IOException{
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	//Get parameter optionId from dlgMenu
	    Map map = context.getExternalContext().getRequestParameterMap();
	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    
	    //Selection option entity from rolOptionList
    	Opcion option = null;
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(activeOptionId)){
    			option = opcionTMP;
    			break;
    		}
    	}
    	
    	//Create and put CompositeComponente Dialog into #pnlDialogs
    	if(option != null){
    		
    		UIComponent pngFrame = context.getViewRoot().findComponent(getFrameId());
    		
    		Map<String, Object> attrs = new TreeMap<String, Object>();
    		//attrs.put("id","dlgOption_"+option.getId());
    		attrs.put("widgetVar", WIDGET_PREFIX+option.getId());
            attrs.put("src", option.getTarget());
            //attrs.put("onHide", "closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            //attrs.put("onShow", "changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            attrs.put("header", option.getEtiqueta());
            attrs.put("update", PNGFRAMES_ID);
    		FacesUtils.includeCompositeComponent(context, pngFrame, COMPONENT_LIBRARY, FRAME_RESOURCE, FRAME_PREFIX+option.getId(), attrs);
    	}
    	
    }
	
	/**
	 * Remove frame from pngFrames 
	 * 
	 * @param event Frame
	 */
	public void closeFrame(CloseEvent event){
    	//Remove the beanOption of the pngFrames
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion option = rolOpcion.getOpcion();
    		if(option.getId().equals(activeOptionId)){
					
    			break;
    		}
    	}
    }

	public UIComponent getFrame(String componentId){
		UIComponent explorer = null;
//		for(UIComponent child : pngFrames.getChildren()){
//			if(child.getId().equals(componentId.substring(0, componentId.indexOf("_exp")))){
//				explorer = child;
//				break;
//			}
//		}
		return explorer; 
	}
	
	private String getFrameId(){
		for (KeyFrame keyFrame : pngFrameList) {
			if(keyFrame.getState()){
				return keyFrame.getKey();
			}			
		}
		return null;
	}
	 
}

