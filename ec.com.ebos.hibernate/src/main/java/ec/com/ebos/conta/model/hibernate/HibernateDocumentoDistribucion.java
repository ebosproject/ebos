package ec.com.ebos.conta.model.hibernate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.CentroCosto;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.DocumentoDistribucion;
import ec.com.ebos.conta.model.DocumentoDistrubucion;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * Distribucion de egresos
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateDocumentoDistribucion.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateDocumentoDistribucion extends Contabilidad<HibernateDocumentoDistribucion> implements DocumentoDistrubucion, DocumentoDistribucion {

	private static final long serialVersionUID = -1204172994571329360L;
	
	protected static final String TABLE_NAME = "DOCUMENTO_DISTRIBUCION";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Centro de Costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_costo", nullable = false)
    private CentroCosto centroCosto;
	
	/**
	 * Subcentro de Costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_subcentro_costo", nullable = false)
    private CentroCosto subcentroCosto;
	
	/**
	 * Documento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_documento")
    private Documento documento;
	
	@Column(name = "valor", nullable = false, length = 16, precision = 2)
	private BigDecimal valor = BigDecimal.ZERO;
	
	/**
	 * Estado de la relacion
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
    
}
