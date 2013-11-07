package ec.com.ebos.core.master.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;

public interface TipoImpuesto extends Master {

	public Auditoria getAuditoria();

	public ClaseImpuesto getClaseImpuesto();

	public String getCodigo();

	public String getDescripcion();

	public Long getId();

	public void setAuditoria(Auditoria auditoria);

	public void setClaseImpuesto(ClaseImpuesto claseImpuesto);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
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
            return labelKey;
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