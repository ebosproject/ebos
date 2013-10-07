package ec.com.ebos.util.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;

/**
 *
 * @author Eduardo Plua Alay
 */
public class UtilException extends RootException{
        
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
    public UtilException(String keySummary, Object ... args){
        super(MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args));
    }

}
