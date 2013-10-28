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
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.root.model.hibernate.field.Entidad_;
import ec.com.ebos.security.model.Usuario;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = EmpresaPersona.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class EmpresaPersona extends Master<EmpresaPersona>{

	private static final long serialVersionUID = 6960552970253412538L;

	protected static final String TABLE_NAME = "EMPRESA_PERSONA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;
	
	@Column(name = Entidad_.estado, nullable = false, length = 1)
    @Type(type = HibernateEntidad.Estado.TYPE)
    private HibernateEntidad.Estado estado;
	
	@OneToMany(mappedBy = "empresaPersona", fetch = FetchType.LAZY)
    private Set<Usuario> usuarioList = new HashSet<Usuario>(0);
	
	@OneToMany(mappedBy = "empresaPersona", fetch = FetchType.LAZY)
    private Set<ActivoCustodio> activoCustodioList = new HashSet<ActivoCustodio>(0);
	
}
