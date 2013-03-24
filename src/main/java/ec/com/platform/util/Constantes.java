package ec.com.platform.util;

import java.util.Date;

import lombok.Getter;
import ec.com.platform.util.crypto.CryptoUtils;

/**
 * <p>Clase que cotiene el valor de variables globales a nivel de todas las
 * aplicaciones de Framework</p>
 *
 * @author Gerardo Yand&uacute;n
 * @author Eduardo Plua Alay
 * @version 1.0, 15/04/2007
 * @since J2SDK1.4.2
 * @update 2013-01-26
 */
public class Constantes {

    /**
     * Nombre de dominio del sistema
     */
    public static final String DOMAIN_NAME = "ec.com.platform";
    
    /**
     * Duracion de la sesion HTTP en segundos (tiempo maximo de inactividad),
     * 1800 por defecto (30 minutos). Se lo puede definir como argumento al
     * iniciar la JVM:
     * <code>-Dciscnet.session_timeout=1800</code>
     */
    public static final int SESSION_TIMEOUT = Integer.parseInt(System.getProperty("platform.session_timeout", "1800"));
    /**
     * Duracion de la sesion HTTP luego de un logout en segundos (tiempo maximo
     * de inactividad), 60 por defecto (1 minuto). Se lo puede definir como
     * argumento al iniciar la JVM:
     * <code>-Dciscnet.session_timeout_logout=60</code>
     */
    public static final int SESSION_TIMEOUT_LOGOUT = Integer.parseInt(System.getProperty("platform.session_timeout_logout", "60"));
    /**
     * Prefijo para el nombre de los cookies
     */
    public static final String COOKIE_PREFIX = "CIS$";
    /**
     * Nombre de cookie para codigo de empresa de inicio de sesion
     */
    public static final String COOKIE_EMPRESA = COOKIE_PREFIX + CryptoUtils.computeHashMD5("codigoEmpresaInicioSesion");
    /**
     * Timeout en segundos para cookie para codigo de empresa de inicio de
     * sesion (1 semana)
     */
    public static final int COOKIE_EMPRESA_TIMEOUT = DateUtils.SECONDS_PER_WEEK;
    /**
     * Nombre de cookie para skin
     */
    public static final String COOKIE_SKIN = COOKIE_PREFIX + CryptoUtils.computeHashMD5("skin");
    /**
     * Timeout en segundos para cookie para skin (1 semana)
     */
    public static final int COOKIE_SKIN_TIMEOUT = DateUtils.SECONDS_PER_WEEK;
    /**
     * Sufijo ".jsp"
     */
    public static final String JSP_SUFFIX = ".jsp";
    /**
     * Sufijo ".xhtml"
     */
    public static final String XHTML_SUFFIX = ".xhtml";
    /**
     * Sufijo ".jsf"
     */
    public static final String JSF_SUFFIX = ".jsf";
    /**
     * form id="frmIniciarSesion"
     */
    public static final String LOGIN_FORM_ID = "frmIniciarSesion";
    /**
     * form id="frmCerrarSesion"
     */
    public static final String LOGOUT_FORM_ID = "frmCerrarSesion";
    /**
     * Vista "/welcome"
     */
    public static final String WELCOME_VIEW = "/home";
    /**
     * Vista "/home"
     */
    public static final String HOME_VIEW = "/home";
    /**
     * Vista "/login"
     */
    public static final String LOGIN_VIEW = "/login";
    /**
     * --Vista "/sw/timeout"
     * Vista "/home"
     */
    public static final String TIMEOUT_VIEW = "/home";
    /**
     * {@link #TIMEOUT_VIEW} + {@link #JSF_SUFFIX}
     */
    public static final String TIMEOUT_PAGE = TIMEOUT_VIEW + JSF_SUFFIX;
    /**
     * Nombre de variable para guardar el token de sesion del userDto
     */
    public static final String TOKEN = "nkttxms";
    /**
     * Variable utilizada como nombre de variable de sesi&oacute;n
     */
    public static final String LOGGED_USER_ATTR = "security.currenLogonUser";
    /**
     * Swiss 2.0: Nombre de variable para guardar la lista de RoleDto del
     * UserDto
     */
    public static final String ROLES_BY_USER = "security.roles.currentUser";
    /**
     * Swiss 2.0: Nombre de variable para guardar las acciones permitidas por
     * cada Usuario JSF
     */
    public static final String ACTIONS_BY_USER = "security.actions.currentUser";
    /**
     * Swiss 2.0 (Struts): Nombre de variable para guardar los Items permitidos
     * por cada Usuario
     */
    public static final String ITEMS_BY_USER = "framework.items.currentUser";
    /**
     * Nombre de variable para guardar los Items permitidos por cada Usuario JSF
     */
    public static final String ITEMS_BY_USER_JSF = "framework.items.currentUserJSF";
    /**
     * Plantilla XSL para menus struts
     */
    public static String PLANTILLA = "menuH.xsl";
    /**
     * Nombre de variable para guardar el nombre de la compan&iacute;a
     */
    public static final String MAXIMO_REGISTROS_PERMITIDOS = "swiss.bean.current.maximoregistros";
    /**
     * Variable utilizada como nombre de la empresa Swiss seleccionada
     */
    public static String SWISS_EMPRESA_ATTR = "swiss.current.empresasSwiss";
    /**
     * Swiss 2.0: Variable utilizada como nombre de sesi&oacute;n para usuario
     * conectado
     */
    public static String SWISS_LOGGED_USER_ATTR = "swiss.current.usuarioSwiss";
    /**
     * Nombre de variable para guardar el nombre de la oficina seleccionada en
     * el Login
     */
    public static final String CURRENT_OFFICE = "swiss.bean.current.office";
    /**
     * Nombre de variable para guardar el nombre del punto de venta del usuario
     */
    public static final String CURRENT_PTO_VENTA = "swiss.bean.current.puntoVenta";
    /**
     * Variable para el nombre de la compania
     */
    public static final String CURRENT_COMPANY = "multicompany.currentCompany";
    /**
     * Variable para el nombre del Sistema
     */
    public static final String CURRENT_SYSTEM = "multicompany.currentSystem";
    /**
     * Variable para guardar si es o no multicompania
     */
    public static final String MULTICOMPANY_SESSION_ATTRIBUTE = "multicompanyservice";
    /**
     * Vriable para activar cookies
     */
    public static final String COOKIE_USER_ID = "Security.cookies.UserId";
    /**
     * Nombre de variable para guardar los datos de acceso al m&oacute:dulo de
     * seguridad
     */
    public final static String SECURITY_NAME_SESSION_ATTRIBUTE = "security";
    public static final String ADMINISTRACION_SESSION = "bean.administracion.session";
    /**
     * Nombre de variable de languaje actual
     */
    public static final String CURRENT_LANGUAGE = "internacionalization.currentLanguaje";
    /**
     * Nombre de variable para el manejo de label de bienvenida
     */
    public static final String CURRENT_LANGUAGE_LABELS = "WelcomeDto";
    /**
     * Nombre de variable para guardar el idioma a utilizar
     */
    public final static String I18N_NAME_SESSION_ATTRIBUTE = "i18n";
    /**
     * Nombre de variable que almacena los accesos por usuario y sistema
     */
    public static final String SESSION_NAME_MAP = "session_name_acciones";
    /**
     * Fecha de liberacion
     */
    public static final String RELEASE_DATE = DateUtils.getFormattedDate("dd-MM-yyyy HH:mm");
    /**
     * Version del SwisSystem en ejecucion, en base a la fecha de despliegue de
     * la aplicacion
     */
    @Getter
    private static String version;

    static {
        resetVersion();
    }

    public static final void resetVersion() {
        version = System.getProperty("swiss.version", Integer.toHexString(new Date().hashCode()));
    }
        
    /**
     * Nombre del tema con el que inicia el sistema, por default = 'aristo' 
     */
	public static final String tema = System.getProperty("platform.session.tema", "aristo");
        
}
