
package ec.com.platform.bitacora.core.exception;

import ec.com.platform.app.resources.AppMensajes;
import ec.com.platform.generic.core.exception.GenericException;

/**
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
public class BitacoraException extends GenericException{
        
	private static final long serialVersionUID = 7814015326508437254L;

	/**
        * Constructor por defecto 
        */
    public BitacoraException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public BitacoraException(String keySummary, Object ... params){
            super(AppMensajes.getString(keySummary, params), keySummary);
    }

}
