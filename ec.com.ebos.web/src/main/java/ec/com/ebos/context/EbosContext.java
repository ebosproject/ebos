package ec.com.ebos.context;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ec.com.ebos.admin.core.process.AdministracionP;
import ec.com.ebos.admin.model.Configuracion;

/**
 * {@link EbosContext} es un spring singleton @Component que nos permite acceder a funciones principales
 * de todad la plataforma
 *
 * @author Eduardo Plua Alay
 * @since 2013/05/01
 */
@Component
public class EbosContext implements Serializable{
  
	private static final long serialVersionUID = -2219488167232672319L;

	@Getter @Setter    
    private Configuracion configuracion;
    
    @Getter @Setter
    @Autowired
    @Qualifier("adminP")
    protected AdministracionP adminP;
    
	private static ApplicationContext applicationContext;
    
    @Autowired
    public EbosContext(ApplicationContext applicationContext) {
    	EbosContext.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
    }
    
    /**
     * Obtiene Valor de un parametro por su clave
     * @param clave
     * @return valor del parametro
     */
    public String getParametro(String clave){
    	if(configuracion == null){
    		configuracion = adminP.getConfiguracion();
    	}
        String value = configuracion.getParametros().get(clave);          
        return value == null? "": value;
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return (T) applicationContext.getBean(beanName, beanClass);
    }
     
    public static <T> T getBean(Class<T> beanClass) {
        return (T) applicationContext.getBean(beanClass);
    }
    
    public static void addCallbackParam(String name, Object value){
    	RequestContext context = RequestContext.getCurrentInstance();
    	context.addCallbackParam(name, value);
    }
    
    public static ExternalContext getExternalContext(){
    	 return FacesContext.getCurrentInstance().getExternalContext();
    }

	public static void updateComponent(String... ids) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		for(String id : ids){
			requestContext.update(id);
		}
	}
}