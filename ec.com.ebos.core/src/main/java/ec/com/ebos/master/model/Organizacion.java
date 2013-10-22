package ec.com.ebos.master.model;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.CentroCostoEmpresa;
import ec.com.ebos.conta.model.CuentaCentro;
import ec.com.ebos.conta.model.CuentaContableEmpresa;
import ec.com.ebos.conta.model.Ejercicio;
import ec.com.ebos.conta.model.field.CentroCostoEmpresa_;
import ec.com.ebos.conta.model.field.CuentaCentro_;
import ec.com.ebos.conta.model.field.CuentaContableEmpresa_;
import ec.com.ebos.conta.model.field.Ejercicio_;
import ec.com.ebos.master.model.field.Activo_;
import ec.com.ebos.master.model.field.EmpresaPersona_;
import ec.com.ebos.master.model.field.Sucursal_;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = Organizacion.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class Organizacion extends Master<Organizacion>{

	private static final long serialVersionUID = 7508531917964868788L;

	protected static final String TABLE_NAME = "ORGANIZACION";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "imagen", nullable = false, length = 50)
	private String imagen;
	
	@ManyToOne
	@JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = EmpresaPersona_.empresa, fetch= FetchType.LAZY)
    private Set<EmpresaPersona> empresaPersonaList = new HashSet<EmpresaPersona>(0);
	
	@OneToMany(mappedBy = Sucursal_.empresa, fetch= FetchType.LAZY)
    private Set<Sucursal> sucursalList = new HashSet<Sucursal>(0);	
	
	@OneToMany(mappedBy = Activo_.empresa, fetch= FetchType.LAZY)
    private Set<Activo> activoList = new HashSet<Activo>(0);
	
	@OneToMany(mappedBy = CentroCostoEmpresa_.empresa, fetch = FetchType.LAZY)
    private Set<CentroCostoEmpresa> centroCostoEmpresaList = new HashSet<CentroCostoEmpresa>(0);
	
	@OneToMany(mappedBy = CuentaCentro_.empresa, fetch = FetchType.LAZY)
    private Set<CuentaCentro> cuentaCentroList = new HashSet<CuentaCentro>(0);
	
	@OneToMany(mappedBy = CuentaContableEmpresa_.empresa, fetch = FetchType.LAZY)
    private Set<CuentaContableEmpresa> cuentaContableEmpresaList = new HashSet<CuentaContableEmpresa>(0);
	
	@OneToMany(mappedBy = Ejercicio_.empresa, fetch = FetchType.LAZY)
    private Set<Ejercicio> ejercicioList = new HashSet<Ejercicio>(0);
}
