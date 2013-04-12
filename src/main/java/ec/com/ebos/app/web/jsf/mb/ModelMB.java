package ec.com.ebos.app.web.jsf.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.seguridad.model.RolOpcion;

/**
 * ManagedBean que maneja el menuOptions y los dialogs
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = "modelMB")
@SessionScoped
public class ModelMB implements Serializable{

	private static final long serialVersionUID = -2206811926573349460L;

	@Setter
    @ManagedProperty(value = "#{sesionUsuario}")
    private SesionUsuarioMB sesionUsuario;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	private Long activeOptionId;
	
	@Getter @Setter
	private Panel pnlDialogs;
		
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
    	rolOptionList = sesionUsuario.getRolOpcionList();
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
    	FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
    	
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
    	
    	if(option != null){
    		//UIComponent pngDialogs = context.getViewRoot().findComponent(pngId);
    		
    		//Create dialog with option entity
    		Dialog dialog = new Dialog();
    		dialog.setId("dlgOption_"+option.getId());
    		dialog.setWidgetVar("wgtOption_"+option.getId());
    		dialog.setHeader(option.getEtiqueta());
    		dialog.setVisible(true);
    		dialog.setDynamic(true);
    		dialog.setAppendToBody(false);
    		dialog.setShowEffect("fade");
    		//dialog.setHideEffect("drop");
    		dialog.setHeight("500");
    		dialog.setWidth("700");
    		dialog.setMinimizable(true);
    		dialog.setMaximizable(true);
    		dialog.setOnHide("closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
    		dialog.setOnShow("changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
    		    		
    		//Add target *.xhtml into dialog
	    	faceletContext.includeFacelet(dialog, option.getTarget());
    	    
	    	//Add dialog in parent pnlDialogs
	    	pnlDialogs.getChildren().add(dialog);
    	    
    	    //Update pnlgDialogs with new dialog
    	    RequestContext.getCurrentInstance().update(pnlDialogs.getId());
    	}
    }
	
	@SuppressWarnings("rawtypes")
	public void closeOption(){
		FacesContext context = FacesContext.getCurrentInstance();
	    Map map = context.getExternalContext().getRequestParameterMap();
	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    
    	//Remove the beanOption of the session
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion option = rolOpcion.getOpcion();
    		if(option.getId().equals(activeOptionId)){
    			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				if (session != null) {
					session.removeAttribute(option.getBeanName());
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
    
}
