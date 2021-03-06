package ec.com.ebos.hibernate.conta.model;

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

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.Ejercicio;
import ec.com.ebos.core.conta.model.Periodo;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.field.Periodo_;
import ec.com.ebos.hibernate.master.model.HibernateOrganizacion;
import ec.com.ebos.hibernate.root.model.field.Entidad_;

/**
 * Ejercicio fiscal
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateEjercicio.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateEjercicio extends HibernateContabilidad implements Ejercicio {

	private static final long serialVersionUID = 5615088107461153660L;

	protected static final String TABLE_NAME = "EJERCICIO";
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
	 * Empresa
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateOrganizacion.class)
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
	
	/**
	 * {@link HibernateCuentaContable} que almacenara la utilidad del ejercicio
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
	@JoinColumn(name = "id_cuenta_utilidad", nullable = false)
    private CuentaContable cuentaUtilidad;
	
	/**
	 * Valor de la utilidad del ejercicio 
	 */
	@Column(name = "valor", nullable = false, length = 16, precision = 2)
	private BigDecimal valor = BigDecimal.ZERO;
	
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
	 * Estado del {@link HibernateEjercicio}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
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
    
	@OneToMany(mappedBy = Periodo_.ejercicio, fetch = FetchType.LAZY, targetEntity = HibernatePeriodo.class)
    private Set<Periodo> periodoList = new HashSet<Periodo>(0);
	
	@OneToMany(mappedBy = Periodo_.periodoFiscalPais, fetch = FetchType.LAZY, targetEntity = HibernatePeriodo.class)
    private Set<Periodo> periodoList2 = new HashSet<Periodo>(0);
}
