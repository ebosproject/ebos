package ec.com.ebos.util.exception;

import ec.com.ebos.generic.core.exception.GenericException;
import ec.com.ebos.util.resources.UtilMensajes;

/**
 *
 * @author Eduardo Plua Alay
 */
public class UtilException extends GenericException{
        
    private static final long serialVersionUID = -4778046922922740413L;

    /**
        * Constructor por defecto 
        */
    public UtilException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public UtilException(String keySummary, Object ... params){
            super(UtilMensajes.getString(keySummary, params), keySummary);
    }

}