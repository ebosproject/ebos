package ec.com.ebos.util;

import java.io.IOException;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.view.facelets.FaceletContext;

/**
 * Utilidades para JSF
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013/04/16
 */
public class FacesUtils {
	
    /**
     * Include the composite component of the given library ane resource name as child of the given UI component parent.
     * This has the same effect as using <code>&lt;my:resourceName&gt;</code>. The given component ID must be unique
     * relative to the current naming container parent and is mandatory for functioning of input components inside the
     * composite, if any.
     * @param context FacesContext
     * @param parent The parent component to include the composite component in.
     * @param libraryName The library name of the composite component.
     * @param resourceName The resource name of the composite component.
     * @param id The component ID of the composite component.
     * @param attrs CompositeComponent attributes
     */
    	public static void includeCompositeComponent(FacesContext context, UIComponent parent, String libraryName, String resourceName, String id, Map<String, Object> attrs) {
    	//public static void includeCompositeComponent(FacesContext context, UIComponent parent, String libraryName, String resourceName, Map<String, Object> attrs) {
            Application application = context.getApplication();
            FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);

            // This basically creates <ui:component> based on <composite:interface>.
            Resource resource = application.getResourceHandler().createResource(resourceName, libraryName);
            UIComponent composite = application.createComponent(context, resource);
            composite.setId(id); // Mandatory for the case composite is part of UIForm! Otherwise JSF can't find inputs.
            //composite.setId(getRandomId());
            composite.getAttributes().putAll(attrs);

            // This basically creates <composite:implementation>.
            UIComponent implementation = application.createComponent(UIPanel.COMPONENT_TYPE);
            implementation.setRendererType("javax.faces.Group");
            composite.getFacets().put(UIComponent.COMPOSITE_FACET_NAME, implementation);

            // Now include the composite component file in the given parent.
            parent.getChildren().clear();
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
    
	/**
	 * Obtiene un id aleatorio
	 * 
	 * @return id generado
	 */
    public static final String getRandomId() {
        return ("" + Math.random()).substring(2);
    }
    
    /**
     * Crea un MethodExpression para ser asignado en {@link UIComponent} como EL
     * 
     * @see http://stackoverflow.com/questions/12098611/dynamic-jsf-2-0-commandbuttons-set-action-with-arguments
     * <h4>Ejemplos de action method:</h4>
     * <pre>
     * createMethodExpression("#{bean.submit1}", null);
     * createMethodExpression("#{bean.submit2}", String.class);
	 * createMethodExpression("#{bean.submit3('foo')}", null, String.class);
	 * createMethodExpression("#{bean.submit4('foo')}", String.class, String.class);
	 * createMethodExpression("#{bean.submit5('foo', 0)}", null, String.class, Long.class);
	 * createMethodExpression("#{bean.submit6(0, 'foo')}", String.class, Long.class, String.class);
	 * </pre>
     * 
     * @param expression
     * @param returnType
     * @param parameterTypes
     * @return a MethodExpression
     */
    public static MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createMethodExpression(
            facesContext.getELContext(), expression, returnType, parameterTypes);
    }  

    /**
     * CRea un MethodExpressionActionListener para ser asignado en {@link UIComponent} como EL
     * @see http://stackoverflow.com/questions/2158009/deprecated-richfaces-javax-faces-el-methodbinding-replacement-use
     * @param actionListenerExpression
     * @return
     */
    public static MethodExpressionActionListener createActionListener(String actionListenerExpression) {
        FacesContext context = FacesContext.getCurrentInstance();
        return new MethodExpressionActionListener(context.getApplication().getExpressionFactory()
            .createMethodExpression(context.getELContext(), actionListenerExpression, null, new Class[] {ActionEvent.class}));
    }
    
    /**
     * Remueve un bean ViewScope del ViewRoot
     * @param beanName
     */
    public static void removeViewScopedBean(String beanName){
      FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(beanName);
    }

}
