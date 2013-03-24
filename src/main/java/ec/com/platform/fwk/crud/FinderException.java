package ec.com.platform.fwk.crud;

/**
 * Excepcion para operaciones de consulta mediante Finder.
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 * @version $
 * @FechaCreacion 22/07/2006
 * @FechaModificacion ${date}
 */
public class FinderException extends RuntimeException {
	
	/** serialVersionUID */
	public static final long serialVersionUID = 1;
	
	/**
	 * Crea un new FinderException object.
	 *
	 * @param e Exception
	 */
    public FinderException(Exception e) {
        super(e);
    }

}
