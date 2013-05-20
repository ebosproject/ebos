package ec.com.ebos.admin.core.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;

/**
 *
 * @author Eduardo Plua Alay
 */
public class AdministracionException extends RootException{
        
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
    public AdministracionException(String keySummary, Object ... args){
            super(MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args));
    }

}
