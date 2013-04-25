package ec.com.ebos.security.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.appl.web.jsf.bean.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.security.core.process.SecurityP;
import ec.com.ebos.security.model.Objeto;
import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.UsuarioRol;

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
    @Qualifier("securityP")
    private SecurityP securityP;
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args){
		securityP.putError(key, args);
	}
    
    //
    // Usuario
    //
    
    @Override
    public Usuario obtenerUsuarioNuevo() {
        return securityP.obtenerUsuarioNuevo();
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return securityP.obtenerUsuarioPorId(id);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return securityP.guardarUsuario(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        securityP.eliminarUsuario(usuario);
    }
    
    @Override
    public List<Usuario> obtenerUsuarioList(Usuario entitySearch, Pagination paginacion) {
        return securityP.obtenerUsuarioList(entitySearch, paginacion);
    }
    
    @Override
    public void generarUsuarioRol(Usuario usuario, Rol rol) {
        securityP.generarUsuarioRol(usuario,rol);
    }

    @Override
    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        securityP.guardarUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario) {
        return securityP.obtenerUsuarioRolList(usuario);
    }
    
    @Override
    public void eliminarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        securityP.eliminarUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public int obtenerUsuarioCount(){
        return securityP.obtenerUsuarioCount();
    }
    
    //
    // Rol
    //
    
    @Override
    public List<Rol> obtenerRolList(Rol rol, Pagination paginacion) {
        return securityP.obtenerRolList(rol, paginacion);
    }

    @Override
    public Rol obtenerRolNuevo() {
        return securityP.obtenerRolNuevo();
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return securityP.guardarRol(rol);
    }

    @Override
    public void eliminarRol(Rol rol) {
        securityP.eliminarRol(rol);
    }    
    
    @Override
    public List<RolOpcion> obtenerRolOpcionList(Rol rol) {
        return securityP.obtenerRolOpcionList(rol);
    }
    
    @Override
    public void generarRolOpcion(Rol rol, Opcion opcion) {
        securityP.generarRolOpcion(rol, opcion);
    }
    
    @Override
    public void guardarRolOpcionList(List<RolOpcion> rolOpcionList) {
        securityP.guardarRolOpcionList(rolOpcionList);
    }
    
    @Override
    public RolOpcion guardarRolOpcion(RolOpcion rolOpcion) {
        return securityP.guardarRolOpcion(rolOpcion);
    }
    
    @Override
    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList) {
        securityP.eliminarRolOpcionList(rolOpcionList);
    }
    
    //
    // Opcion
    //
    
    @Override
    public List<Opcion> obtenerOpcionList(Opcion opcion, Pagination paginacion) {
        return securityP.obtenerOpcionList(opcion, paginacion);
    }

    @Override
    public Opcion obtenerOpcionNuevo() {
        return securityP.obtenerOpcionNuevo();
    }

    @Override
    public Opcion guardarOpcion(Opcion opcion) {
        return securityP.guardarOpcion(opcion);
    }

    @Override
    public void eliminarOpcion(Opcion opcion) {
        securityP.eliminarOpcion(opcion);
    }
    
    @Override
    public Opcion obtenerOpcion(Long id) {
        return securityP.obtenerOpcion(id);
    }

    @Override
    public List<Opcion> obtenerOpcionPadreList() {
        return securityP.obtenerOpcionPadreList();
    }
    
    //
    // Objeto
    //
    public List<Objeto> obtenerObjetoList(Objeto objeto, Pagination paginacion){
    	return securityP.obtenerObjetoList(objeto, paginacion);
    }

    public Objeto obtenerObjetoNuevo(){
    	return securityP.obtenerObjetoNuevo();
    }

    public Objeto guardarObjeto(Objeto objeto){
    	return securityP.guardarObjeto(objeto);
    }

    public void eliminarObjeto(Objeto objeto){
    	securityP.eliminarObjeto(objeto);
    }

    public Objeto obtenerObjeto(Long id){
    	return securityP.obtenerObjeto(id);
    }
    
    //
    // Session Usuario
    //
    
    @Override
    public boolean iniciarSesion(SessionBean aThis) {        
        return securityP.iniciarSesion(aThis);
    }      
    
    @Override
    public void cambiarPassword(Usuario usuario){
        securityP.cambiarPassword(usuario);
    }
    
    
    @Override
    public SessionBean getSesionUsuario() {
    	return securityP.getSessionMB();
    }    
    
    public void guardarPreferenciasUsuario(Usuario usuario){
    	securityP.guardarPreferenciasUsuario(usuario);
    }
    
}
