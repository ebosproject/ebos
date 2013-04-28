package ec.com.ebos.security.model;

import ec.com.ebos.root.model.Entidad;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class Security<T extends Security<T>> extends Entidad<T> {

	private static final long serialVersionUID = 4754819524498451550L;
	
	public static final String SCHEMA = "EBOSSECU";
    
}
