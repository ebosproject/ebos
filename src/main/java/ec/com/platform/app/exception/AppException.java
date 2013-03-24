
package ec.com.platform.app.exception;

import ec.com.platform.app.resources.AppMensajes;
import ec.com.platform.generic.core.exception.GenericException;

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
            super(AppMensajes.getString(keySummary, params), keySummary);
    }

}
