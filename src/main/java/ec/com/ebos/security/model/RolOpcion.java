package ec.com.ebos.security.model;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "SEGTROL_OPCION", schema = "EBOSSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class RolOpcion extends Security<RolOpcion> {

	private static final long serialVersionUID = -6703784659046763976L;

	@Id
	@SequenceGenerator(name = "SEGTROL_OPCION_ID_GENERATOR", sequenceName = "EBOSSEGU.SEGSROL_OPCION")
	@GeneratedValue(generator = "SEGTROL_OPCION_ID_GENERATOR")
    private Long id;

	@Embedded
	private Auditoria auditoria;
	
	@ManyToOne
	@JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
        
    @ManyToOne
    @JoinColumn(name = "opcion_id", nullable = false)
    private Opcion opcion;
    
    @Column(name="crear")
    private boolean crear;
    
    @Column(name="editar")
    private boolean editar;
    
    @Column(name="eliminar")
    private boolean eliminar;
    
    @Column(name="exportar")
    private boolean exportar;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
}
