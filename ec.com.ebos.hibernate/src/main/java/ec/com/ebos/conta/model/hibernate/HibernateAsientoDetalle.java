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
import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.admin.model.hibernate.HibernateDocumento;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.Asiento;
import ec.com.ebos.conta.model.AsientoDetalle;
import ec.com.ebos.conta.model.CentroCosto;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.CuentaContable;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * Asientos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateAsientoDetalle.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateAsientoDetalle extends HibernateContabilidad implements AsientoDetalle {

	private static final long serialVersionUID = -2771940733623486993L;
	
	protected static final String TABLE_NAME = "ASIENTO_DETALLE";
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
	 * Asiento contable
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateAsiento.class)
	@JoinColumn(name = "id_asiento", nullable = false)
    private Asiento asiento;
	
	/**
	 * Cuenta contable
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
	@JoinColumn(name = "id_cuenta_contable", nullable = false)
    private CuentaContable cuentaContable;
	
	/**
	 * Centro de costo
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCentroCosto.class)
	@JoinColumn(name = "id_centro_costo")
    private CentroCosto centroCosto;
	
	/**
	 * Subcentro de costo
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCentroCosto.class)
	@JoinColumn(name = "id_subcentro_costo")
    private CentroCosto subcentroCosto;
	
	/**
	 * Documento
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateDocumento.class)
	@JoinColumn(name = "id_documento")
    private Documento documento;
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Monto deudor del detalle
	 */
    @Column(name = "valor_debe", nullable = false, length = 16, precision = 2)
    private BigDecimal valorDebe =  BigDecimal.ZERO;
    
    /**
	 * Monto acreedor del detalle
	 */
    @Column(name = "valor_haber", nullable = false, length = 16, precision = 2)
    private BigDecimal valorHaber =  BigDecimal.ZERO;
    
    /**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;

}
