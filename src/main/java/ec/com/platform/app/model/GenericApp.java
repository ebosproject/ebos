package ec.com.platform.app.model;

import ec.com.platform.generic.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class GenericApp<T extends GenericApp<T>> extends Entidad<T> {

	private static final long serialVersionUID = 1823575951990721496L;
	
}
