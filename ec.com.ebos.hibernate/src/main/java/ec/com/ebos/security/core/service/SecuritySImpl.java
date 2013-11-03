package ec.com.ebos.security.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.master.session.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.security.core.process.SecurityP;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.hibernate.HibernateRol;
import ec.com.ebos.security.model.hibernate.HibernateRolOpcion;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;
import ec.com.ebos.security.model.hibernate.HibernateUsuarioRol;

/**
 * @author Eduardo Plua Alay
 */
@Service("securityS")
public class SecuritySImpl implements SecurityS{
    
	private static final long serialVersionUID = 6232192533121674820L;
	
	//
    // Servicios modulos
    //
	
	//@Resource() TODO (epa): Probar Annotation
	@Getter @Setter
    @Autowired
    @Qualifier(SecurityP.BEAN_NAME)
    private SecurityP securityP;
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args){
		securityP.putError(key, args);
	}
	
	public void putError(ExceptionAspectHandlerException e){
		securityP.putError(e);
	}
    
    //
    // Usuario
    //
    
    @Override
    public Usuario createUsuario() {
        return securityP.createUsuario();
    }
    
    @Override
    public Usuario getUsuario(Long id) {
        return securityP.getUsuario(id);
    }

    @Override
    public Usuario saveUsuario(HibernateUsuario usuario) {
        return securityP.saveUsuario(usuario);
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        securityP.deleteUsuario(usuario);
    }
    
    @Override
    public List<HibernateUsuario> findUsuarioList(Usuario entitySearch, Pagination pagination) {
        return securityP.findUsuarioList(entitySearch, pagination);
    }
    
    @Override
    public void generateUsuarioRol(Usuario usuario, Rol rol) {
        securityP.generateUsuarioRol(usuario,rol);
    }

    @Override
    public void saveUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList) {
        securityP.saveUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public List<HibernateUsuarioRol> getUsuarioRolList(Usuario usuario) {
        return securityP.getUsuarioRolList(usuario);
    }
    
    @Override
    public void deleteUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList) {
        securityP.deleteUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public int getUsuarioCount(){
        return securityP.getUsuarioCount();
    }
    
    //
    // Rol
    //
    
    @Override
    public List<HibernateRol> findRolList(HibernateRol rol, Pagination pagination) {
        return securityP.findRolList(rol, pagination);
    }

    @Override
    public HibernateRol createRol() {
        return securityP.createRol();
    }

    @Override
    public HibernateRol saveRol(HibernateRol rol) {
        return securityP.saveRol(rol);
    }

    @Override
    public void deleteRol(HibernateRol rol) {
        securityP.eliminarRol(rol);
    }    
    
    @Override
    public List<HibernateRolOpcion> getRolOpcionList(Rol rol) {
        return securityP.getRolOpcionList(rol);
    }
    
    @Override
    public void generateRolOpcion(Rol rol, Opcion opcion) {
        securityP.generateRolOpcion(rol, opcion);
    }
    
    @Override
    public void saveRolOpcionList(List<HibernateRolOpcion> rolOpcionList) {
        securityP.saveRolOpcionList(rolOpcionList);
    }
    
    @Override
    public RolOpcion saveRolOpcion(HibernateRolOpcion rolOpcion) {
        return securityP.saveRolOpcion(rolOpcion);
    }
    
    @Override
    public void deleteRolOpcionList(List<HibernateRolOpcion> rolOpcionList) {
        securityP.deleteRolOpcionList(rolOpcionList);
    }
    
    //
    // Session Usuario
    //
    
    @Override
    public boolean authLogin(SessionBean sessionBean) {        
        return securityP.authLogin(sessionBean);
    }      
    
    @Override
    public void changePassword(HibernateUsuario usuario){
        securityP.changePassword(usuario);
    }
    
    
    @Override
    public SessionBean getSessionBean() {
    	return securityP.getSessionBean();
    }
    
    @Override
    public Auditoria getAuditoria() {
    	return securityP.getAuditoria();
    }
        
    
    public void saveUserPreferences(Usuario usuario){
    	securityP.saveUserPreferences(usuario);
    }
    
}
