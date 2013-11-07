package ec.com.ebos.master.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.master.model.Master;
import ec.com.ebos.core.master.model.TipoImpuesto;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * @update 2013/04/29 <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateTipoImpuesto.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateTipoImpuesto extends HibernateEntidad implements TipoImpuesto{

	private static final long serialVersionUID = 3922934845182492539L;
	
	protected static final String TABLE_NAME = "TIPO_IMPUESTO";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del tipo de documento
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;
	
	@Column(name = "clase", nullable = false, length = 3)
    @Type(type = ClaseImpuesto.TYPE)
	private ClaseImpuesto claseImpuesto;
	
}
