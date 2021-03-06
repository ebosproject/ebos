package ec.com.ebos.core.security.process;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.aspect.exception.ExceptionAspectHandlerException;
import ec.com.ebos.core.master.session.SessionBean;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.security.model.Rol;
import ec.com.ebos.core.security.model.RolOpcion;
import ec.com.ebos.core.security.model.Usuario;
import ec.com.ebos.core.security.model.UsuarioRol;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface SecurityP {
	
	/**
	 * Nombre del Spring Bean para {@link SecurityP}
	 */
	public static final String BEAN_NAME = "securityP";
	
	/**
	 * Nombre del Bean como EL a ser referenciado para la inyeccion de una instancia de {@link SecurityPImpl} 
	 * en otro ManagedBean 
	 */
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args);
	
	public void putError(ExceptionAspectHandlerException e);

    //
    //Usuario
    //
    public Usuario createUsuario();

    public Usuario getUsuario(Long id);

    public Usuario saveUsuario(Usuario usuario);

    public void deleteUsuario(Usuario usuario);

    public List<Usuario> findUsuarioList(Usuario entitySearch, Pagination pagination);

    public void saveUsuarioRolList(List<UsuarioRol> usuarioRolList);

    public void generateUsuarioRol(Usuario usuario, Rol rol);

    public List<UsuarioRol> getUsuarioRolList(Usuario usuario);

    public void deleteRolOpcionList(List<RolOpcion> rolOpcionList);
    
    public int getUsuarioCount();

    //
    // Rol
    //
    public List<Rol> findRolList(Rol rol, Pagination pagination);

    public Rol createRol();

    public Rol saveRol(Rol rol);

    public void eliminarRol(Rol rol);

    public List<RolOpcion> getRolOpcionList(Rol rol);

    public void generateRolOpcion(Rol rol, Opcion opcion);

    public void saveRolOpcionList(List<RolOpcion> rolOpcionList);

    public RolOpcion saveRolOpcion(RolOpcion rolOpcion);

    public void deleteUsuarioRolList(List<UsuarioRol> usuarioRolList);

    
    // 
    // Session Usuario
    //
    @Transactional(readOnly = true)
    public boolean authLogin(SessionBean aThis);

    public void changePassword(Usuario usuario);

    public SessionBean getSessionBean();

	public void saveUserPreferences(Usuario usuario);

	public Auditoria getAuditoria();


}
