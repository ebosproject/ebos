package ec.com.ebos.root.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.root.resources.RootMensajes;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * Superclase para todas las @Entidades de todos los modulos del sistema
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class Entidad<T extends Entidad<T>> implements IEntidad, Serializable {

	private static final long serialVersionUID = 2233398298735454479L;
	
	/**
	 * Metodos para propiedad Id
	 */
	public abstract Long getId();
    public abstract void setId(Long id);

    public Auditoria getAuditoria(){
    	return null;
    }
    public void setAuditoria(Auditoria auditoria){    	
    }
    
	@Getter @Setter
    @Transient
    private Date fechaDesde;
    
    @Getter @Setter
    @Transient
    private Date fechaHasta;
    
    @Transient
    @Override
    public Object getValue() {
        return getId();
    }
    
    /////////// METODOS PROXY PARA PROPIEDAD AUDITORIA ///////
    
    @Transient
    public Usuario getUsuarioCreacion(){    	
    	return getAuditoria() != null ? getAuditoria().getCreador() : null;
    }
    
    @Transient
    public void setUsuarioCreacion(Usuario usuarioCreacion){
    	if(getAuditoria() != null){
    		getAuditoria().setCreador(usuarioCreacion);
    	}
    }
    
    @Transient
    public Usuario getUsuarioModificacion(){
    	return getAuditoria() != null ? getAuditoria().getModificador() : null;
    }
    
    @Transient
    public void setUsuarioModificacion(Usuario usuarioModificacion){
    	if(getAuditoria() != null){    	
    		getAuditoria().setModificador(usuarioModificacion);
    	}
    }
    
    @Transient
    public Date getFechaCreacion(){
    	return getAuditoria() != null ? getAuditoria().getCreado() : null;
    }
    
    @Transient
    public void setFechaCreacion(Date fechaCreacion){
    	if(getAuditoria() != null){
    		getAuditoria().setCreado(fechaCreacion);
    	}
    }
    
    @Transient
    public Date getFechaModificacion(){
    	return getAuditoria() != null ? getAuditoria().getModificado() : null;
    }
    
    @Transient
    public void setFechaModificacion(Date fechaModificacion){
    	if(getAuditoria() != null){
    		getAuditoria().setModificado(fechaModificacion);
    	}
    }

    /**
     * @return Etiqueta; por default devuelve el Id de la entidad
     */
    @Transient
    @Override
    public String getLabel() {
        return String.valueOf(getId());
    }   

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

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
                GenericUtils.buildLabeledEnumMap(Estado.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Estado> LIST = Arrays.asList(Estado.values());

        @Override
        public String getLabel() {
            return RootMensajes.getString(labelKey);
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
