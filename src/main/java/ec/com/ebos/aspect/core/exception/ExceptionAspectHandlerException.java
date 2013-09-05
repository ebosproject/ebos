package ec.com.ebos.aspect.core.exception;

import ec.com.ebos.root.core.exception.RootException;

public class ExceptionAspectHandlerException extends RootException {

	private static final long serialVersionUID = 5508632996944278587L;

	/**
	 * Constructor por defecto
	 */
	public ExceptionAspectHandlerException() {
		super();
	}

	/**
	 * Constructor en base a un mensaje
	 * 
	 * @param summary
	 *            Mensaje de error
	 */
	public ExceptionAspectHandlerException(String summary, Object... args) {
		super(summary, args.toString());
	}

}
