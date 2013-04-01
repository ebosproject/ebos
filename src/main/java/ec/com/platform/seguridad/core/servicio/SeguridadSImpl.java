package ec.com.platform.seguridad.core.servicio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.seguridad.core.gestor.SeguridadG;
import ec.com.platform.seguridad.model.Objeto;
import ec.com.platform.seguridad.model.Opcion;
import ec.com.platform.seguridad.model.Rol;
import ec.com.platform.seguridad.model.RolOpcion;
import ec.com.platform.seguridad.model.Usuario;
import ec.com.platform.seguridad.model.UsuarioRol;

/**
 * @author Eduardo Plua Alay
 */
@Service("seguridadS")
public class SeguridadSImpl implements SeguridadS{
    
	private static final long serialVersionUID = 6232192533121674820L;
	
	//
    // Servicios modulos
    //
	
	//@Resource() TODO (epa): Probar Annotation
	@Getter @Setter
    @Autowired
    @Qualifier("seguridadG")
    private SeguridadG seguridadG;
    
    //
    // Usuario
    //
    
    @Override
    public Usuario obtenerUsuarioNuevo() {
        return seguridadG.obtenerUsuarioNuevo();
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return seguridadG.obtenerUsuarioPorId(id);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return seguridadG.guardarUsuario(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        seguridadG.eliminarUsuario(usuario);
    }
    
    @Override
    public List<Usuario> obtenerUsuarioList(Usuario entitySearch, Paginacion paginacion) {
        return seguridadG.obtenerUsuarioList(entitySearch, paginacion);
    }
    
    @Override
    public void generarUsuarioRol(Usuario usuario, Rol rol) {
        seguridadG.generarUsuarioRol(usuario,rol);
    }

    @Override
    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        seguridadG.guardarUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario) {
        return seguridadG.obtenerUsuarioRolList(usuario);
    }
    
    @Override
    public void eliminarUsuarioRolList(List<UsuarioRol> usuarioRolList) {
        seguridadG.eliminarUsuarioRolList(usuarioRolList);
    }
    
    @Override
    public int obtenerUsuarioCount(){
        return seguridadG.obtenerUsuarioCount();
    }
    
    //
    // Rol
    //
    
    @Override
    public List<Rol> obtenerRolList(Rol rol, Paginacion paginacion) {
        return seguridadG.obtenerRolList(rol, paginacion);
    }

    @Override
    public Rol obtenerRolNuevo() {
        return seguridadG.obtenerRolNuevo();
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return seguridadG.guardarRol(rol);
    }

    @Override
    public void eliminarRol(Rol rol) {
        seguridadG.eliminarRol(rol);
    }    
    
    @Override
    public List<RolOpcion> obtenerRolOpcionList(Rol rol) {
        return seguridadG.obtenerRolOpcionList(rol);
    }
    
    @Override
    public void generarRolOpcion(Rol rol, Opcion opcion) {
        seguridadG.generarRolOpcion(rol, opcion);
    }
    
    @Override
    public void guardarRolOpcionList(List<RolOpcion> rolOpcionList) {
        seguridadG.guardarRolOpcionList(rolOpcionList);
    }
    
    @Override
    public RolOpcion guardarRolOpcion(RolOpcion rolOpcion) {
        return seguridadG.guardarRolOpcion(rolOpcion);
    }
    
    @Override
    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList) {
        seguridadG.eliminarRolOpcionList(rolOpcionList);
    }
    
    //
    // Opcion
    //
    
    @Override
    public List<Opcion> obtenerOpcionList(Opcion opcion, Paginacion paginacion) {
        return seguridadG.obtenerOpcionList(opcion, paginacion);
    }

    @Override
    public Opcion obtenerOpcionNuevo() {
        return seguridadG.obtenerOpcionNuevo();
    }

    @Override
    public Opcion guardarOpcion(Opcion opcion) {
        return seguridadG.guardarOpcion(opcion);
    }

    @Override
    public void eliminarOpcion(Opcion opcion) {
        seguridadG.eliminarOpcion(opcion);
    }
    
    @Override
    public Opcion obtenerOpcion(Long id) {
        return seguridadG.obtenerOpcion(id);
    }

    @Override
    public List<Opcion> obtenerOpcionPadreList() {
        return seguridadG.obtenerOpcionPadreList();
    }
    
    //
    // Objeto
    //
    public List<Objeto> obtenerObjetoList(Objeto objeto, Paginacion paginacion){
    	return seguridadG.obtenerObjetoList(objeto, paginacion);
    }

    public Objeto obtenerObjetoNuevo(){
    	return seguridadG.obtenerObjetoNuevo();
    }

    public Objeto guardarObjeto(Objeto objeto){
    	return seguridadG.guardarObjeto(objeto);
    }

    public void eliminarObjeto(Objeto objeto){
    	seguridadG.eliminarObjeto(objeto);
    }

    public Objeto obtenerObjeto(Long id){
    	return seguridadG.obtenerObjeto(id);
    }
    
    //
    // Session Usuario
    //
    
    @Override
    public boolean iniciarSesion(SesionUsuarioMB aThis) {        
        return seguridadG.iniciarSesion(aThis);
    }      
    
    @Override
    public void cambiarPassword(Usuario usuario){
        seguridadG.cambiarPassword(usuario);
    }
    
    
    @Override
    public SesionUsuarioMB getSesionUsuario() {
    	return seguridadG.getSesionUsuario();
    }    
    
    public void guardarPreferenciasUsuario(Usuario usuario){
    	seguridadG.guardarPreferenciasUsuario(usuario);
    }

}
