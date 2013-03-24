package ec.com.platform.seguridad.core.gestor;

import java.util.List;

import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.seguridad.model.Objeto;
import ec.com.platform.seguridad.model.Opcion;
import ec.com.platform.seguridad.model.Rol;
import ec.com.platform.seguridad.model.RolOpcion;
import ec.com.platform.seguridad.model.Usuario;
import ec.com.platform.seguridad.model.UsuarioRol;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface SeguridadG {

    //
    //Usuario
    //
    public Usuario obtenerUsuarioNuevo();

    public Usuario obtenerUsuarioPorId(Long id);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    public List<Usuario> obtenerUsuarioList(Usuario entitySearch, Paginacion paginacion);

    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList);

    public void generarUsuarioRol(Usuario usuario, Rol rol);

    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario);

    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList);
    
    public int obtenerUsuarioCount();

    //
    // Rol
    //
    public List<Rol> obtenerRolList(Rol rol, Paginacion paginacion);

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
    public List<Opcion> obtenerOpcionList(Opcion opcion, Paginacion paginacion);

    public Opcion obtenerOpcionNuevo();

    public Opcion guardarOpcion(Opcion opcion);

    public void eliminarOpcion(Opcion opcion);

    public Opcion obtenerOpcion(Long id);

    public List<Opcion> obtenerOpcionPadreList();


    //
    // Objeto
    //
    public List<Objeto> obtenerObjetoList(Objeto objeto, Paginacion paginacion);

    public Objeto obtenerObjetoNuevo();

    public Objeto guardarObjeto(Objeto objeto);

    public void eliminarObjeto(Objeto objeto);

    public Objeto obtenerObjeto(Long id);

    
    // 
    // Session Usuario
    //
    public boolean iniciarSesion(SesionUsuarioMB aThis);

    public void cambiarPassword(Usuario usuario);

    public SesionUsuarioMB getSesionUsuario();
}
