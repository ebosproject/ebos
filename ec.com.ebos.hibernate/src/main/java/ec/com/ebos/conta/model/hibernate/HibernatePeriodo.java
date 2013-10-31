package ec.com.ebos.conta.model.hibernate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Temporal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.hibernate.field.SaldoCentroCosto_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCuentaCentro_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCuentaContable_;
import ec.com.ebos.conta.model.hibernate.field.SaldoRetencion_;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.model.field.Entidad_;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;


/**
 * Periodos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernatePeriodo.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernatePeriodo extends Contabilidad<HibernatePeriodo> {

	private static final long serialVersionUID = -747394377074840493L;
	
	protected static final String TABLE_NAME = "PERIODO";
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
	 * Ejercicio contable
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_ejercicio", nullable = false)
	private HibernateEjercicio ejercicio;
	
	/**
	 * Periodo fiscal pais
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_periodo_fiscal_pais")
	private HibernateEjercicio periodoFiscalPais;
	
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
	 * Valor de la utilidad del ejercicio 
	 */
	@Column(name = "valor_utilidad", nullable = false, length = 16, precision = 2)
	private BigDecimal valorUtilidad = BigDecimal.ZERO;
	
	/**
	 * Fecha de incio del ejercicio
	 */
	@Column(name = "fecha_inicial")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicial;
	
	/**
	 * Fecha final del ejercicio
	 */
	@Column(name = "fecha_final", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date creacion;
    
	/**
	 * Estado del {@link HibernatePeriodo}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private HibernatePeriodo.Estado estado = Estado.PENDIENTE;
	
	
	@OneToMany(mappedBy = SaldoCentroCosto_.periodo, fetch = FetchType.LAZY)
    private Set<HibernateSaldoCentroCosto> saldoCentroCostoList = new HashSet<HibernateSaldoCentroCosto>(0);
	
	@OneToMany(mappedBy = SaldoCuentaCentro_.periodo, fetch = FetchType.LAZY)
    private Set<HibernateSaldoCuentaCentro> saldoCuentaCentroList = new HashSet<HibernateSaldoCuentaCentro>(0);
	
	@OneToMany(mappedBy = SaldoCuentaContable_.periodo, fetch = FetchType.LAZY)
    private Set<HibernateSaldoCuentaContable> saldoCuentaContableList = new HashSet<HibernateSaldoCuentaContable>(0);
	
	@OneToMany(mappedBy = SaldoRetencion_.periodo, fetch = FetchType.LAZY)
    private Set<HibernateSaldoRetencion> saldoRetencionList = new HashSet<HibernateSaldoRetencion>(0);

    /**
     * <strong>Estado A/I para cualquier Entidad</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Estados </th>
     * <tr><td valign="top"> A: Activo<br> I: Inactivo<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum Estado implements StringValuedEnum<Estado> {

        PENDIENTE("P"),
        VIGENTE("V"),
        ABIERTO("A"),
        CERRADO("C"),
        ;

        public static class Type extends StringValuedEnumType<Estado> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".root.model.Periodo$Estado$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Estado(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Estado> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Estado.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Estado> LIST = Arrays.asList(Estado.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isPendiente() {
            return this.equals(PENDIENTE);
        }

        public boolean isVigente() {
            return this.equals(VIGENTE);
        }

        public boolean isCerrado() {
            return this.equals(CERRADO);
        }

        public boolean isAbierto() {
            return this.equals(ABIERTO);
        }
    }

}
