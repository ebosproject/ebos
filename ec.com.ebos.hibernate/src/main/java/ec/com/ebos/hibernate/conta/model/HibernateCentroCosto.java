package ec.com.ebos.hibernate.conta.model;

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

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.AsientoDetalle;
import ec.com.ebos.core.conta.model.CentroCosto;
import ec.com.ebos.core.conta.model.CentroCostoEmpresa;
import ec.com.ebos.core.conta.model.CentroSubcentro;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.DocumentoDistribucion;
import ec.com.ebos.core.conta.model.SaldoCentroCosto;
import ec.com.ebos.core.conta.model.SaldoCuentaCentro;
import ec.com.ebos.core.conta.model.TipoCentroCosto;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.field.AsientoDetalle_;
import ec.com.ebos.hibernate.conta.model.field.CentroCostoEmpresa_;
import ec.com.ebos.hibernate.conta.model.field.CentroCosto_;
import ec.com.ebos.hibernate.conta.model.field.CentroSubcentro_;
import ec.com.ebos.hibernate.conta.model.field.DocumentoDistribucion_;
import ec.com.ebos.hibernate.conta.model.field.SaldoCentroCosto_;
import ec.com.ebos.hibernate.conta.model.field.SaldoCuentaCentro_;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.root.model.field.Entidad_;

/**
 * Se definen los centros de costo a usarse por el grupo de empresas
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateCentroCosto.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateCentroCosto extends HibernateContabilidad implements CentroCosto {

	private static final long serialVersionUID = -567856834893258749L;
	
	protected static final String TABLE_NAME = "CENTRO_COSTO";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	/**
	 * Centro costo padre
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCentroCosto.class)
	@JoinColumn(name = "id_padre")
    private CentroCosto padre;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateTipoCentroCosto.class)
	@JoinColumn(name = "id_tipo_centro_costo")
    private TipoCentroCosto tipoCentroCosto;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	/**
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght, unique = true)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = 150) //TODO (epa): Consultar si no se usa default Entidad.DESCRIPCION_LENGTH=100
	private String descripcion;
	
	@Column(name="codigo_formato", nullable = false, length = 50)
	private String codigoFormato;
	
	@Column(name="nivel", nullable = false, length = 2)
	private int nivel = 1;
    
	@Column(name="terminante", nullable = false, length = 2)
	private int terminante = 0;
	
	@Column(name="pide_subcentro_costo", nullable = false)
	private boolean pideSubcentroCosto = false;
	
	/**
	 * Estado del {@link HibernateCentroCosto}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
	@OneToMany(mappedBy = AsientoDetalle_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateAsientoDetalle.class)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
	@OneToMany(mappedBy = AsientoDetalle_.subcentroCosto, fetch = FetchType.LAZY, targetEntity = HibernateAsientoDetalle.class)
    private Set<AsientoDetalle> asientoDetalleList2 = new HashSet<AsientoDetalle>(0);
	
	@OneToMany(mappedBy = CentroCosto_.padre, fetch = FetchType.LAZY, targetEntity = HibernateCentroCosto.class)
    private Set<CentroCosto> centroCostoList = new HashSet<CentroCosto>(0);
	
	@OneToMany(mappedBy = CentroCostoEmpresa_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateCentroCostoEmpresa.class)
    private Set<CentroCostoEmpresa> centroCostoEmpresaList = new HashSet<CentroCostoEmpresa>(0);
	
	@OneToMany(mappedBy = CentroSubcentro_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateCentroSubcentro.class)
    private Set<CentroSubcentro> centroSubcentroList = new HashSet<CentroSubcentro>(0);
	
	@OneToMany(mappedBy = CentroSubcentro_.subcentroCosto, fetch = FetchType.LAZY, targetEntity = HibernateCentroSubcentro.class)
    private Set<CentroSubcentro> centroSubcentroList2 = new HashSet<CentroSubcentro>(0);
	
	@OneToMany(mappedBy = DocumentoDistribucion_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateDocumentoDistribucion.class)
    private Set<DocumentoDistribucion> documentoDistribucionList = new HashSet<DocumentoDistribucion>(0);
	
	@OneToMany(mappedBy = DocumentoDistribucion_.subcentroCosto, fetch = FetchType.LAZY, targetEntity = HibernateDocumentoDistribucion.class)
    private Set<DocumentoDistribucion> documentoDistribucionList2 = new HashSet<DocumentoDistribucion>(0);
	
	@OneToMany(mappedBy = SaldoCentroCosto_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCentroCosto.class)
    private Set<SaldoCentroCosto> saldoCentroCostoList = new HashSet<SaldoCentroCosto>(0);
	
	@OneToMany(mappedBy = SaldoCuentaCentro_.centroCosto, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaCentro.class)
    private Set<SaldoCuentaCentro> saldoCuentaCentroList = new HashSet<SaldoCuentaCentro>(0);
	
	@OneToMany(mappedBy = SaldoCuentaCentro_.subcentroCosto, fetch = FetchType.LAZY, targetEntity = HibernateSaldoCuentaCentro.class)
    private Set<SaldoCuentaCentro> saldoCuentaCentroList2 = new HashSet<SaldoCuentaCentro>(0);
}
