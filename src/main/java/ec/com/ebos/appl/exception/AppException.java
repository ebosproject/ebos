
package ec.com.ebos.appl.exception;

import ec.com.ebos.appl.resources.ApplMensajes;
import ec.com.ebos.root.core.exception.GenericException;

/**
 *
 * @author Eduardo Plua Alay
 */
public class AppException extends GenericException{
        
    private static final long serialVersionUID = -4778046922922740413L;

    /**
        * Constructor por defecto 
        */
    public AppException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public AppException(String keySummary, Object ... params){
            super(ApplMensajes.getString(keySummary, params), keySummary);
    }

}
