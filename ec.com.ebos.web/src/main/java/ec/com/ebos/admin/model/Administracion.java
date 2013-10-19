package ec.com.ebos.admin.model;

import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Administracion<T extends Administracion<T>> extends Entidad<T> {

	private static final long serialVersionUID = -9112699178930476045L;
	
	public static final String SCHEMA = "EBOSADMI";
	
}
