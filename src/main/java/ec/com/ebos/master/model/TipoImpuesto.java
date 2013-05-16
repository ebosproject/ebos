package ec.com.ebos.master.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.master.resources.MasterMensajes;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.model.field.Entidad_;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * @update 2013/04/29 <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = TipoImpuesto.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class TipoImpuesto extends Entidad<TipoImpuesto>{

	private static final long serialVersionUID = 3922934845182492539L;
	
	protected static final String TABLE_NAME = "TIPO_IMPUESTO";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del tipo de documento
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;
	
	@Column(name = "clase", nullable = false, length = 3)
    @Type(type = ClaseImpuesto.TYPE)
	private ClaseImpuesto claseImpuesto;
	
    /**
     * <strong>Clase de Impuesto</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Clases </th>
     * <tr><td valign="top"> 
     * IVA: IVA <br> 
     * ICE: Impuestoa consumos especiales <br>
     * RIV: Retencion IVA <br> 
     * RFT: Retencion Fuente <br>
     * TAS: Tasas <br>
     * </td></tr>
     * </table>
     *
     * @author VIP Consultores
     *
     */
    public enum ClaseImpuesto implements StringValuedEnum<ClaseImpuesto> {

        IVA("IVA"),
        ICE("ICE"),
        RETENCION_IVA("RIV"),
        RETENCION_FUENTE("RFT"),
        TASA("TAS"),
        ;

        public static class Type extends StringValuedEnumType<ClaseImpuesto> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".master.model.TipoImpuesto$ClaseImpuesto$Type";

        @Getter
        private String value;
        private String labelKey;

        private ClaseImpuesto(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, ClaseImpuesto> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(ClaseImpuesto.values());
        /**
         * Lists para iteraciones
         */
        public static final List<ClaseImpuesto> LIST = Arrays.asList(ClaseImpuesto.values());

        @Override
        public String getLabel() {
            return MasterMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isIva() {
            return this.equals(IVA);
        }
        
        public boolean isICE() {
            return this.equals(ICE);
        }
        
        public boolean isRetencionFuente() {
            return this.equals(RETENCION_FUENTE);
        }

        public boolean isRetencionIva() {
            return this.equals(RETENCION_IVA);
        }

    }
}
