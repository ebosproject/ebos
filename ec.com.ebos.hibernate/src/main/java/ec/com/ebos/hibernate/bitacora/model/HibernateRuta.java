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
import ec.com.ebos.core.bitacora.model.Ruta;
import ec.com.ebos.core.root.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateRuta.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateRuta extends HibernateBitacora implements Ruta{

	private static final long serialVersionUID = -4302893291410989102L;

	protected static final String TABLE_NAME = "RUTA";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
