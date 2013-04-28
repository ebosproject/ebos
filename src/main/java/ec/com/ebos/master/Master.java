package ec.com.ebos.master;

import ec.com.ebos.admin.core.process.AdministracionP;
import ec.com.ebos.admin.model.Configuracion;

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
public class Master implements Serializable{
  
	private static final long serialVersionUID = -2219488167232672319L;

	@Getter @Setter    
    private Configuracion configuracion;
    
    @Getter @Setter
    @Autowired
    @Qualifier("adminP")
    protected AdministracionP adminP;

    public Master() {
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
}