package ec.com.ebos.seguridad.core.servicio;

import ec.com.ebos.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.ebos.fwk.crud.Paginacion;
import ec.com.ebos.seguridad.model.Objeto;
import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.seguridad.model.Rol;
import ec.com.ebos.seguridad.model.RolOpcion;
import ec.com.ebos.seguridad.model.Usuario;
import ec.com.ebos.seguridad.model.UsuarioRol;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface SeguridadS extends Serializable {
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args);
    
    //
    // Usuario
    //
    public Usuario obtenerUsuarioNuevo();

    public Usuario obtenerUsuarioPorId(Long id);
    
    public Usuario guardarUsuario(Usuario usuario);
    
    public void eliminarUsuario(Usuario usuario);
    
    public List<Usuario> obtenerUsuarioList(Usuario entitySearch, Paginacion paginacion);
    
    public void generarUsuarioRol(Usuario usuario, Rol rol);

    public void guardarUsuarioRolList(List<UsuarioRol> usuarioRolList);
    
    public List<UsuarioRol> obtenerUsuarioRolList(Usuario usuario);
    
    public void eliminarUsuarioRolList(List<UsuarioRol> usuarioRolList);

    public int obtenerUsuarioCount();
    //
    // Rol
    //
    public List<Rol> obtenerRolList(Rol rol, Paginacion paginacion);

    public Rol obtenerRolNuevo();

    public Rol guardarRol(Rol rol);

    public void eliminarRol(Rol rol);
    
    public void generarRolOpcion(Rol rol, Opcion opcion);
    
    public List<RolOpcion> obtenerRolOpcionList(Rol rol);
    
    public void guardarRolOpcionList(List<RolOpcion> rolOpcionList);
    
    public RolOpcion guardarRolOpcion(RolOpcion rolOpcion);
    
    public void eliminarRolOpcionList(List<RolOpcion> rolOpcionList);
    
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
    // Session usuario
    //

    @Transactional
    public boolean iniciarSesion(SesionUsuarioMB aThis);

    public void cambiarPassword(Usuario usuario);
    
    public SesionUsuarioMB getSesionUsuario();

	public void guardarPreferenciasUsuario(Usuario usuario);

}
