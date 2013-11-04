package ec.com.ebos.root.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;


/**
 * Interfaz para Clases y Enums que implementen los metodos {@link #getValue()} y {@link #getLabel()}.
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @update 2013-04-25
 */
public interface Entidad extends Root{

	/**
	 * Metodos para propiedad Id
	 */
	public Long getId();

	public void setId(Long id);

	public Object getValue();

	/**
	 * @return Etiqueta; por default devuelve el Id de la entidad
	 */
	public String getLabel();

	public boolean equals(Object object);

	public int hashCode();

	public String toString();

	//
	// Metodos de auditoria 
	//
	
	public void setAuditoria(Auditoria auditoria);

	public void setCreador(Usuario usuario);

	public void setCreado(Date date);

	public void setModificador(Usuario usuario);

	public void setModificado(Date date);

	public Object getAuditoria();
	
	
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

        ACTIVO("A"),
        INACTIVO("I");

        public static class Type extends StringValuedEnumType<Estado> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".root.model.Entidad$Estado$Type";
        
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

        public boolean isActivo() {
            return this.equals(ACTIVO);
        }

        public boolean isInactivo() {
            return this.equals(INACTIVO);
        }
    }

}
