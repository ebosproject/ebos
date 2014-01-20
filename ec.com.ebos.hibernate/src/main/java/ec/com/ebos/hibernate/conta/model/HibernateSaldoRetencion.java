package ec.com.ebos.hibernate.conta.model;

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

import org.hibernate.annotations.Target;

import ec.com.ebos.core.admin.model.TipoRetencion;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.Periodo;
import ec.com.ebos.core.conta.model.SaldoRetencion;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.hibernate.admin.model.HibernateTipoRetencion;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * Saldos retenciones
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateSaldoRetencion.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@Auditable
public class HibernateSaldoRetencion extends HibernateContabilidad implements SaldoRetencion {

	private static final long serialVersionUID = 8010531470490736331L;
	/**
	 * Id de la estructura organizacional
	 */
	protected static final String TABLE_NAME = "SALDO_RETENCION";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	/**
	 * Periodo contable
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernatePeriodo.class)
    @JoinColumn(name = "id_periodo")
	private Periodo periodo;
	
	/**
	 * Tipo retencion
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernateTipoRetencion.class)
    @JoinColumn(name = "id_tipo_retencion")
	private TipoRetencion tipoRetencion;
	
	@Column(name = "valor_retenido", nullable = false, length = 16, precision = 2)
	private BigDecimal valorRetenido = BigDecimal.ZERO;
	
	@Column(name = "base_imponible", nullable = false, length = 16, precision = 2)
	private BigDecimal baseImponible = BigDecimal.ZERO;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
		
    
}
