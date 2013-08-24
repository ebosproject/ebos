
package ec.com.ebos.mse.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;

/**
 *
 * @author Victor Viejo Chabla
 */
public class MseException extends RootException{
        

    /**
	 * 
	 */
	private static final long serialVersionUID = 3113376499129116486L;

	/**
        * Constructor por defecto 
        */
    public MseException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public MseException(String keySummary, Object ... args){
            super(MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args));
    }

}
