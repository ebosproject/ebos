
package ec.com.ebos.conta.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;

/**
 *
 * @author Victor Viejo Chabla
 */
public class ContaException extends RootException{
        

    /**
	 * 
	 */
	private static final long serialVersionUID = 3113376499129116486L;

	/**
        * Constructor por defecto 
        */
    public ContaException(){
            super();
    }

    /**
        * Constructor en base a un mensaje
        * 
        * @param keySummary Mensaje de error
        */
    public ContaException(String keySummary, Object ... args){
            super(MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args));
    }

}
