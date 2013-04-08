package ec.com.ebos.seguridad.exception;

import ec.com.ebos.generic.core.exception.GenericException;
import ec.com.ebos.seguridad.resources.SeguridadMensajes;

/**
 * 
 * @author Eduardo Plua Alay
 */
public class SeguridadException extends GenericException {

	private static final long serialVersionUID = -4778046922922740413L;

	/**
	 * Constructor por defecto
	 */
	public SeguridadException() {
		super();
	}

	/**
	 * Constructor en base a un mensaje
	 * 
	 * @param keySummary
	 *            Mensaje de error
	 */
	public SeguridadException(String keySummary, Object... params) {
		super(SeguridadMensajes.getString(keySummary, params), keySummary);
	}

}
