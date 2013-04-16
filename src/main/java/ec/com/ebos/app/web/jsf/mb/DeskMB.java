package ec.com.ebos.app.web.jsf.mb;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.CloseEvent;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.seguridad.model.RolOpcion;
import ec.com.ebos.util.FacesUtils;

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
    @ManagedProperty(value = "#{"+SessionMB.BEAN_NAME+"}")
    private SessionMB sessionMB;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	private Long activeOptionId;
	
	@Getter @Setter
	private HtmlPanelGroup pngDialogs;

	private String PNGDIALOGS_ID = ":pngDialogs";
	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String DIALOG_PREFIX = "dlgOption_";
	private String WIDGET_PREFIX = "wgtOption_";
	private String DIALOG_RESOURCE = "dialog.xhtml";
		
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
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
    		Map<String, Object> attrs = new TreeMap<String, Object>();
    		attrs.put("id","dlgOption_"+option.getId());
    		attrs.put("widgetVar", WIDGET_PREFIX+option.getId());
            attrs.put("src", option.getTarget());
            //attrs.put("onHide", "closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            //attrs.put("onShow", "changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            attrs.put("header", option.getEtiqueta());
    		FacesUtils.includeCompositeComponent(context, pngDialogs, COMPONENT_LIBRARY, DIALOG_RESOURCE, DIALOG_PREFIX+option.getId(), attrs);
    	}
    	
    }
	
//	@SuppressWarnings("rawtypes")
	public void closeOption(CloseEvent event){
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
//	    Map map = extContext.getRequestParameterMap();
	    HttpSession session = (HttpSession) extContext.getSession(false);	    
//	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    String dialogId = event.getComponent().getId();
		activeOptionId = Long.valueOf(dialogId.substring(10, dialogId.indexOf("_dlg")));
	    
    	//Remove the beanOption of the session
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion option = rolOpcion.getOpcion();
    		if(option.getId().equals(activeOptionId)){
				if (session != null) {					
					pngDialogs.getChildren().remove(getActiveDialog());
					session.removeAttribute(option.getBeanName());
					//RequestContext.getCurrentInstance().update(PNGDIALOGS_ID);
				}				
    			break;
    		}
    	}
    }

    @SuppressWarnings("rawtypes")
	public void changeOption() {
    	FacesContext context = FacesContext.getCurrentInstance();
	    Map map = context.getExternalContext().getRequestParameterMap();
	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    
    	//TODO (epa): completar
		//RequestContext.getCurrentInstance().execute("jsChangeOption("+activeOptionId+")");
	}
    
    /**
     * Get active dialog from binding {@link #pnlDialogs}
     * @return UIComponent activeDialog
     */
    private UIComponent getActiveDialog(){
    	for(UIComponent dialog : pngDialogs.getChildren()){
    		if(dialog.getId().equals("dlgOption_"+activeOptionId)){
    			return dialog;
    		}
    	}
    	return null;
    }
    
}
