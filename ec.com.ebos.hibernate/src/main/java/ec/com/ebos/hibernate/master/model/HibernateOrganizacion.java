package ec.com.ebos.hibernate.master.model;

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
import ec.com.ebos.core.conta.model.CentroCostoEmpresa;
import ec.com.ebos.core.conta.model.CuentaCentro;
import ec.com.ebos.core.conta.model.CuentaContableEmpresa;
import ec.com.ebos.core.conta.model.Ejercicio;
import ec.com.ebos.core.master.model.Activo;
import ec.com.ebos.core.master.model.EmpresaPersona;
import ec.com.ebos.core.master.model.Master;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.master.model.Sucursal;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.HibernateCentroCostoEmpresa;
import ec.com.ebos.hibernate.conta.model.HibernateCuentaCentro;
import ec.com.ebos.hibernate.conta.model.HibernateCuentaContableEmpresa;
import ec.com.ebos.hibernate.conta.model.HibernateEjercicio;
import ec.com.ebos.hibernate.conta.model.field.CentroCostoEmpresa_;
import ec.com.ebos.hibernate.conta.model.field.CuentaCentro_;
import ec.com.ebos.hibernate.conta.model.field.CuentaContableEmpresa_;
import ec.com.ebos.hibernate.conta.model.field.Ejercicio_;
import ec.com.ebos.hibernate.master.model.field.Activo_;
import ec.com.ebos.hibernate.master.model.field.EmpresaPersona_;
import ec.com.ebos.hibernate.master.model.field.Sucursal_;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateOrganizacion.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateOrganizacion extends HibernateMaster implements Organizacion{

	private static final long serialVersionUID = 7508531917964868788L;

	protected static final String TABLE_NAME = "ORGANIZACION";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
		
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "imagen", nullable = false, length = 50)
	private String imagen;
	
	@ManyToOne(targetEntity = HibernatePersona.class)
	@JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = EmpresaPersona_.empresa, fetch= FetchType.LAZY, targetEntity = HibernateEmpresaPersona.class)
    private Set<EmpresaPersona> empresaPersonaList = new HashSet<EmpresaPersona>(0);
	
	@OneToMany(mappedBy = Sucursal_.empresa, fetch= FetchType.LAZY, targetEntity = HibernateSucursal.class)
    private Set<Sucursal> sucursalList = new HashSet<Sucursal>(0);	
	
	@OneToMany(mappedBy = Activo_.empresa, fetch= FetchType.LAZY, targetEntity = HibernateActivo.class)
    private Set<Activo> activoList = new HashSet<Activo>(0);
	
	@OneToMany(mappedBy = CentroCostoEmpresa_.empresa, fetch = FetchType.LAZY, targetEntity = HibernateCentroCostoEmpresa.class)
    private Set<CentroCostoEmpresa> centroCostoEmpresaList = new HashSet<CentroCostoEmpresa>(0);
	
	@OneToMany(mappedBy = CuentaCentro_.empresa, fetch = FetchType.LAZY, targetEntity = HibernateCuentaCentro.class)
    private Set<CuentaCentro> cuentaCentroList = new HashSet<CuentaCentro>(0);
	
	@OneToMany(mappedBy = CuentaContableEmpresa_.empresa, fetch = FetchType.LAZY, targetEntity = HibernateCuentaContableEmpresa.class)
    private Set<CuentaContableEmpresa> cuentaContableEmpresaList = new HashSet<CuentaContableEmpresa>(0);
	
	@OneToMany(mappedBy = Ejercicio_.empresa, fetch = FetchType.LAZY, targetEntity = HibernateEjercicio.class)
    private Set<Ejercicio> ejercicioList = new HashSet<Ejercicio>(0);
}
