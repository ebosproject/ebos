package ec.com.ebos.hibernate.conta.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.AsientoDetalle;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.CuentaCentro;
import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.CuentaContableEmpresa;
import ec.com.ebos.core.conta.model.Ejercicio;
import ec.com.ebos.core.conta.model.SaldoCuentaCentro;
import ec.com.ebos.core.conta.model.SaldoCuentaContable;
import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.field.AsientoDetalle_;
import ec.com.ebos.hibernate.conta.model.field.CuentaCentro_;
import ec.com.ebos.hibernate.conta.model.field.CuentaContableEmpresa_;
import ec.com.ebos.hibernate.conta.model.field.CuentaContable_;
import ec.com.ebos.hibernate.conta.model.field.Ejercicio_;
import ec.com.ebos.hibernate.conta.model.field.SaldoCuentaCentro_;
import ec.com.ebos.hibernate.conta.model.field.SaldoCuentaContable_;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.root.model.field.Entidad_;

/**
 * Plan de cuentas general
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateCuentaContable.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateCuentaContable extends HibernateContabilidad implements CuentaContable {

	private static final long serialVersionUID = 2646781225564626367L;
	
	protected static final String TABLE_NAME = "CUENTA_CONTABLE";
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
	 * Cuenta contable padre
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
	@JoinColumn(name = "id_padre")
    private CuentaContable padre;
	
	/**
	 * Tipo de cuenta
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateTipoCuenta.class)
	@JoinColumn(name = "id_tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;
	
	/**
	 * Codigo de la {@link HibernateCuentaContable}
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght, unique = true)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la cuenta
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = 150) //TODO (epa): Consultar si no se usa default Entidad.DESCRIPCION_LENGTH=100
	private String descripcion;
	
	@Column(name="codigo_formato", nullable = false, length = 50)
	private String codigoFormato;
	
	/**
	 * Nivel de la cuenta dentro de la jerarquia
	 */
	@Column(name="nivel", nullable = false, length = 2)
	private int nivel = 1;
	
	/**
	 * Indica si es cuenta final
	 */
	@Column(name="terminante", nullable = false, length = 2)
	private int terminante = 0;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	/**
	 * Naturaleza de la {@link HibernateCuentaContable}
	 */
	@Column(name = "naturaleza", nullable = false, length = 1)
    @Type(type = CuentaContable.Naturaleza.TYPE)
    private CuentaContable.Naturaleza naturaleza = CuentaContable.Naturaleza.DEUDORA;
	
	/**
	 * Indica si pide o no centro de costo
	 */
	@Column(name = "pide_centro_costo", nullable = false)
	private boolean pideCentroCosto = false;
	
	/**
	 * Tipo de proceso en la que se puede usar la {@link HibernateCuentaContable}
	 */
	@Column(name = "tipo_proceso", nullable = false, length = 1)
    @Type(type = CuentaContable.TipoProceso.TYPE)
    private CuentaContable.TipoProceso tipoProceso = CuentaContable.TipoProceso.MIXTA;
	
	
	/**
	 * Estado de la {@link HibernateCuentaContable}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
	@OneToMany(mappedBy = AsientoDetalle_.cuentaContable, fetch = FetchType.LAZY, targetEntity = HibernateAsientoDetalle.class)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
	@OneToMany(mappedBy = CuentaCentro_.cuentaContable, fetch = FetchType.LAZY, targetEntity = HibernateCuentaCentro.class)
    private Set<CuentaCentro> cuentaCentroList = new HashSet<CuentaCentro>(0);
	
	@OneToMany(mappedBy = CuentaContable_.padre, fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
    private Set<CuentaContable> cuentaContableList = new HashSet<CuentaContable>(0);
	
	@OneToMany(mappedBy = CuentaContableEmpresa_.cuentaContable, fetch = FetchType.LAZY, targetEntity = HibernateCuentaContableEmpresa.class)
    private Set<CuentaContableEmpresa> cuentaContableEmpresaList = new HashSet<CuentaContableEmpresa>(0);
	
	@OneToMany(mappedBy = Ejercicio_.cuentaUtilidad, fetch = FetchType.LAZY, targetEntity = HibernateEjercicio.class)
    private Set<Ejercicio> ejercicioList = new HashSet<Ejercicio>(0);
	
	@OneToMany(mappedBy = SaldoCuentaCentro_.cuentaContable, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaCentro.class)
    private Set<SaldoCuentaCentro> saldoCuentaCentroList = new HashSet<SaldoCuentaCentro>(0);
	
	@OneToMany(mappedBy = SaldoCuentaContable_.cuentaContable, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaContable.class)
    private Set<SaldoCuentaContable> saldoCuentaContableList = new HashSet<SaldoCuentaContable>(0);
	
}
