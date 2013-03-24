package ec.com.platform.administracion.core.exception;

import ec.com.platform.administracion.resources.AdministracionMensajes;
import ec.com.platform.generic.core.exception.GenericException;

/**
 *
 * @author Eduardo Plua Alay
 */
public class AdministracionException extends GenericException{
        
     /**
	 * 
	 */
	private static final long serialVersionUID = 7542131143401733402L;

	/**
        * Constructor por defecto 
        */
    public AdministracionException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public AdministracionException(String keySummary, Object ... params){
            super(AdministracionMensajes.getString(keySummary, params), keySummary);
    }

}
