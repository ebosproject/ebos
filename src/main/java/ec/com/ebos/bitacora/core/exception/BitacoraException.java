
package ec.com.ebos.bitacora.core.exception;

import ec.com.ebos.master.resources.MasterMensajes;
import ec.com.ebos.root.core.exception.GenericException;

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
            super(MasterMensajes.getString(keySummary, params), keySummary);
    }

}
