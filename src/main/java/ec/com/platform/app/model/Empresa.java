package ec.com.platform.app.model;

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
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "APPTEMPRESA", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class Empresa extends GenericApp<Empresa>{

	private static final long serialVersionUID = 7508531917964868788L;

	@Id
	@SequenceGenerator(name = "APPTEMPRESA_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSEMPRESA")
	@GeneratedValue(generator = "APPTEMPRESA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "imagen", nullable = false, length = 50)
	private String imagen;
	
	@ManyToOne
	@JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;
	
	@Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
	
	@OneToMany(mappedBy = "empresa", fetch= FetchType.LAZY)
    private Set<EmpresaPersona> empresaPersonaList = new HashSet<EmpresaPersona>(0);
	
	@OneToMany(mappedBy = "empresa", fetch= FetchType.LAZY)
    private Set<Sucursal> sucursalList = new HashSet<Sucursal>(0);	
	
	@OneToMany(mappedBy = "empresa", fetch= FetchType.LAZY)
    private Set<Activo> activoList = new HashSet<Activo>(0);
}
