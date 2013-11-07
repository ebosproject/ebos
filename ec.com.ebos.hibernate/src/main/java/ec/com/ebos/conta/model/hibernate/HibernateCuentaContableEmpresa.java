package ec.com.ebos.conta.model.hibernate;

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
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.Contabilidad;
import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.CuentaContableEmpresa;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.master.model.hibernate.HibernateOrganizacion;

/**
 * {@link HibernateCuentaContable}(s) que puede usar una empresa({@link HibernateOrganizacion})
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateCuentaContableEmpresa.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateCuentaContableEmpresa extends HibernateContabilidad implements CuentaContableEmpresa {

	private static final long serialVersionUID = -9092708941710165749L;
	
	protected static final String TABLE_NAME = "CUENTA_CONTABLE_EMPRESA";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateCuentaContable.class)
	@JoinColumn(name = "id_cuenta_contable", nullable = false)
    private CuentaContable cuentaContable;		
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernateOrganizacion.class)
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
    
}
