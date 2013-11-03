package ec.com.ebos.conta.model.hibernate;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.CentroCosto;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.CuentaContable;
import ec.com.ebos.conta.model.Periodo;
import ec.com.ebos.conta.model.SaldoCuentaCentro;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * Saldo cuenta centros de costo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateSaldoCuentaCentro.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateSaldoCuentaCentro extends Contabilidad<HibernateSaldoCuentaCentro> implements SaldoCuentaCentro {

	private static final long serialVersionUID = 1433992189978133926L;
	
	protected static final String TABLE_NAME = "SALDO_CUENTA_CENTRO";
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
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false)
	private Periodo periodo;
	
	/**
	 * Cuenta contable
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_contable", nullable = false)
	private CuentaContable cuentaContable;
	
	/**
	 * Centro de costos
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_centro_costo", nullable = false)
	private CentroCosto centroCosto;
	
	/**
	 * Subcentro de costos
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_subcentro_costo")
	private CentroCosto subcentroCosto;
	
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
	
	/**
	 * Saldo de la cuenta en el periodo
	 */
	@Column(name = "saldo", nullable = false, length = 16, precision = 2)
	private BigDecimal saldo = BigDecimal.ZERO;
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Estado del {@link HibernateSaldoCuentaCentro}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;	
    
}
