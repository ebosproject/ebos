package ec.com.ebos.hibernate.bitacora.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.bitacora.model.Bitacora;
import ec.com.ebos.core.bitacora.model.RutaLog;
import ec.com.ebos.core.root.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateRutaLog.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateRutaLog extends HibernateBitacora implements RutaLog{

	private static final long serialVersionUID = 6923351017759818531L;

	protected static final String TABLE_NAME = "RUTA_LOG";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
