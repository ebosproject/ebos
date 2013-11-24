package ec.com.ebos.web.master.jsf.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.master.exception.MasterException;
import ec.com.ebos.core.master.session.SessionBean;
import ec.com.ebos.core.security.model.RolOpcion;
import ec.com.ebos.core.util.FacesUtils;
import ec.com.ebos.core.util.NumberUtils;
import ec.com.ebos.core.util.StringUtils;

/**
 * ManagedBean que maneja el menuOptions y los dialogs
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Component
@ManagedBean(name = DeskBean.BEAN_NAME)
@SessionScoped
public class DeskBean implements Serializable{

	private static final long serialVersionUID = 7101283517831219515L;

	public static final String BEAN_NAME = "deskBean";
	
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	@Setter
    @Autowired
    @Qualifier(SessionBean.BEAN_NAME)
    private SessionBean sessionBean;    
	
	private MenuModel menuModel;
	
	private List<RolOpcion> rolOptionList;
	
	@Setter
	private Panel pnlFrames; 

	private String COMPONENT_LIBRARY = "componentes/ebos";
	private String PNGFRAME_PREFIX = "png_";
	private String FRAME_PREFIX = "fra_";
	private String FRAME_RESOURCE = "frame.xhtml";
		
	@PostConstruct
    public void init(){
    	menuModel = new DefaultMenuModel();
    	pnlFrames = new Panel();
    }
	
    private void buildMenuModel() { //TODO (epa): Optimizar con Mapas y hacerlo recursivo, con N niveles
    	String style = "background-image:url('resources/images/%s') !important;"
                + "background-repeat: no-repeat;"
                + "background-position: center left;"
                + "padding: 0px 0px 0px 20px";
    	rolOptionList = sessionBean.getRolOpcionList();
    	//Construye el arbol de opciones
        for (RolOpcion rolOpcion : rolOptionList) {
            Submenu submenu = new Submenu();
            Opcion modulo = rolOpcion.getOpcion();
            if (modulo.getPadre() == null) {
            	String labelExpression = String.format("#{msg.%s}",modulo.getEtiqueta());
            	submenu.setValueExpression("label", FacesUtils.createValueExpression(labelExpression, String.class));
                String iconoModulo = modulo.getIcono();
                if(iconoModulo != null && !iconoModulo.isEmpty()){
                	submenu.setStyle(String.format(style, iconoModulo));                	
                }

                for (RolOpcion pantalla : rolOptionList) {
                    Opcion pantallaOp = pantalla.getOpcion();
                    
                    if (pantallaOp.getPadre() != null) {
                        if (pantallaOp.getPadre() == modulo) {
                            MenuItem item = new MenuItem();                                                               
                            String valueExpression = String.format("#{msg.%s}",pantallaOp.getEtiqueta()); 
                            item.setValueExpression("value", FacesUtils.createValueExpression(valueExpression, String.class));
                            String actionExpression = String.format("#{%s.openFrame('%d')}",BEAN_NAME,pantallaOp.getId());
                            item.setActionExpression(FacesUtils.createMethodExpression(actionExpression, null, Long.class));
                            item.setOnstart("wgtAS.show();");
                            item.setOncomplete("jsUpdPngFrame(xhr, status, args);wgtAS.hide();");
                            String iconoPantalla = pantallaOp.getIcono();
                            if(iconoPantalla != null && !iconoPantalla.isEmpty()){
                            	item.setStyle(String.format(style, iconoPantalla));
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
    
    private void buildPnlFrameList(){
    	FacesContext context = FacesContext.getCurrentInstance();
    	pnlFrames.getChildren().clear();
    	int maxOptions = sessionBean.getUsuario().getMaxOptions();
    	for (int i = 0; i < maxOptions; i++) {
    		HtmlPanelGroup pngFrame = new HtmlPanelGroup();
    		pngFrame.setId(PNGFRAME_PREFIX+context.getViewRoot().createUniqueId());
    		pnlFrames.getChildren().add(pngFrame);
		}
    }
    
    public Panel getPnlFrames(){
		if(pnlFrames.getChildren().isEmpty()){
			buildPnlFrameList();	
		}
    	return pnlFrames;
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
    	Opcion option = getOption(optionId);
    	
    	//Crear y agrega el compositeComponent Frame en su contenedor pngFrame
    	UIComponent pngFrame = getPngFrame();
    	if(pngFrame != null){
    		pngFrame = context.getViewRoot().findComponent(pngFrame.getId());
            try{
            	Map<String, Object> attrs = new TreeMap<String, Object>();
            	attrs.put("optionId", option.getId());
	            attrs.put("src", option.getTarget());
	            attrs.put("header", option.getEtiqueta());
	            attrs.put("parentId", pngFrame.getId());
	            attrs.put("width", option.getWidth());
	            attrs.put("height", option.getHeight());
        		FacesUtils.includeCompositeComponent(context, pngFrame, COMPONENT_LIBRARY, FRAME_RESOURCE, FRAME_PREFIX+context.getViewRoot().createUniqueId(), attrs);
        		requestContext.addCallbackParam("pngFrameId",pngFrame.getId());
            } catch(Exception ex){
            	pngFrame.getChildren().clear();
            	throw new MasterException(ex);
            }
    	}
    }

	/**
	 * Elimina el frame del contenedor pngFrame actual 
	 * 
	 * @param event Frame 
	 */
	public void closeFrame(CloseEvent event){
		String pngFrameId = event.getComponent().getParent().getParent().getParent().getId();
		Long optionId = NumberUtils.parseLong(event.getComponent().getAttributes().get("optionId").toString());
		boolean update = false;
		try{
			for(UIComponent pngFrame : pnlFrames.getChildren()){
				if(pngFrame.getId().equals(pngFrameId)){
					pngFrame.getChildren().clear();
					update = true;
					
					String beanName = getOption(optionId).getBeanName();
					if (!StringUtils.isBlank(beanName)) {
						// Remueve bean de la session actual
						HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
						if (session != null) {
							session.removeAttribute(beanName);
						}
					}
					break;
				}
			}
		} catch(Exception ex){
			sessionBean.putError("desktop.summary.loadoption", ex.getMessage());
		} finally{
			if(update){
				RequestContext.getCurrentInstance().update(pngFrameId);
			}
		}
    }
	
	/**
	 * Obtiene un contenedor pngFrame disponible para
	 * ser usado por un frame de alguna opcion seleccionada
	 *  
	 * @return if pngFrame.children is empty return pngFrame; else return null  
	 */
	private UIComponent getPngFrame(){
		for (UIComponent pngFrame : pnlFrames.getChildren()) {			
			if(pngFrame.getChildCount() == 0){
				return pngFrame;
			}
		}
		sessionBean.putWarn("desktop.warn.maxloadoption","",sessionBean.getUsuario().getMaxOptions());
		return null;
	}
	
	/**
	 * Obtiene una opcion de la lista de opciones del usuario actual
	 * 
	 * @param optionId
	 * @return
	 */
	private Opcion getOption(Long optionId) {
    	for(RolOpcion rolOpcion : rolOptionList){
    		Opcion opcionTMP = rolOpcion.getOpcion();
    		if(opcionTMP.getId().equals(optionId)){
    			return opcionTMP;
    		}
    	}
    	return null;
	}
		
	@Getter @Setter
	private String url = "";
	 
}

