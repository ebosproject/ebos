package ec.com.ebos.core.aspect.exception;

import ec.com.ebos.core.root.exception.RootException;

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
	 * @param summary
	 *            Mensaje de error
	 */
	public AuditoryAspectException(String summary, Object... args) {
		super(summary, args.toString());
	}
}
