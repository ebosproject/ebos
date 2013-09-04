package ec.com.ebos.aspect.core.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.MessageUtils;

public class SecurityAspectException extends RootException {

	private static final long serialVersionUID = 1305855571537286057L;

	/**
	 * Constructor por defecto
	 */
	public SecurityAspectException() {
		super();
	}

	/**
	 * Constructor en base a un mensaje
	 * 
	 * @param keySummary
	 *            Mensaje de error
	 */
	public SecurityAspectException(String keySummary, Object... args) {
		super(MessageUtils.getLabel(keySummary, args));
	}
}
