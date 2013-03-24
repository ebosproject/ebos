package ec.com.platform.fwk.crud;

/**
 * Excepcion para operaciones Crud.
 *
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 * @version $
 * @FechaCreacion 22/07/2006
 * @FechaModificacion ${date}
 */
public class CrudException extends RuntimeException {
	/** serialVersionUID */
	public static final long serialVersionUID = 1;

	/**
	 * Crea un new CrudException object.
	 *
	 * @param e Exception
	 */
	public CrudException(Exception e) {
		super(e);
	}

}
