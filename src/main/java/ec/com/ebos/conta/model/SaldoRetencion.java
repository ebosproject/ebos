package ec.com.ebos.conta.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import ec.com.ebos.admin.model.TipoRetencion;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.root.model.Auditoria;

/**
 * Saldos retenciones
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = SaldoRetencion.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class SaldoRetencion extends Contabilidad<SaldoRetencion> {

	private static final long serialVersionUID = 8010531470490736331L;
	/**
	 * Id de la estructura organizacional
	 */
	protected static final String TABLE_NAME = "SALDO_RETENCION";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	/**
	 * Periodo contable
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_periodo")
	private Periodo periodo;
	
	/**
	 * Tipo retencion
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_tipo_retencion")
	private TipoRetencion tipoRetencion;
	
	@Column(name = "valor_retenido", nullable = false, length = 16, precision = 2)
	private BigDecimal valorRetenido = BigDecimal.ZERO;
	
	@Column(name = "base_imponible", nullable = false, length = 16, precision = 2)
	private BigDecimal baseImponible = BigDecimal.ZERO;
	
	@Embedded
	private Auditoria auditoria;
	
		
    
}
