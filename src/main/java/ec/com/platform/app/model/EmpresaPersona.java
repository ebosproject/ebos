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

import org.hibernate.annotations.Type;

import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Entidad;
import ec.com.platform.seguridad.model.Usuario;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "APPTEMPRESA_PERSONA", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class EmpresaPersona extends GenericApp<EmpresaPersona>{

	private static final long serialVersionUID = 6960552970253412538L;

	@Id
	@SequenceGenerator(name = "APPTEMPRESA_PERSONA_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSEMPRESA_PERSONA")
	@GeneratedValue(generator = "APPTEMPRESA_PERSONA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = "empresaPersona", fetch = FetchType.LAZY)
    private Set<Usuario> usuarioList = new HashSet<Usuario>(0);
	
	@OneToMany(mappedBy = "empresaPersona", fetch = FetchType.LAZY)
    private Set<ActivoCustodio> activoCustodioList = new HashSet<ActivoCustodio>(0);
	
}
