package ec.com.ebos.conta.model;

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

import ec.com.ebos.conta.model.field.CuentaContable_;
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
@Table(name = TipoCuenta.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class TipoCuenta extends Contabilidad<TipoCuenta> {

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
    @Type(type = TipoCuenta.Tipo.TYPE)
    private TipoCuenta.Tipo tipo = TipoCuenta.Tipo.BALANCE;
	
	/**
	 * Naturaleza del {@link TipoCuenta}
	 */
	@Column(name = "naturaleza", nullable = false, length = 1)
    @Type(type = CuentaContable.Naturaleza.TYPE)
    private CuentaContable.Naturaleza naturaleza = CuentaContable.Naturaleza.DEUDORA;
	
	
	@OneToMany(mappedBy = CuentaContable_.tipoCuenta, fetch = FetchType.LAZY)
    private Set<CuentaContable> cuentaContableList = new HashSet<CuentaContable>(0);
	
	/**
     * <strong>Tipos de Cuenta</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> B: Balance<br> P: Perdida o Ganancias<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum Tipo implements StringValuedEnum<Tipo> {

        BALANCE("B"),
        PERDIDA_GANANCIA("P");

        public static class Type extends StringValuedEnumType<Tipo> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conta.model.TipoCuenta$Tipo$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Tipo(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Tipo> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Tipo.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Tipo> LIST = Arrays.asList(Tipo.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isBalance() {
            return this.equals(BALANCE);
        }

        public boolean isPerdidaOGanancia() {
            return this.equals(PERDIDA_GANANCIA);
        }
    }
    
}
