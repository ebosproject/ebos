package ec.com.ebos.seguridad.model;

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

import ec.com.ebos.generic.model.Auditoria;
import ec.com.ebos.generic.model.Entidad;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "SEGTOPCION", schema = "EBOSSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class Opcion extends GenericSeguridad<Opcion> {
    
	private static final long serialVersionUID = 6962443015587297421L;

	@Id
	@SequenceGenerator(name = "SEGTOPCION_ID_GENERATOR", sequenceName = "EBOSSEGU.SEGSOPCION")
	@GeneratedValue(generator = "SEGTOPCION_ID_GENERATOR")
    private Long id;

	@Embedded
	private Auditoria auditoria;	
	
	@ManyToOne
	@JoinColumn(name = "padre_id")
    private Opcion padre;
	
	@ManyToOne
	@JoinColumn(name = "objeto_id")
    private Objeto objeto;
    
    @Column(name="nombre", length=30, unique=true, nullable=false)
    private String nombre;
    
    @Column(name="descripcion", length=500)
    private String descripcion;
    
    @Column(name="etiqueta", length=20, nullable=false)
    private String etiqueta;
    
    @Column(name="target", length=200, nullable=false)
    private String target;
    
    @Column(name="icono", length=200)
    private String icono;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "opcion", fetch= FetchType.LAZY)
    private Set<RolOpcion> rolOpcionList = new HashSet<RolOpcion>(0);

    @OneToMany(mappedBy = "padre", fetch= FetchType.LAZY)
    private Set<Opcion> opcionList = new HashSet<Opcion>(0);

    @Transient
    private String beanName;
}
