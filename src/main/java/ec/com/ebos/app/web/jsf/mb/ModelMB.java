package ec.com.ebos.app.web.jsf.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lombok.Setter;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.extensions.event.OpenEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.seguridad.model.RolOpcion;

/**
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
	
	private List<RolOpcion> rolOpcionList;
	
	private String activeOptionId;
	
	private String pngId = "pngDialogs";
	
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
    	rolOpcionList = sesionUsuario.getRolOpcionList();
    	//Construye arbol de opciones
        for (RolOpcion rolOpcion : rolOpcionList) {
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

                for (RolOpcion pantalla : rolOpcionList) {
                    Opcion pantallaOp = pantalla.getOpcion();
                    
                    if (pantallaOp.getPadre() != null) {
                        if (pantallaOp.getPadre() == modulo) {
                            MenuItem item = new MenuItem();                                                                              
                            item.setValue(pantallaOp.getEtiqueta());
                            //item.setOnclick("jsOpenOption('"+pantallaOp.getId()+"','"+pantallaOp.getTarget()+"');");
                            item.setOnclick("jsOpenOption('"+pantallaOp.getId()+"');");
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
	public void openOption(){
    	FacesContext context = FacesContext.getCurrentInstance();
	    Map map = context.getExternalContext().getRequestParameterMap();
	    activeOptionId = (String) map.get("optionId");
	        
    	Opcion opcion = null;
    	for(RolOpcion rolOpcion : rolOpcionList){
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(Long.parseLong(activeOptionId))){
    			opcion = opcionTMP;
    			break;
    		}
    	}
    	if(opcion != null){
    		UIComponent pngDialogs = context.getViewRoot().findComponent(pngId);
    		
    		Dialog dialog = new Dialog();
    		dialog.setId("dlgOption_"+opcion.getId());
    		dialog.setHeader(opcion.getEtiqueta());
    		dialog.setVisible(true);
    		dialog.setMinimizable(true);
    		dialog.setMaximizable(true);
    		dialog.setOnHide("openOption(([{name:'optionId', value:"+opcion.getId()+"}]));");
    		pngDialogs.getChildren().add(dialog);
    		RequestContext.getCurrentInstance().update(pngId);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public void closeOption(CloseEvent event){
	    activeOptionId = event.getComponent().getId();
	    
    	//Remove the beanOption of the session
    	for(RolOpcion rolOpcion : rolOpcionList){
    		Opcion option = rolOpcion.getOpcion();
    		if(option.getId().equals(Long.parseLong(activeOptionId))){
    			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				if (session != null) {
					session.removeAttribute(option.getBeanName());
				}
    			break;
    		}
    	}
    	RequestContext.getCurrentInstance().execute("jsCloseOption("+activeOptionId+")");
    }

    public void changeOption(OpenEvent event) {
		RequestContext.getCurrentInstance().execute("jsChangeOption("+activeOptionId+")");
	}
    
}
