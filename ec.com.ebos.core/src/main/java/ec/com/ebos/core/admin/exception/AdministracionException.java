package ec.com.ebos.core.admin.exception;

import ec.com.ebos.core.root.exception.RootException;
import ec.com.ebos.core.util.FacesUtils;
import ec.com.ebos.core.util.MessageUtils;

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
