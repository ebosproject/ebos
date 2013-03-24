package ec.com.platform.app.model;

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
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = "APPTCATEGORIA", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false)
public class Categoria extends GenericApp<Categoria> {
    
	private static final long serialVersionUID = 2268813629003896997L;

	@Id
	@SequenceGenerator(name = "APPTCATEGORIA_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSCATEGORIA")
	@GeneratedValue(generator = "APPTCATEGORIA_ID_GENERATOR")
	private Long id;	

	@ManyToOne
	@JoinColumn(name = "padre_id")
    private Categoria padre;
	
	@Column(name = "codigo", unique = true, nullable = false, length = 20)
	private String codigo;
    
	@Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
	
    @Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
    
    @OneToMany(mappedBy = "categoria", fetch= FetchType.LAZY)
    private Set<Activo> activoList = new HashSet<Activo>(0);       
    
    @OneToMany(mappedBy = "padre", fetch= FetchType.LAZY)
    private Set<Categoria> categoriaList = new HashSet<Categoria>(0);
}

