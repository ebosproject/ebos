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

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import ec.com.ebos.app.web.jsf.component.CustomTab;
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
	
	@Setter 
    private TabView tabModel;
	
	private CustomTab tab;
	
	private List<RolOpcion> rolOpcionList;
	
	private String activeOpcionId;
	
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
    	tabModel = new TabView();
    }
	
	public MenuModel getMenuModel(){
		if(menuModel.getContents().isEmpty()){
			buildMenuModel();		
		}
    	return menuModel;
	}
	
	public TabView getTabModel(){// TODO (epa): quitar si ya no hay errores de CustomTab
		return tabModel;
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
                            item.setOnclick("jsAbrirOpcion('"+pantallaOp.getId()+"','"+pantallaOp.getTarget()+"');");
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
        //Limpia el tabModel
        tabModel.getChildren().clear();
    }
    
    @SuppressWarnings("rawtypes")
	public void abrirOpcion(){
    	FacesContext context = FacesContext.getCurrentInstance();
	    Map map = context.getExternalContext().getRequestParameterMap();
	    activeOpcionId = (String) map.get("opcionId");
	    int index = 0;
	    boolean exist = false;

	    for(UIComponent tab: tabModel.getChildren()){
	    	if(exist = tab.getId().equals("tab_"+activeOpcionId)){
	    		index = tabModel.getChildren().indexOf(tab); 
	    		break;
	    	}
	    }
	    
	    if(!exist){	    	
	    	Opcion opcion = null;
	    	for(RolOpcion rolOpcion : rolOpcionList){ //TODO (epa): Despues de la optimizacion de Mapas, se debe actualizar esta busqueda
	    		Opcion opcionTMP = rolOpcion.getOpcion();
	    		if(opcionTMP.getId().equals(Long.parseLong(activeOpcionId))){
	    			opcion = opcionTMP;
	    			break;
	    		}
	    	}
	    	if(opcion != null){
	    		tab = new CustomTab();
		    	tab.setId("tab_"+activeOpcionId);
		    	tab.setTitle(opcion.getEtiqueta());
		    	tab.setClosable(true);
		    	tab.setOpcion(opcion);
		    	tabModel.getChildren().add(tab);
		    	index = tabModel.getChildren().indexOf(tab);
	    	}
	    }
    	tabModel.setActiveIndex(index);
    }
    
    public void cerrarOpcion(TabCloseEvent event){
    	Tab selectedTab = event.getTab();
    	String opcionId = selectedTab.getId().substring(4);
    	//Remueve el beanOption de la sesion
    	for(RolOpcion rolOpcion : rolOpcionList){ //TODO (epa): Despues de la optimizacion de Mapas, se debe actualizar esta busqueda
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(Long.parseLong(opcionId))){
    			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				if (session != null) {
					session.removeAttribute(opcionTMP.getBeanName());
				}
    			break;
    		}
    	}
    	//Remueve el tab del tabView
    	tabModel.getChildren().remove(selectedTab);
    	RequestContext requestContext = RequestContext.getCurrentInstance();
    	requestContext.execute("jsCerrarOpcion("+opcionId+")");
    	if(activeOpcionId.equals(opcionId)){
    		activeOpcionId = tabModel.getChildren().size() > 0? tabModel.getChildren().get(0).getId().substring(4) : "0";
    		requestContext.execute("jsCambiarOpcion("+activeOpcionId+")");    		
    	}    	
    }

    public void cambiarOpcion(TabChangeEvent event) {
    	Tab selectedTab = event.getTab();
    	activeOpcionId = selectedTab != null? selectedTab.getId().substring(4) : "0";
		RequestContext.getCurrentInstance().execute("jsCambiarOpcion("+activeOpcionId+")");
	}
    
}
