package ec.com.ebos.master.model.hibernate;

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
import ec.com.ebos.conta.model.Asiento;
import ec.com.ebos.conta.model.hibernate.field.Asiento_;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.TipoDocumento;

/**
 * Tipos de transacciones de la empresa
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013/04/27
 */
@Entity
@Table(name = HibernateTipoDocumento.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class HibernateTipoDocumento extends HibernateMaster implements TipoDocumento{

	private static final long serialVersionUID = -2896367216397132540L;

	protected static final String TABLE_NAME = "TIPO_DOCUMENTO";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del tipo de documento
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Column(name = "impresion_automatica", nullable = false)
	private boolean impresionAutomatica = false;
	
	@Column(name = "contable", nullable = false)
	private boolean contable = true;
	
	@Column(name = "contabiliza", nullable = false)
	private boolean contabiliza = false;
	
	@Column(name = "pide_preimpreso", nullable = false)
	private boolean pidePreimpreso = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento_reserva")
    private TipoDocumento tipoDocumentoReversa; //TODO (epa): Consultar si tabla es recursiva
	
    @OneToMany(mappedBy = Asiento_.tipoDocumento, fetch = FetchType.LAZY)
    private Set<Asiento> asientoList = new HashSet<Asiento>(0);
	
}
