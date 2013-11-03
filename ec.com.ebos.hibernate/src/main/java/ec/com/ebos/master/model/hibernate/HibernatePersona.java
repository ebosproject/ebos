package ec.com.ebos.master.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.master.model.DivisionGeografica;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.field.EmpresaPersona_;
import ec.com.ebos.master.model.field.Organizacion_;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.mse.model.field.Monaguillo_;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguillo;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.DateUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = HibernatePersona.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernatePersona extends Master<HibernatePersona> implements Persona{

	private static final long serialVersionUID = -2896367216397132540L;

	protected static final String TABLE_NAME = "PERSONA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = Persona_.nombres, nullable = false, length = 50)
	private String nombres;

	@Column(name = Persona_.apellidos, nullable = false, length = 50)
	private String apellidos;
	
	@Getter @Setter
	@Column(name = Persona_.nacimiento, nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date nacimiento;

	@Column(name = Persona_.genero, nullable = false, length = 1)
    @Type(type = Genero.TYPE)
    private Genero genero;
	
	@Column(name = Persona_.identificacion, unique = true, nullable = false, length = 20)
	private String identificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Persona_.id_ciudad)
    private DivisionGeografica ciudad;
	
	/**
	 *  Posibles configuraciones
	 *	@Type(type="org.hibernate.type.PrimitiveByteArrayBlobType")
	 *  @Column(name = "imagen", columnDefinition="bytea")
	 *	@Column(name = "imagen")
	 *  Postgresql types: oid y bytea
	 *  @see http://stackoverflow.com/questions/3677380/proper-hibernate-annotation-for-byte
	 *  @see http://www.zephid.dk/2008/08/09/oid-vs-bytea-in-postgresql/
	 *  @see http://amilasilva88.blogspot.com/2010/08/postgresql-bytea-and-oid.html
	 *  @see https://forum.hibernate.org/viewtopic.php?p=2432614 
	 *  Conclusion: oid mas eficiente que bytea
	 *  
	*/
	@Lob //Postgres default oid type
	@Column(name = Persona_.imagen)
	private byte[] imagen;
	
	@Column(name = Persona_.contentType, length = 15)
	private String contentType;
	
	@Column(name = Persona_.direccion, length = 100)
	private String direccion;
	
	@Column(name = Persona_.mail, length = 100)
	private String mail;
	
	@Column(name = Persona_.facebook, length = 30)
	private String facebook;

	@Column(name = Persona_.telefono, length = 25)
	private String telefono;
	
	@Column(name = Persona_.celular, length = 25)
	private String celular;
	
	@Column(name = Persona_.usuario, nullable = false)
	private boolean usuario = false;
	
	@Column(name = Persona_.cliente, nullable = false)
	private boolean cliente = false;
	
	@Column(name = Persona_.proveedor, nullable = false)
	private boolean proveedor = false;
	
	@Column(name = Persona_.empleado, nullable = false)
	private boolean empleado = false;
	
	@Column(name = Persona_.tipoIdentificacion, nullable = false, length = 1)
    @Type(type = TipoIdentificacion.TYPE)
    private TipoIdentificacion tipoIdentificacion;
	
	
	/**
	 * Numero de cedula, ruc o codigo de indentificacion
	 */
	@Column(name = Persona_.tipoPersona, nullable = false, length = 1)
	@Type(type = TipoPersona.TYPE)
    private TipoPersona tipoPersona;
	
	@Column(name = Persona_.estado, nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = Organizacion_.persona, fetch = FetchType.LAZY)
    private Set<HibernateOrganizacion> empresaList = new HashSet<HibernateOrganizacion>(0);
	
	@OneToMany(mappedBy = EmpresaPersona_.persona, fetch= FetchType.LAZY)
    private Set<HibernateEmpresaPersona> empresaPersonaList = new HashSet<HibernateEmpresaPersona>(0);
	
	@OneToMany(mappedBy = Monaguillo_.persona, fetch= FetchType.LAZY)
    private Set<HibernateMonaguillo> monagilloList = new HashSet<HibernateMonaguillo>(0);
	
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.master.model.Persona#getEdad()
	 */
	public int getEdad(){
		return DateUtils.calcularEdad(nacimiento);
	}
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.master.model.Persona#getFullName()
	 */
	public String getFullName(){
		return nombres +" "+ apellidos;
	}
	
}
