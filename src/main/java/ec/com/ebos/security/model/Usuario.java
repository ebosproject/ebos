package ec.com.ebos.security.model;

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
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.appl.model.EmpresaPersona;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "SEGTUSUARIO", schema = "EBOSSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class Usuario extends Security<Usuario> {

	private static final long serialVersionUID = 5615088107461153660L;

	@Id
	@SequenceGenerator(name = "SEGTUSUARIO_ID_GENERATOR", sequenceName = "EBOSSEGU.SEGSUSUARIO")
	@GeneratedValue(generator = "SEGTUSUARIO_ID_GENERATOR")
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresapersona_id", nullable = false)
    private EmpresaPersona empresaPersona;
    
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;
    
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @Column(name = "tema", length = 50)
    private String tema;
    
    @Column(name = "maxoptions")
    private int maxOptions;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<UsuarioRol> usuarioRolList = new HashSet<UsuarioRol>(0);

    @Transient
    private String newpassword;
    
    @Transient
    private String confpassword;	
    
}
