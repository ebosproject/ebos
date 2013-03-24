package ec.com.platform.app;

import ec.com.platform.administracion.core.gestor.AdministracionG;
import ec.com.platform.administracion.model.Configuracion;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eduardo Plua Alay
 */
@Component
public class Application implements Serializable{
  
	private static final long serialVersionUID = -2219488167232672319L;

	@Getter @Setter    
    private Configuracion configuracion;
    
    @Getter @Setter
    @Autowired
    @Qualifier("administracionG")
    protected AdministracionG administracionG;

    public Application() {
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
    		configuracion = administracionG.obtenerConfiguracion();
    	}
        String value = configuracion.getParametros().get(clave);          
        return value == null? "": value;
    }
}