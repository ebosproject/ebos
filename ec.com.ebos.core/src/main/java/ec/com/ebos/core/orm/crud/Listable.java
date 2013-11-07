package ec.com.ebos.core.orm.crud;

import java.io.Serializable;

/**
 * Interface DOCUMENT ME!
 * 
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 * @version $$
 * @FechaCreacion 12/07/2006
 * @FechaModificacion ${date}
 */
public interface Listable extends Serializable {
	/**
	 * Retorna un identificador del objeto	 *
	 * @return Serializable, objeto de identificacion unica
	 */
	public Serializable getEntityId();

	/**
	 * Retorna un identificador del objeto para mostrar en interface de usuario	 *
	 * @return Serializable, objeto para renderizar
	 */
	public Serializable getEtiqueta();
}
