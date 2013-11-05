package ec.com.ebos.master.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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

import ec.com.ebos.conta.model.hibernate.HibernateDocumentoDistribucion;
import ec.com.ebos.master.model.Activo;
import ec.com.ebos.master.model.ActivoCategoria;
import ec.com.ebos.master.model.Categoria;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.root.model.Entidad;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = HibernateCategoria.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
public class HibernateCategoria extends HibernateMaster implements ActivoCategoria, Categoria {
    
	private static final long serialVersionUID = 2268813629003896997L;

	protected static final String TABLE_NAME = "CATEGORIA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;	

	@ManyToOne(targetEntity = HibernateCategoria.class)
	@JoinColumn(name = "id_padre")
    private ActivoCategoria padre;
	
	@Column(name = "codigo", unique = true, nullable = false, length = 20)
	private String codigo;
    
	@Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "categoria", fetch= FetchType.LAZY, targetEntity = HibernateActivo.class)
    private Set<Activo> activoList = new HashSet<Activo>(0);       
    
    @OneToMany(mappedBy = "padre", fetch= FetchType.LAZY, targetEntity = HibernateCategoria.class)
    private Set<Categoria> categoriaList = new HashSet<Categoria>(0);
}

