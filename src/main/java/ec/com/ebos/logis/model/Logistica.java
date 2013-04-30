package ec.com.ebos.logis.model;

import ec.com.ebos.root.model.Entidad;


/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * @update 2013/04/29 <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Logistica <T extends Logistica<T>> extends Entidad<T> {

	private static final long serialVersionUID = -6594189464836448551L;
	
	public static final String SCHEMA = "EBOSLOGI";
		
}
