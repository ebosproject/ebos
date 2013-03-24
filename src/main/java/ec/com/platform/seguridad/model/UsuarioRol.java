package ec.com.platform.seguridad.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "SEGTUSUARIO_ROL", schema = "PLTFSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class UsuarioRol extends GenericSeguridad<UsuarioRol> {

	private static final long serialVersionUID = -1368865964860468015L;

	@Id
	@SequenceGenerator(name = "SEGTUSUARIO_ROL_ID_GENERATOR", sequenceName = "PLTFSEGU.SEGSUSUARIO_ROL")
	@GeneratedValue(generator = "SEGTUSUARIO_ROL_ID_GENERATOR")
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
            
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
    
}
