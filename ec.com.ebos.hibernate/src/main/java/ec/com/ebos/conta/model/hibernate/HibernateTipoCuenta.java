package ec.com.ebos.conta.model.hibernate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.TipoCuenta;
import ec.com.ebos.conta.model.hibernate.field.CuentaContable_;
import ec.com.ebos.root.model.field.Entidad_;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * Tipos de cuenta contable
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateTipoCuenta.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Auditable
public class HibernateTipoCuenta extends Contabilidad<HibernateTipoCuenta> implements TipoCuenta {

	private static final long serialVersionUID = 948691083807866461L;
	
	protected static final String TABLE_NAME = "TIPO_CUENTA";
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
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;	
	
	/**
	 * Tipo de tipo de cuenta
	 */
	@Column(name = "tipo", nullable = false, length = 1)
    @Type(type = HibernateTipoCuenta.Tipo.TYPE)
    private HibernateTipoCuenta.Tipo tipo = ec.com.ebos.conta.model.Tipo.BALANCE;
	
	/**
	 * Naturaleza del {@link HibernateTipoCuenta}
	 */
	@Column(name = "naturaleza", nullable = false, length = 1)
    @Type(type = ec.com.ebos.conta.model.Naturaleza.TYPE)
    private HibernateCuentaContable.Naturaleza naturaleza = ec.com.ebos.conta.model.Naturaleza.DEUDORA;
	
	
	@OneToMany(mappedBy = CuentaContable_.tipoCuenta, fetch = FetchType.LAZY)
    private Set<HibernateCuentaContable> cuentaContableList = new HashSet<HibernateCuentaContable>(0);
	
}
