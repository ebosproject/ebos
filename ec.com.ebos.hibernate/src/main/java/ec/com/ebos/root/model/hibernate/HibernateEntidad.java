package ec.com.ebos.root.model.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Usuario;

/**
 * Superclase para todas las @Entidades de todos los modulos del sistema
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
//public abstract class HibernateEntidad<T extends HibernateEntidad<T>> implements Entidad, Serializable {
public abstract class HibernateEntidad implements Entidad, Serializable {

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
    public Usuario getCreador(){    	
    	return getAuditoria() != null ? getAuditoria().getCreador() : null;
    }
    
    @Transient
    public void setCreador(Usuario creador){
    	if(getAuditoria() != null){
    		getAuditoria().setCreador(creador);
    	}
    }
    
    @Transient
    public Usuario getModificador(){
    	return getAuditoria() != null ? getAuditoria().getModificador() : null;
    }
    
    @Transient
    public void setModificador(Usuario modificador){
    	if(getAuditoria() != null){    	
    		getAuditoria().setModificador(modificador);
    	}
    }
    
    @Transient
    public Date getCreado(){
    	return getAuditoria() != null ? getAuditoria().getCreado() : null;
    }
    
    @Transient
    public void setCreado(Date creado){
    	if(getAuditoria() != null){
    		getAuditoria().setCreado(creado);
    	}
    }
    
    @Transient
    public Date getModificado(){
    	return getAuditoria() != null ? getAuditoria().getModificado() : null;
    }
    
    @Transient
    public void setModificado(Date modificado){
    	if(getAuditoria() != null){
    		getAuditoria().setModificado(modificado);
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

    
}
