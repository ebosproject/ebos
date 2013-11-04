package ec.com.ebos.bitacora.model.hibernate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.bitacora.model.Bitacora;
import ec.com.ebos.bitacora.model.InspeccionDetalle;
import ec.com.ebos.root.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateInspeccionDetalle.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateInspeccionDetalle extends HibernateBitacora implements InspeccionDetalle{

	private static final long serialVersionUID = -7309331268578348471L;

	protected static final String TABLE_NAME = "INSPECCION_DETALLE";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
