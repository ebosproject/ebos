package ec.com.ebos.admin.model.hibernate;

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

import ec.com.ebos.admin.model.Administracion;
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = HibernateObjeto.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateObjeto extends HibernateAdministracion implements Objeto {
    
	private static final long serialVersionUID = -3052521057254508069L;

	protected static final String TABLE_NAME = "OBJETO";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@Embedded
	private Auditoria auditoria;	
    
    @Column(name="codigo", length=30, unique=true, nullable=false)
    private String codigo;
    
    @Column(name="descripcion", length=150)
    private String descripcion;
    
    @Column(name = "tipo", nullable = false, length = 1)
    @Type(type = Objeto.TipoObjeto.TYPE)
    private Objeto.TipoObjeto tipo;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;

    @OneToMany(mappedBy = "objeto", fetch=FetchType.LAZY)
    private Set<Opcion> opcionList = new HashSet<Opcion>(0);

	@Override
	public Object getValue() {
		return codigo;
	}

	@Override
	public String getLabel() {
		return descripcion;
	}
    
}
