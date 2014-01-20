package ec.com.ebos.hibernate.conta.model;

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

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.CuentaCentro;
import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.TipoCentroCosto;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.master.model.HibernateOrganizacion;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * Asiento detalle
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateCuentaCentro.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateCuentaCentro extends HibernateContabilidad implements CuentaCentro {

	private static final long serialVersionUID = 1943393643558976463L;
	
	protected static final String TABLE_NAME = "CUENTA_CENTRO";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateOrganizacion.class)
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
	@JoinColumn(name = "id_cuenta_contable", nullable = false)
    private CuentaContable cuentaContable;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateTipoCentroCosto.class)
	@JoinColumn(name = "id_tipo_centro_costo")
    private TipoCentroCosto tipoCentroCosto;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	/**
	 * Estado de la {@link HibernateCuentaCentro}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
    
}
