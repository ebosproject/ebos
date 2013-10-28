package ec.com.ebos.security.model;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class Security<T extends Security<T>> extends HibernateEntidad<T> {

	private static final long serialVersionUID = 4754819524498451550L;
	
	public static final String SCHEMA = "EBOSSECU";
    
}
