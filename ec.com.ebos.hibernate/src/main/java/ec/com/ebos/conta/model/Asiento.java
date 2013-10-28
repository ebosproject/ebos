package ec.com.ebos.conta.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.field.AsientoDetalle_;
import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.master.model.TipoDocumento;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * Asientos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = Asiento.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class Asiento extends Contabilidad<Asiento> {

	private static final long serialVersionUID = -1240240963222272900L;

	protected static final String TABLE_NAME = "ASIENTO";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
    private Organizacion empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_punto_venta")
    private Organizacion puntoVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo")
    private Periodo periodo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento")
    private TipoDocumento tipoDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_asiento", nullable = false)
    private TipoAsiento tipoAsiento;
    
	/**
	 * Documento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_documento")
    private Documento documento;
	
	/**
	 * Secuencia anual por empresa y tipo de asiento
	 */
	@Column(name = "secuencia", nullable = false)
	private int secuencia;
	
	/**
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght) //TODO (epa): Consultar si es unico
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;
	
	/**
	 * Comentario del asiento
	 */
	@Column(name = "comentario", nullable = false, length = 100)
	private String comentario;
	
	/**
	 * Indica si el asiento esta o no cuadrado
	 */
	@Column(name = "cuadrado", nullable = false)
	private boolean cuadrado = true;
	
	@OneToMany(mappedBy = AsientoDetalle_.asiento, fetch = FetchType.LAZY)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
}
