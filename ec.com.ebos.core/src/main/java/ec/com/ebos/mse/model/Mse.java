package ec.com.ebos.mse.model;

import ec.com.ebos.root.model.Entidad;

/**
 * Clase abstracta para todas las {@link Entidad} del modulo {@link Mse}
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Mse<T extends Mse<T>> {

	public static final String SCHEMA = "EBOSMSE";
    
}
