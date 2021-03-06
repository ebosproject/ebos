package ec.com.ebos.hibernate.conta.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Asiento;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.TipoAsiento;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.field.Asiento_;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.root.model.field.Entidad_;

/**
 * Asientos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateTipoAsiento.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateTipoAsiento extends HibernateContabilidad implements TipoAsiento {

	private static final long serialVersionUID = -7133451003821826238L;
	
	protected static final String TABLE_NAME = "TIPO_ASIENTO";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	/**
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;	
	
	@Column(name = "formato_referencia", length = 20, nullable = false)
	private String formatoReferencia = "###-###-##########";
	
	/**
	 * Indica si distrubuye o no
	 */
	@Column(name = "distribucion",nullable = false)
	private boolean distribucion = false;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	/**
	 * Estado del {@link HibernateTipoAsiento}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;		
	
	@OneToMany(mappedBy = Asiento_.tipoAsiento, fetch = FetchType.LAZY, targetEntity = HibernateAsiento.class)
    private Set<Asiento> asientoList = new HashSet<Asiento>(0);
    
}
