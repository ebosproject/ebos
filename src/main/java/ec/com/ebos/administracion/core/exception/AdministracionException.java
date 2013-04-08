package ec.com.ebos.administracion.core.exception;

import ec.com.ebos.administracion.resources.AdministracionMensajes;
import ec.com.ebos.generic.core.exception.GenericException;

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
