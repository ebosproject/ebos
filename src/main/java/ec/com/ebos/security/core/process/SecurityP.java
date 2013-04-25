package ec.com.ebos.security.core.process;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.ebos.appl.web.jsf.bean.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.security.model.Objeto;
import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.UsuarioRol;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface SecurityP {
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args);

    //
    //Usuario
    //
    public Usuario obtenerUsuarioNuevo();

    public Usuario obtenerUsuarioPorId(Long id);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    public List<Usuario> obtenerUsuarioList(Usuario entitySearch, Pagination paginacion);

    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList);

    public void generarUsuarioRol(Usuario usuario, Rol rol);

    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario);

    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList);
    
    public int obtenerUsuarioCount();

    //
    // Rol
    //
    public List<Rol> obtenerRolList(Rol rol, Pagination paginacion);

    public Rol obtenerRolNuevo();

    public Rol guardarRol(Rol rol);

    public void eliminarRol(Rol rol);

    public List<RolOpcion> obtenerRolOpcionList(Rol rol);

    public void generarRolOpcion(Rol rol, Opcion opcion);

    public void guardarRolOpcionList(List<RolOpcion> rolOpcionList);

    public RolOpcion guardarRolOpcion(RolOpcion rolOpcion);

    public void eliminarUsuarioRolList(List<UsuarioRol> usuarioRolList);

    //
    // Opcion
    //
    public List<Opcion> obtenerOpcionList(Opcion opcion, Pagination paginacion);

    public Opcion obtenerOpcionNuevo();

    public Opcion guardarOpcion(Opcion opcion);

    public void eliminarOpcion(Opcion opcion);

    public Opcion obtenerOpcion(Long id);

    public List<Opcion> obtenerOpcionPadreList();


    //
    // Objeto
    //
    public List<Objeto> obtenerObjetoList(Objeto objeto, Pagination paginacion);

    public Objeto obtenerObjetoNuevo();

    public Objeto guardarObjeto(Objeto objeto);

    public void eliminarObjeto(Objeto objeto);

    public Objeto obtenerObjeto(Long id);

    
    // 
    // Session Usuario
    //
    @Transactional(readOnly = true)
    public boolean iniciarSesion(SessionBean aThis);

    public void cambiarPassword(Usuario usuario);

    public SessionBean getSessionMB();

	public void guardarPreferenciasUsuario(Usuario usuario);

}
