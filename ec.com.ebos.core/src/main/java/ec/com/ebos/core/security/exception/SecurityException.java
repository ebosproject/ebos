package ec.com.ebos.core.security.exception;

import ec.com.ebos.core.root.exception.RootException;
import ec.com.ebos.core.util.FacesUtils;
import ec.com.ebos.core.util.MessageUtils;

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
	public SecurityException(String keySummary, Object... args) {
		super(MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args));
	}

}
