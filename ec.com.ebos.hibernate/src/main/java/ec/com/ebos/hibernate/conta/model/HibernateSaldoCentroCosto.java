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
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.CentroCosto;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.Periodo;
import ec.com.ebos.core.conta.model.SaldoCentroCosto;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * Saldos centros de costos
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateSaldoCentroCosto.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@Auditable
public class HibernateSaldoCentroCosto extends HibernateContabilidad implements SaldoCentroCosto {

	private static final long serialVersionUID = 5615088107461153660L;

	protected static final String TABLE_NAME = "SALDO_CENTRO_COSTO";
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
	 * Periodo contable
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernatePeriodo.class)
    @JoinColumn(name = "id_periodo", nullable = false)
	private Periodo periodo;
	
	/**
	 * {@link HibernateCentroCosto}
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernateCentroCosto.class)
    @JoinColumn(name = "id_centro_costo", nullable = false)
	private CentroCosto centroCosto;

	/**
	 * Tipo de saldo
	 */
	@Column(name = "tipoSaldo", nullable = false, length = 3)
    @Type(type = HibernateSaldoCentroCosto.TipoSaldo.TYPE)
    private SaldoCentroCosto.TipoSaldo tipoSaldo = SaldoCentroCosto.TipoSaldo.CENTRO;
	
	/**
	 * Valor inicial de la cuenta en el periodo
	 */
	@Column(name = "saldo_inicial", nullable = false, length = 16, precision = 2)
	private BigDecimal saldoInicial = BigDecimal.ZERO;
	
	/**
	 * Valor deudor de la cuenta
	 */
	@Column(name = "valor_debe", nullable = false, length = 16, precision = 2)
	private BigDecimal valorDebe = BigDecimal.ZERO;
	
	/**
	 * Valor acreedor de la cuenta
	 */
	@Column(name = "valor_haber", nullable = false, length = 16, precision = 2)
	private BigDecimal valorHaber = BigDecimal.ZERO;
	
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	/**
	 * Estado del {@link SaldoCentroCosto}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
    
}
