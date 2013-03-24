package ec.com.platform.seguridad.exception;

import ec.com.platform.generic.core.exception.GenericException;
import ec.com.platform.seguridad.resources.SeguridadMensajes;

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
