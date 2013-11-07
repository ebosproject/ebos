package ec.com.ebos.hibernate.admin.model;

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

import ec.com.ebos.core.admin.model.Administracion;
import ec.com.ebos.core.admin.model.Objeto;
import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.security.model.RolOpcion;
import ec.com.ebos.hibernate.admin.model.field.Opcion_;
import ec.com.ebos.hibernate.security.model.HibernateRolOpcion;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateOpcion.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateOpcion extends HibernateAdministracion implements Opcion {
    
	private static final long serialVersionUID = 6962443015587297421L;

	protected static final String TABLE_NAME = "OPCION";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@Embedded
	private Auditoria auditoria;	
	
	@ManyToOne(targetEntity = HibernateOpcion.class)
	@JoinColumn(name = "id_padre")
    private Opcion padre;
	
	@ManyToOne(targetEntity = HibernateObjeto.class)
	@JoinColumn(name = "id_objeto")
    private Objeto objeto;
	    
    @Column(name="nombre", length=30, unique=true, nullable=false)
    private String nombre;
    
    @Column(name="descripcion", length=500)
    private String descripcion;
    
    @Column(name="etiqueta", length=30, nullable=false)
    private String etiqueta;
    
    @Column(name="target", length=200, nullable=false)
    private String target;
    
    @Column(name="icono", length=200)
    private String icono;

    @Column(name=Opcion_.width, length=4)
    private int width = 700;
    
    @Column(name=Opcion_.height, length=4)
    private int height = 450;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "opcion", fetch= FetchType.LAZY, targetEntity = HibernateRolOpcion.class)
    private Set<RolOpcion> rolOpcionList = new HashSet<RolOpcion>(0);

    @OneToMany(mappedBy = "padre", fetch= FetchType.LAZY, targetEntity = HibernateOpcion.class)
    private Set<Opcion> opcionList = new HashSet<Opcion>(0);

    @Transient
    private String beanName;
}
