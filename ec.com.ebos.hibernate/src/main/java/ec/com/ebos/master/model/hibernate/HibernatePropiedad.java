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
import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.master.model.PropiedadValor;
import ec.com.ebos.root.model.Entidad;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = HibernatePropiedad.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
public class HibernatePropiedad extends HibernateMaster implements Propiedad {
    
	private static final long serialVersionUID = -5344259206776358470L;

	protected static final String TABLE_NAME = "PROPIEDAD";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;	

	@ManyToOne(targetEntity = HibernateActivo.class)
	@JoinColumn(name = "id_activo", nullable = false)
	private Activo activo;
	
	@ManyToOne(targetEntity = HibernateCategoria.class)
	@JoinColumn(name = "id_categoria", nullable = false)
    private ActivoCategoria categoria;
	
	@Column(name = "tipoDato", nullable = false, length = 1)
    @Type(type = HibernatePropiedad.TipoDato.TYPE)
    private HibernatePropiedad.TipoDato tipoDato;
	
	@Column(name = "valor", length = 50)
	private String valor;
	
	@Column(name = "valorDefecto", length = 50)
	private String valorDefecto;
	
    @Column(name = "longitud")
	private int longitud;
    
    @Column(name="requerido")
    private boolean requerido;
    
    @Column(name="lista")
    private boolean lista;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "propiedad", fetch= FetchType.LAZY, targetEntity = HibernatePropiedadValor.class)
    private Set<PropiedadValor> propiedadValorList = new HashSet<PropiedadValor>(0);
    
}

