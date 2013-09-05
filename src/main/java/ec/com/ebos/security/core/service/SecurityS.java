package ec.com.ebos.security.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.master.web.jsf.bean.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.security.model.UsuarioRol;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface SecurityS extends Serializable {
	
	/**
	 * Nombre del Spring Bean para {@link SecurityS}
	 */
	public static final String BEAN_NAME = "securityS";
	
	/**
	 * Nombre del Bean como EL a ser referenciado para la inyeccion de una instancia de {@link SecuritySImpl} 
	 * en otro ManagedBean
	 */
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args);
    
	public void putError(Throwable t);
    //
    // Usuario
    //
    public Usuario createUsuario();

    public Usuario getUsuario(Long id);
    
    public Usuario saveUsuario(Usuario usuario);
    
    public void deleteUsuario(Usuario usuario);
    
    public List<Usuario> findUsuarioList(Usuario entitySearch, Pagination pagination);
    
    public void generateUsuarioRol(Usuario usuario, Rol rol);

    public void saveUsuarioRolList(List<UsuarioRol> usuarioRolList);
    
    public List<UsuarioRol> getUsuarioRolList(Usuario usuario);
    
    public void deleteUsuarioRolList(List<UsuarioRol> usuarioRolList);

    public int getUsuarioCount();
    //
    // Rol
    //
    public List<Rol> findRolList(Rol rol, Pagination pagination);

    public Rol createRol();

    public Rol saveRol(Rol rol);

    public void deleteRol(Rol rol);
    
    public void generateRolOpcion(Rol rol, Opcion opcion);
    
    public List<RolOpcion> getRolOpcionList(Rol rol);
    
    public void saveRolOpcionList(List<RolOpcion> rolOpcionList);
    
    public RolOpcion saveRolOpcion(RolOpcion rolOpcion);
    
    public void deleteRolOpcionList(List<RolOpcion> rolOpcionList);
    
    
    //
    // Session usuario
    //

    @Transactional(readOnly = true)
    public boolean authLogin(SessionBean sessionBean);

    public void changePassword(Usuario usuario);
    
    public SessionBean getSessionBean();

	public void saveUserPreferences(Usuario usuario);

}
