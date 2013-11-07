package ec.com.ebos.core.master.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;

public interface Propiedad extends Master {

	public Activo getActivo();

	public ActivoCategoria getCategoria();

	public Entidad.Estado getEstado();

	public Long getId();

	public int getLongitud();

	public Set<PropiedadValor> getPropiedadValorList();

	public Propiedad.TipoDato getTipoDato();

	public String getValor();

	public String getValorDefecto();

	public boolean isLista();

	public boolean isRequerido();

	public void setActivo(Activo activo);

	public void setCategoria(ActivoCategoria categoria);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setLista(boolean lista);

	public void setLongitud(int longitud);

	public void setPropiedadValorList(Set<PropiedadValor> propiedadValorList);

	public void setRequerido(boolean requerido);

	public void setTipoDato(Propiedad.TipoDato tipoDato);

	public void setValor(String valor);

	public void setValorDefecto(String valorDefecto);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
    /**
     * <strong>Tipo de dato</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Estados </th>
     * <tr><td valign="top"> 
     * T: TEXT <br> 
     * N: NUMBER <br>
     * B: BOOLEAN <br>
     * </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoDato implements StringValuedEnum<TipoDato> { //TODO (epa): agregar mas tipos de datos

        TEXT("T"),
        NUMBER("N"),
        BOOLEAN("B");

        public static class Type extends StringValuedEnumType<TipoDato> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".core.master.model.Propiedad$TipoDato$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoDato(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoDato> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoDato.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoDato> LIST = Arrays.asList(TipoDato.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isTexto() {
            return this.equals(TEXT);
        }
        
        public boolean isNumber() {
            return this.equals(NUMBER);
        }
        
        public boolean isBoolean() {
            return this.equals(BOOLEAN);
        }

    }

}