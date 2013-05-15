package ec.com.ebos.security.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.security.resources.SecurityMensajes;

/**
 * 
 * @author Eduardo Plua Alay
 */
public class SecurityException extends RootException {

	private static final long serialVersionUID = -4778046922922740413L;

	/**
	 * Constructor por defecto
	 */
	public SecurityException() {
		super();
	}

	/**
	 * Constructor en base a un mensaje
	 * 
	 * @param keySummary
	 *            Mensaje de error
	 */
	public SecurityException(String keySummary, Object... params) {
		super(SecurityMensajes.getString(keySummary, params), keySummary);
	}

}
