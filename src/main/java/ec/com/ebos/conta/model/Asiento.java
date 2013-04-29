package ec.com.ebos.conta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.TipoDocumento;
import ec.com.ebos.root.model.Entidad;

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
public class Asiento extends Contabilidad<Asiento> {

	private static final long serialVersionUID = -1240240963222272900L;

	protected static final String TABLE_NAME = "ASIENTO";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

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
	@Column(name = Entidad.CODIGO_NAME, nullable = false, length = Entidad.CODIGO_LENGTH) //TODO (epa): Consultar si es unico
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad.DESCRIPCION_NAME, nullable = false, length = Entidad.DESCRIPCION_LENGTH)
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
	
}
