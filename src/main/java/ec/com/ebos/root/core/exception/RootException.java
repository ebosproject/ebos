package ec.com.ebos.root.core.exception;

import ec.com.ebos.util.ObjectUtils;

import java.io.PrintStream;
import org.apache.commons.lang.StringUtils;


/**
 * Superclase para el manejo de errores de toda la plataforma
 * 
 * @author Eduardo Plua Alay
 */
public abstract class RootException extends RuntimeException {

	private static final long serialVersionUID = -6368553756451514439L;

	public static enum StackTraceLevel { NONE, BRIEF, FULL };

	private final String key;

	/**
	 * Indica si el error es fatal 
	 */
	private boolean fatal = false;

	/**
	 * Nivel de impresion del stack trace de la causa (default: FULL)
	 */
	private StackTraceLevel stackTraceLevel = StackTraceLevel.FULL;

	/**
	 * @return <code>true</code> si el error es fatal 
	 */
	public boolean isFatal() {
		return fatal;
	}

	public String getKey() {
		return key;
	}

	/**
	 * @return Este mismo error convertido en fatal
	 */
	@SuppressWarnings("unchecked")
	public <E extends RootException> E fatal() {
		this.fatal = true;
		return (E) this;
	}
	
	/**
	 * Definit nivel de stackTrace de la causa
	 * @param stackTraceLevel
	 */
	@SuppressWarnings("unchecked")
	public <E extends RootException> E setStackTrace(StackTraceLevel stackTraceLevel) {
		this.stackTraceLevel = stackTraceLevel;
		return (E) this;
	}

	/**
	 * No imprimir stackTrace de la causa
	 */
	@SuppressWarnings("unchecked")
	public <E extends RootException> E noStackTrace() {
		this.stackTraceLevel = StackTraceLevel.NONE;
		return (E) this;
	}

	/**
	 * Imprimir stackTrace resumido de la causa
	 */
	@SuppressWarnings("unchecked")
	public <E extends RootException> E briefStackTrace() {
		this.stackTraceLevel = StackTraceLevel.BRIEF;
		return (E) this;
	}

	/**
	 * Imprimir stackTrace completo de la causa
	 */
	@SuppressWarnings("unchecked")
	public <E extends RootException> E fullStackTrace() {
		this.stackTraceLevel = StackTraceLevel.FULL;
		return (E) this;
	}

	/**
	 * Constructor por defecto. 
	 */
	public RootException() {
		super();
		this.key = StringUtils.EMPTY;
	}

	/**
	 * Constructor en base a un mensaje.
	 * @param message Mensaje de error.
	 */
	public RootException(String message) {
		super(message);
		this.key = StringUtils.EMPTY;
	}

	/**
	 * Constructor en base a un mensaje.
	 * @param message Mensaje de error.
	 */
	public RootException(String message, String key) {
		super(message);
		this.key = key;
	}

	/**
	 * Constructor en base a un objeto de causa.
	 * @param cause Causa del Error.
	 */
	public RootException(Throwable cause) {
		super(cause);
		this.key = StringUtils.EMPTY;
	}

	@Override
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			s.println(this);
			Throwable ourCause = getCause();
			if (ourCause != null && stackTraceLevel != StackTraceLevel.NONE) {
				// imprimir causa
				s.print("Caused by: ");
				if (stackTraceLevel == StackTraceLevel.BRIEF) {
		            s.println(ourCause);
					StackTraceElement[] trace = ourCause.getStackTrace();
					if (!ObjectUtils.isEmpty(trace)) {
						for (StackTraceElement stackTraceElement : trace) {
							String string = stackTraceElement.toString();
							if (string.startsWith("ec.com.ebos.")) {
								s.println("\tat " + string);
							}
						}
					}
				} else {
					ourCause.printStackTrace(s);
				}
			} else {
				// imprimir primer elemento del trace (generalmente donde se lanzo)
				StackTraceElement[] trace = getStackTrace();
				if (!ObjectUtils.isEmpty(trace)) {
	                s.println("\tat " + trace[0]);
				}
			}
		}
	}
}