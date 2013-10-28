package ec.com.ebos.conta.model;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;

/**
 * Entidad abstracta para todas las {@link HibernateEntidad} del modulo {@link Contabilidad}
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Contabilidad<T extends Contabilidad<T>> extends HibernateEntidad<T> {

	private static final long serialVersionUID = -1478352918071303540L;
	public static final String SCHEMA = "EBOSCONT";
    
}
