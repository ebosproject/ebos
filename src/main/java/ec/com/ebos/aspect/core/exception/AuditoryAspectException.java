package ec.com.ebos.aspect.core.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.MessageUtils;

public class AuditoryAspectException extends RootException {

	private static final long serialVersionUID = 7586489021770371969L;

	/**
	 * Constructor por defecto
	 */
	public AuditoryAspectException() {
		super();
	}

	/**
	 * Constructor en base a un mensaje
	 * 
	 * @param keySummary
	 *            Mensaje de error
	 */
	public AuditoryAspectException(String keySummary, Object... args) {
		super(MessageUtils.getLabel(keySummary, args));
	}
}
