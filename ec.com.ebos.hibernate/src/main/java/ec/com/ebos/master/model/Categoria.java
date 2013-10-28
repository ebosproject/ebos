package ec.com.ebos.master.model;

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

import ec.com.ebos.root.model.hibernate.HibernateEntidad;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = Categoria.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
public class Categoria extends Master<Categoria> {
    
	private static final long serialVersionUID = 2268813629003896997L;

	protected static final String TABLE_NAME = "CATEGORIA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;	

	@ManyToOne
	@JoinColumn(name = "id_padre")
    private Categoria padre;
	
	@Column(name = "codigo", unique = true, nullable = false, length = 20)
	private String codigo;
    
	@Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = HibernateEntidad.Estado.TYPE)
    private HibernateEntidad.Estado estado;
    
    @OneToMany(mappedBy = "categoria", fetch= FetchType.LAZY)
    private Set<Activo> activoList = new HashSet<Activo>(0);       
    
    @OneToMany(mappedBy = "padre", fetch= FetchType.LAZY)
    private Set<Categoria> categoriaList = new HashSet<Categoria>(0);
}

