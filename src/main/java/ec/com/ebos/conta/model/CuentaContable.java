package ec.com.ebos.conta.model;

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
import ec.com.ebos.master.model.Master;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * Plan de cuentas general
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = CuentaContable.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class CuentaContable extends Contabilidad<CuentaContable> {

	private static final long serialVersionUID = 2646781225564626367L;
	
	protected static final String TABLE_NAME = "CUENTA_CONTABLE";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	/**
	 * Cuenta contable padre
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_padre")
    private CuentaContable padre;
	
	/**
	 * Tipo de cuenta
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;
	
	/**
	 * Codigo de la {@link CuentaContable}
	 */
	@Column(name = Entidad.CODIGO_NAME, nullable = false, length = Entidad.CODIGO_LENGTH, unique = true)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la cuenta
	 */
	@Column(name = Entidad.DESCRIPCION_NAME, nullable = false, length = 150) //TODO (epa): Consultar si no se usa default Entidad.DESCRIPCION_LENGTH=100
	private String descripcion;
	
	@Column(name="codigo_formato", nullable = false, length = 50)
	private String codigoFormato;
	
	/**
	 * Nivel de la cuenta dentro de la jerarquia
	 */
	@Column(name="nivel", nullable = false, length = 2)
	private int nivel = 1;
	
	/**
	 * Indica si es cuenta final
	 */
	@Column(name="terminante", nullable = false, length = 2)
	private int terminante = 0;
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Naturaleza de la {@link CuentaContable}
	 */
	@Column(name = "naturaleza", nullable = false, length = 1)
    @Type(type = CuentaContable.Naturaleza.TYPE)
    private CuentaContable.Naturaleza naturaleza = CuentaContable.Naturaleza.DEUDORA;
	
	/**
	 * Indica si pide o no centro de costo
	 */
	@Column(name = "pide_centro_costo", nullable = false)
	private boolean pideCentroCosto = false;
	
	/**
	 * Tipo de proceso en la que se puede usar la {@link CuentaContable}
	 */
	@Column(name = "tipo_proceso", nullable = false, length = 1)
    @Type(type = CuentaContable.TipoProceso.TYPE)
    private CuentaContable.TipoProceso tipoProceso = CuentaContable.TipoProceso.MIXTA;
	
	
	/**
	 * Estado de la {@link CuentaContable}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
    /**
     * <strong>Naturalezas de una {@link CuentaContable}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> D: Deudora<br> A: Acreedora<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum Naturaleza implements StringValuedEnum<Naturaleza> {

        DEUDORA("D"),
        ACREEDORA("A");

        public static class Type extends StringValuedEnumType<Estado> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conta.model.CuentaContable$Naturaleza$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Naturaleza(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Naturaleza> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(Naturaleza.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Naturaleza> LIST = Arrays.asList(Naturaleza.values());

        @Override
        public String getLabel() {
            return ContaMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isDeudora() {
            return this.equals(DEUDORA);
        }

        public boolean isAcreedora() {
            return this.equals(ACREEDORA);
        }
    }
    
    /**
     * <strong>Tipos de procesos de una {@link CuentaContable}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> X: Manual<br> A: Automatica<br> M: Mixta<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum TipoProceso implements StringValuedEnum<TipoProceso> {

        MIXTA("X"),
        AUTOMATICA("A"),
        MANUAL("M");
        

        public static class Type extends StringValuedEnumType<Estado> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conta.model.CuentaContable$TipoOProceso$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private TipoProceso(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, TipoProceso> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoProceso.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoProceso> LIST = Arrays.asList(TipoProceso.values());

        @Override
        public String getLabel() {
            return ContaMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isManual() {
            return this.equals(MANUAL);
        }

        public boolean isAutomatica() {
            return this.equals(AUTOMATICA);
        }
        
        public boolean isMixta() {
            return this.equals(MIXTA);
        }
    }
    
}
