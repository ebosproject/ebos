package ec.com.ebos.app.model;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.generic.model.Auditoria;
import ec.com.ebos.generic.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "APPTACTIVO", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class Activo extends GenericApp<Activo>{

	private static final long serialVersionUID = 7521526484197846192L;

	@Id
	@SequenceGenerator(name = "APPTACTIVO_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSACTIVO")
	@GeneratedValue(generator = "APPTACTIVO_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
        
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = "activo", fetch = FetchType.LAZY)
    private Set<ActivoCustodio> activoCustodioList = new HashSet<ActivoCustodio>(0);
	
	@OneToMany(mappedBy = "activo", fetch = FetchType.LAZY)
    private Set<Propiedad> propiedadList = new HashSet<Propiedad>(0);
}
