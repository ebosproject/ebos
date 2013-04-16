package ec.com.ebos.app.web.jsf.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.ExternalContext;
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

import com.sun.faces.facelets.Facelet;
import com.sun.faces.facelets.FaceletFactory;
import com.sun.faces.util.RequestStateManager;

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
	
	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String DIALOG_PREFIX = "dlgOption_";
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
    		attrs.put("widgetVar", "wgtOption_"+option.getId());
            attrs.put("src", option.getTarget());
            attrs.put("onHide", "closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            attrs.put("onShow", "changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
            attrs.put("header", option.getEtiqueta());
    		includeCompositeComponent(context, pnlDialogs, "componentes/ebos", "dialog.xhtml","dlgOption_"+option.getId(), attrs);
    		RequestContext.getCurrentInstance().update(pnlDialogs.getId());
    	}
    }
	
	@SuppressWarnings("rawtypes")
	public void closeOption(){
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
	    Map map = extContext.getRequestParameterMap();
	    HttpSession session = (HttpSession) extContext.getSession(false);	    
	    activeOptionId = Long.parseLong((String) map.get("optionId"));
	    
    	//Remove the beanOption of the session
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion option = rolOpcion.getOpcion();
    		if(option.getId().equals(activeOptionId)){
				if (session != null) {					
					pnlDialogs.getChildren().remove(getActiveDialog());
					session.removeAttribute(option.getBeanName());
					RequestContext.getCurrentInstance().update(pnlDialogs.getId());
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
     * Get Active Dialog from binding {@link #pnlDialogs}
     * @return Active Dialog
     */
    private UIComponent getActiveDialog(){
    	for(UIComponent dialog : pnlDialogs.getChildren()){
    		if(dialog.getId().equals("dlgOption_"+activeOptionId)){
    			return dialog;
    		}
    	}
    	return null;
    }
    
    /**
     * Create CompositeComponent from resource
     * 
     * @param context
     * @param libraryName
     * @param resourceName
     * @param option
     */
    public void includeCompositeComponent2(FacesContext context, String libraryName, String resourceName, Opcion option) {
        // Prepare.
        Application application = context.getApplication();
        FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);

        // This basically creates <ui:component> based on <composite:interface>.
        Resource componentResource = application.getResourceHandler().createResource(resourceName, libraryName);
        
        UIComponent composite = application.createComponent(context, componentResource);
        composite.setId("dlgOption_"+option.getId()); // Mandatory for the case composite is part of UIForm! Otherwise JSF can't find inputs.

        // This basically creates <composite:implementation>.
        UIComponent compositeRoot = application.createComponent(UIPanel.COMPONENT_TYPE);
        //composite.getAttributes().put(Resource.COMPONENT_RESOURCE_KEY, componentResource);
        
        Map<String, Object> attrs = new TreeMap<String, Object>();
        attrs.put(Resource.COMPONENT_RESOURCE_KEY, componentResource);
        attrs.put("id","dlgOption_"+option.getId());
		attrs.put("widgetVar", "wgtOption_"+option.getId());
        attrs.put("src", option.getTarget());
        attrs.put("onHide", "closeOption(([{name:'optionId', value:"+option.getId()+"}]));");
        attrs.put("onShow", "changeOption(([{name:'optionId', value:"+option.getId()+"}]));");
        attrs.put("header", option.getEtiqueta());
        composite.getAttributes().putAll(attrs);
        compositeRoot.setRendererType("javax.faces.Group");
        //composite.getFacets().put(UIComponent.COMPOSITE_FACET_NAME, compositeRoot);
        
        // Now include the composite component file in the given parent.
    	//Add dialog in parent pnlDialogs
        //pnlDialogs.getChildren().add(compositeRoot);
        //pnlDialogs.pushComponentToEL(context, compositeRoot); // This makes #{cc} available.
        try {
        	FaceletFactory factory = (FaceletFactory)
        			RequestStateManager.get(context, RequestStateManager.FACELET_FACTORY);
        	Facelet f = factory.getFacelet(componentResource.getURL());
        	f.apply(context, compositeRoot);
//            faceletContext.includeFacelet(compositeRoot, componentResource.getURL());
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
        	pnlDialogs.getChildren().add(compositeRoot);
            //pnlDialogs.popComponentFromEL(context);
            //Update pnlgDialogs with new dialog
    	    RequestContext.getCurrentInstance().update(pnlDialogs.getId());
        }
    }
    
    /**
     * Include the composite component of the given library ane resource name as child of the given UI component parent.
     * This has the same effect as using <code>&lt;my:resourceName&gt;</code>. The given component ID must be unique
     * relative to the current naming container parent and is mandatory for functioning of input components inside the
     * composite, if any.
     * @param parent The parent component to include the composite component in.
     * @param libraryName The library name of the composite component.
     * @param resourceName The resource name of the composite component.
     * @param id The component ID of the composite component.
     */
    private void includeCompositeComponent(FacesContext context, UIComponent parent, String libraryName, String resourceName, String id, Map<String, Object> attrs) {
            Application application = context.getApplication();
            FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);

            // This basically creates <ui:component> based on <composite:interface>.
            Resource resource = application.getResourceHandler().createResource(resourceName, libraryName);
            UIComponent composite = application.createComponent(context, resource);
            composite.setId(id); // Mandatory for the case composite is part of UIForm! Otherwise JSF can't find inputs.
            composite.getAttributes().putAll(attrs);

            // This basically creates <composite:implementation>.
            UIComponent implementation = application.createComponent(UIPanel.COMPONENT_TYPE);
            implementation.setRendererType("javax.faces.Group");
            composite.getFacets().put(UIComponent.COMPOSITE_FACET_NAME, implementation);

            // Now include the composite component file in the given parent.
            parent.getChildren().add(composite);
            parent.pushComponentToEL(context, composite); // This makes #{cc} available.
            try {
                faceletContext.includeFacelet(implementation, resource.getURL());
            }
            catch (IOException e) {
                throw new FacesException(e);
            }
            finally {
                parent.popComponentFromEL(context);
            }
    }
    
}
