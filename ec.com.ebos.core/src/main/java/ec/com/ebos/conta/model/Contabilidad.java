package ec.com.ebos.conta.model;

import ec.com.ebos.root.model.Entidad;

/**
 * Entidad abstracta para todas las {@link Entidad} del modulo {@link Contabilidad}
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Contabilidad<T extends Contabilidad<T>> {

	public static final String SCHEMA = "EBOSCONT";
    
}
