package ec.com.ebos.bitacora.model;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 */
public abstract class Bitacora<T extends Bitacora<T>> extends HibernateEntidad<T> {

	private static final long serialVersionUID = -8243313018975221667L;
	
	public static final String SCHEMA = "EBOSBITA";
	
}
