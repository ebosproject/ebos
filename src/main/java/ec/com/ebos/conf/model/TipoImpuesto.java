package ec.com.ebos.conf.model;

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
import ec.com.ebos.bitacora.model.Evento.TipoEvento;
import ec.com.ebos.bitacora.resources.BitacoraMensajes;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Entity
@Table(name = DB_STRUCTURE.TIPO_IMPUESTO, schema = "ebosconf")
@Data @EqualsAndHashCode(callSuper=false) 
public class TipoImpuesto extends Entidad<TipoImpuesto>{

	private static final long serialVersionUID = 3922934845182492539L;

	@Id
	@SequenceGenerator(name = DB_STRUCTURE.TIPO_IMPUESTO+"_ID_GENERATOR", sequenceName = "S_"+DB_STRUCTURE.TIPO_IMPUESTO)
	@GeneratedValue(generator = DB_STRUCTURE.TIPO_IMPUESTO+"_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "codigo", nullable = false, length = Entidad.CODIGO_LENGTH)
	private String codigo;
	
	@Column(name = "descripcion", nullable = false, length = Entidad.DESCRIPCION_LENGTH)
	private String descripcion;
	
	@Column(name = "clase", nullable = false, length = 3)
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
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conf.model.Evento$"+DB_STRUCTURE.TIPO_IMPUESTO+"$Type";

        @Getter
        private String value;
        private String labelKey;

        private ClaseImpuesto(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoEvento> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoEvento.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoEvento> LIST = Arrays.asList(TipoEvento.values());

        @Override
        public String getLabel() {
            return BitacoraMensajes.getString(labelKey);
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
