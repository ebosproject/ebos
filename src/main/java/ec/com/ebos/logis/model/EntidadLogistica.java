package ec.com.ebos.logis.model;

import javax.persistence.Embedded;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;


/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 */
public abstract class EntidadLogistica <T extends EntidadLogistica<T>> extends Entidad<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6594189464836448551L;
	public static final String SCHEMA_OWNER = "tipo_impuesto";
	
	@Embedded
	private Auditoria auditoria;
		
}
