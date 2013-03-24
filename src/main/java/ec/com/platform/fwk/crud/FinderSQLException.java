package ec.com.platform.fwk.crud;

public class FinderSQLException extends Exception {
	private static final long serialVersionUID = 1961439641069396035L;

	/**
	 * Crea un new FinderSQLException object.
	 * 
	 * @param e
	 *            Exception
	 */
	public FinderSQLException(Exception e) {
		super(e);
	}

	/**
	 * Crea un FinderSQLException object.
	 * 
	 * @param msg
	 *            Mensaje
	 */
	public FinderSQLException(String msg) {
		super(msg);
	}

}
