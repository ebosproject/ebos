package ec.com.ebos.security.core.process;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.master.session.SessionBean;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.hibernate.HibernateRol;
import ec.com.ebos.security.model.hibernate.HibernateRolOpcion;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;
import ec.com.ebos.security.model.hibernate.HibernateUsuarioRol;

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
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	//
	// Put Messages
	//
	
	public void putError(String key, Object... args);
	
	public void putError(ExceptionAspectHandlerException e);

    //
    //Usuario
    //
    public HibernateUsuario createUsuario();

    public HibernateUsuario getUsuario(Long id);

    public HibernateUsuario saveUsuario(HibernateUsuario usuario);

    public void deleteUsuario(HibernateUsuario usuario);

    public List<HibernateUsuario> findUsuarioList(HibernateUsuario entitySearch, Pagination pagination);

    public void saveUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList);

    public void generateUsuarioRol(HibernateUsuario usuario, Rol rol);

    public List<HibernateUsuarioRol> getUsuarioRolList(HibernateUsuario usuario);

    public void deleteRolOpcionList(List<HibernateRolOpcion> rolOpcionList);
    
    public int getUsuarioCount();

    //
    // Rol
    //
    public List<HibernateRol> findRolList(HibernateRol rol, Pagination pagination);

    public HibernateRol createRol();

    public HibernateRol saveRol(HibernateRol rol);

    public void eliminarRol(HibernateRol rol);

    public List<HibernateRolOpcion> getRolOpcionList(Rol rol);

    public void generateRolOpcion(Rol rol, Opcion opcion);

    public void saveRolOpcionList(List<HibernateRolOpcion> rolOpcionList);

    public RolOpcion saveRolOpcion(HibernateRolOpcion rolOpcion);

    public void deleteUsuarioRolList(List<HibernateUsuarioRol> usuarioRolList);

    
    // 
    // Session Usuario
    //
    @Transactional(readOnly = true)
    public boolean authLogin(SessionBean aThis);

    public void changePassword(HibernateUsuario usuario);

    public SessionBean getSessionBean();

	public void saveUserPreferences(HibernateUsuario usuario);


}
