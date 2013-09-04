package ec.com.ebos.aspect.core.exception;

import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.util.MessageUtils;

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
	 * @param keySummary
	 *            Mensaje de error
	 */
	public ExceptionAspectHandlerException(String keySummary, Object... args) {
		super(MessageUtils.getLabel(keySummary, args));
	}

}
