package ec.com.ebos.admin.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

public interface Objeto extends Administracion {

	public Auditoria getAuditoria();

	public String getCodigo();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public Set<Opcion> getOpcionList();

	public Objeto.TipoObjeto getTipo();

	public void setAuditoria(Auditoria auditoria);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setOpcionList(Set<Opcion> opcionList);

	public void setTipo(Objeto.TipoObjeto tipo);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
	/**
     * <strong>TipoObjeto de un {@link HibernateObjeto}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> B: Bean<br> O: Otro<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoObjeto implements StringValuedEnum<TipoObjeto> {

        BEAN("B"),
        OTRO("O");

        public static class Type extends StringValuedEnumType<TipoObjeto> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".admin.model.Objeto$TipoObjeto$Type";
        
        @Getter
        private String value;
        private String labelKey;

        private TipoObjeto(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoObjeto> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoObjeto.values());

        /**
         * Listas para iteraciones
         */
        public static final List<TipoObjeto> LIST = Arrays.asList(TipoObjeto.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isBean() {
            return this.equals(BEAN);
        }
        
        public boolean isOtro() {
            return this.equals(OTRO);
        }
    }

}