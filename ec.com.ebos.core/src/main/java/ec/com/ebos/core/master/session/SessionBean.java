package ec.com.ebos.core.master.session;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import ec.com.ebos.core.admin.model.Bundle;
import ec.com.ebos.core.admin.model.Bundle.Localidad;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.master.model.Tema;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.security.model.RolOpcion;
import ec.com.ebos.core.security.model.Usuario;
import ec.com.ebos.core.security.service.SecurityS;


/**
 * Session Interface
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-10-21 
 */
public interface SessionBean {

	/**
	 * Nombre del bean para {@link SessionBean}
	 */
	public static final String BEAN_NAME = "sessionBean";
	/**
	 * Nombre del Bean como EL a ser referenciado para la inyeccion de una instancia de {@link SessionBean} en otro ManagedBean
	 */
	public static final String EL_BEAN_NAME = "#{" + BEAN_NAME + "}";

	public SecurityS getSecurityS();

	public void setSecurityS(SecurityS securityS);

	public Localidad getLocalidad();

	public void setLocalidad(Localidad localidad);

	public Usuario getUsuario();

	public void setUsuario(Usuario usuario);

	public Organizacion getEmpresa();

	public void setEmpresa(Organizacion empresa);

	public boolean isLogin();

	public void setLogin(boolean login);

	public List<RolOpcion> getRolOpcionList();

	public void setRolOpcionList(List<RolOpcion> rolOpcionList);

	@PostConstruct
	public void postConstruct();

	@PreDestroy
	public void preDestroy();

	public void iniciarSesion();

	public void cerrarSesion();

	public void cambiarPassword();

	// Permisos de Seguridad
	public static final int EDIT_ACTION_ID = 1;
	public static final int CREATE_ACTION_ID = 2;
	public static final int DELETE_ACTION_ID = 3;
	public static final int EXPORT_ACTION_ID = 4;

	/**
	 * Metodo que verifica los permisos que el usuario actual tiene sobre
	 * las opciones asignadas
	 *  
	 * @param target
	 * @param accion
	 * @return
	 */
	public boolean verificaAcceso(String target, int accion);

	public String getTema();

	/**
	 * Guarda el tema seleccionado por el usuario actual
	 * @param theme
	 */
	public void saveTheme(String theme);

	/**
	 * Guarda la nueva localidad del usuario actual
	 */
	public void saveLocale();

	public List<Bundle.Localidad> getLocalidadList();

	public boolean isSuccess();

	public void setSuccess(boolean success);

	public void putInfo(String keySummary, String detail, Object... args);

	public void putInfo(String summary);

	public void putSuccess(String keySummary, String detail, Object... args);

	public void putSuccess(String summary);

	public void putWarn(String keySummary, String detail, Object... args);

	public void putWarning(String summary);

	public void putError(String keySummary, String detail, Object... args);

	public void putError(String summary);

	public void putFatal(String keySummary, String detail, Object... args);

	public void putFatal(String summary);

	public Auditoria getAuditoria();

	public Tema getInstanceTema(String nombre, String imagen);

}