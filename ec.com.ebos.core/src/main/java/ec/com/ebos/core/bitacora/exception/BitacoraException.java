
package ec.com.ebos.core.bitacora.exception;

import ec.com.ebos.core.root.exception.RootException;
import ec.com.ebos.core.util.MessageUtils;

/**
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
public class BitacoraException extends RootException{
        
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
    public BitacoraException(String keySummary, Object ... args){
            super(MessageUtils.getLabel(keySummary, args));
    }

}
