package ec.com.ebos.security.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.master.model.Tema;
import ec.com.ebos.master.model.hibernate.HibernateTema;
import ec.com.ebos.master.session.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.security.core.process.SecurityP;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.UsuarioRol;
import ec.com.ebos.security.model.hibernate.HibernateRol;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;

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
    public Usuario saveUsuario(Usuario usuario) {
        return securityP.saveUsuario(usuario);
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        securityP.deleteUsuario(usuario);
    }
    
    @Override
    public List<Usuario> findUsuarioList(Usuario entitySearch, Pagination pagination) {
        return securityP.findUsuarioList(entitySearch, pagination);
    }
    
    @Override
    public void generateUsuarioRol(Usuario usuario, Rol rol) {
        securityP.generateUsuarioRol(usuario,rol);
    }

    @Override
    public void saveUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        securityP.saveUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public List<UsuarioRol> getUsuarioRolList(Usuario usuario) {
        return securityP.getUsuarioRolList(usuario);
    }
    
    @Override
    public void deleteUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        securityP.deleteUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public int getUsuarioCount(){
        return securityP.getUsuarioCount();
    }
    
    public Usuario getInstanceUsuario(){
    	return new HibernateUsuario();
    }
    
    //
    // Rol
    //
    
    @Override
    public List<Rol> findRolList(Rol rol, Pagination pagination) {
        return securityP.findRolList(rol, pagination);
    }

    @Override
    public Rol createRol() {
        return securityP.createRol();
    }

    @Override
    public Rol saveRol(Rol rol) {
        return securityP.saveRol(rol);
    }

    @Override
    public void deleteRol(Rol rol) {
        securityP.eliminarRol(rol);
    }    
    
    @Override
    public List<RolOpcion> getRolOpcionList(Rol rol) {
        return securityP.getRolOpcionList(rol);
    }
    
    @Override
    public void generateRolOpcion(Rol rol, Opcion opcion) {
        securityP.generateRolOpcion(rol, opcion);
    }
    
    @Override
    public void saveRolOpcionList(List<RolOpcion> rolOpcionList) {
        securityP.saveRolOpcionList(rolOpcionList);
    }
    
    @Override
    public RolOpcion saveRolOpcion(RolOpcion rolOpcion) {
        return securityP.saveRolOpcion(rolOpcion);
    }
    
    @Override
    public void deleteRolOpcionList(List<RolOpcion> rolOpcionList) {
        securityP.deleteRolOpcionList(rolOpcionList);
    }
    
    public Rol getInstanceRol(){
    	return new HibernateRol();
    }
    
    //
    // Session Usuario
    //
    
    @Override
    public boolean authLogin(SessionBean sessionBean) {        
        return securityP.authLogin(sessionBean);
    }      
    
    @Override
    public void changePassword(Usuario usuario){
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
    
    public Tema getInstanceTema(String nombre, String imagen){
    	return new HibernateTema(nombre, imagen);
    }
}
