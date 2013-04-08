package ec.com.ebos.seguridad.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.generic.model.Auditoria;
import ec.com.ebos.generic.model.Entidad;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "SEGTROL", schema = "EBOSSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class Rol extends GenericSeguridad<Rol> {
    
	private static final long serialVersionUID = 1684996463596485829L;

	@Id
	@SequenceGenerator(name = "SEGTROL_ID_GENERATOR", sequenceName = "EBOSSEGU.SEGSROL")
	@GeneratedValue(generator = "SEGTROL_ID_GENERATOR")
    private Long id;
    
	@Embedded
	private Auditoria auditoria;
	
    @Column(name="nombre", length=30, unique=true, nullable=false)
    private String nombre;
    
    @Column(name="descripcion", length=500)
    private String descripcion;

    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)    
    private Set<RolOpcion> rolOpcionList = new HashSet<RolOpcion>(0);
    
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)    
    private Set<UsuarioRol> usuarioRolList = new HashSet<UsuarioRol>(0);
            
}
