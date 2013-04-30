
package ec.com.ebos.conta.exception;

import ec.com.ebos.master.resources.MasterMensajes;
import ec.com.ebos.root.core.exception.GenericException;

/**
 *
 * @author Victor Viejo Chabla
 */
public class ContaException extends GenericException{
        

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
    public ContaException(String keySummary, Object ... params){
            super(MasterMensajes.getString(keySummary, params), keySummary);
    }

}
