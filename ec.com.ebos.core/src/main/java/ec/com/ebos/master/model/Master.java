package ec.com.ebos.master.model;

import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Master<T extends Master<T>> extends Entidad<T> {

	private static final long serialVersionUID = 1823575951990721496L;
	
	public static final String SCHEMA = "EBOSMAST";
	
}
