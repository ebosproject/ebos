package ec.com.ebos.conta.model.hibernate;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.conta.model.hibernate.field.SaldoCentroCosto_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCuentaCentro_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCuentaContable_;
import ec.com.ebos.conta.model.hibernate.field.SaldoRetencion_;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.Ejercicio;
import ec.com.ebos.core.conta.model.Periodo;
import ec.com.ebos.core.conta.model.SaldoCentroCosto;
import ec.com.ebos.core.conta.model.SaldoCuentaCentro;
import ec.com.ebos.core.conta.model.SaldoCuentaContable;
import ec.com.ebos.core.conta.model.SaldoRetencion;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.root.model.hibernate.field.Entidad_;


/**
 * Periodos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernatePeriodo.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernatePeriodo extends HibernateContabilidad implements Periodo {

	private static final long serialVersionUID = -747394377074840493L;
	
	protected static final String TABLE_NAME = "PERIODO";
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
	 * Ejercicio contable
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernateEjercicio.class)
    @JoinColumn(name = "id_ejercicio", nullable = false)
	private Ejercicio ejercicio;
	
	/**
	 * Periodo fiscal pais
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernateEjercicio.class)
    @JoinColumn(name = "id_periodo_fiscal_pais")
	private Ejercicio periodoFiscalPais;
	
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

	/**
	 * Valor de la utilidad del ejercicio 
	 */
	@Column(name = "valor_utilidad", nullable = false, length = 16, precision = 2)
	private BigDecimal valorUtilidad = BigDecimal.ZERO;
	
	/**
	 * Fecha de incio del ejercicio
	 */
	@Column(name = "fecha_inicial")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicial;
	
	/**
	 * Fecha final del ejercicio
	 */
	@Column(name = "fecha_final", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date creacion;
    
	/**
	 * Estado del {@link HibernatePeriodo}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Periodo.Estado estado = Periodo.Estado.PENDIENTE;
	
	
	@OneToMany(mappedBy = SaldoCentroCosto_.periodo, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCentroCosto.class)
    private Set<SaldoCentroCosto> saldoCentroCostoList = new HashSet<SaldoCentroCosto>(0);
	
	@OneToMany(mappedBy = SaldoCuentaCentro_.periodo, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaCentro.class)
    private Set<SaldoCuentaCentro> saldoCuentaCentroList = new HashSet<SaldoCuentaCentro>(0);
	
	@OneToMany(mappedBy = SaldoCuentaContable_.periodo, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaContable.class)
    private Set<SaldoCuentaContable> saldoCuentaContableList = new HashSet<SaldoCuentaContable>(0);
	
	@OneToMany(mappedBy = SaldoRetencion_.periodo, fetch = FetchType.LAZY, targetEntity = HibernateSaldoRetencion.class)
    private Set<SaldoRetencion> saldoRetencionList = new HashSet<SaldoRetencion>(0);

}
