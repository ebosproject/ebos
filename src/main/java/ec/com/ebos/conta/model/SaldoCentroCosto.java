package ec.com.ebos.conta.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.conta.resources.ContaMensajes;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * Saldos centros de costos
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = SaldoCentroCosto.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class SaldoCentroCosto extends Contabilidad<SaldoCentroCosto> {

	private static final long serialVersionUID = 5615088107461153660L;

	protected static final String TABLE_NAME = "SALDO_CENTRO_COSTO";
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
	 * Periodo contable
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false)
	private Periodo periodo;
	
	/**
	 * {@link CentroCosto}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_centro_costo", nullable = false)
	private CentroCosto centroCosto;

	/**
	 * Tipo de saldo
	 */
	@Column(name = "tipoSaldo", nullable = false, length = 3)
    @Type(type = SaldoCentroCosto.TipoSaldo.TYPE)
    private SaldoCentroCosto.TipoSaldo tipoSaldo = SaldoCentroCosto.TipoSaldo.CENTRO;
	
	/**
	 * Valor inicial de la cuenta en el periodo
	 */
	@Column(name = "saldo_inicial", nullable = false, length = 16, precision = 2)
	private BigDecimal saldoInicial = BigDecimal.ZERO;
	
	/**
	 * Valor deudor de la cuenta
	 */
	@Column(name = "valor_debe", nullable = false, length = 16, precision = 2)
	private BigDecimal valorDebe = BigDecimal.ZERO;
	
	/**
	 * Valor acreedor de la cuenta
	 */
	@Column(name = "valor_haber", nullable = false, length = 16, precision = 2)
	private BigDecimal valorHaber = BigDecimal.ZERO;
	
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Estado del {@link SaldoCentroCosto}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
		
    /**
     * <strong>Tipo saldo de un {@link SaldoCentroCosto}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> CTR: Centro<br> SCT: Subcentro<br> FLJ: Flujo de caja<br> </td></tr>
     * </table>
     *
     * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum TipoSaldo implements StringValuedEnum<TipoSaldo> {
    	
        CENTRO("CTR"),
        SUBCENTRO("SCT"),
        FLUJO_CAJA("FLJ");

        public static class Type extends StringValuedEnumType<TipoSaldo> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conta.model.SaldoCentroCosto$TipoSaldo$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private TipoSaldo(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, TipoSaldo> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoSaldo.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoSaldo> LIST = Arrays.asList(TipoSaldo.values());

        @Override
        public String getLabel() {
            return ContaMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isCentro() {
            return this.equals(CENTRO);
        }

        public boolean isSubcentro() {
            return this.equals(SUBCENTRO);
        }
        
        public boolean isFlujoCaja() {
            return this.equals(FLUJO_CAJA);
        }
    }
    
}
