package ec.com.ebos.util;

import java.util.Date;

import lombok.Getter;
import ec.com.ebos.util.crypto.CryptoUtils;

/**
 * <p>Clase que cotiene el valor de variables globales a nivel de todas las
 * aplicaciones de Framework</p>
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class Constantes {

    /**
     * Nombre de dominio del sistema
     */
    public static final String DOMAIN_NAME = "ec.com.ebos";
    
    /**
     * Duracion de la sesion HTTP en segundos (tiempo maximo de inactividad),
     * 1800 por defecto (30 minutos). Se lo puede definir como argumento al
     * iniciar la JVM:
     * <code>-Debos.session_timeout=1800</code>
     */
    public static final int SESSION_TIMEOUT = Integer.parseInt(System.getProperty("ebos.session_timeout", "1800"));
    /**
     * Duracion de la sesion HTTP luego de un logout en segundos (tiempo maximo
     * de inactividad), 60 por defecto (1 minuto). Se lo puede definir como
     * argumento al iniciar la JVM:
     * <code>-Debos.session_timeout_logout=60</code>
     */
    public static final int SESSION_TIMEOUT_LOGOUT = Integer.parseInt(System.getProperty("ebos.session_timeout_logout", "60"));
    /**
     * Prefijo para el nombre de los cookies
     */
    public static final String COOKIE_PREFIX = "EBOS$";

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
    public static final String WELCOME_VIEW = "/welcome";
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
    public static final String TIMEOUT_VIEW = "/timeout";
    
    /**
     * Variable utilizada como nombre de variable de sesi&oacute;n
     */
    public static final String LOGGED_USER_ATTR = "security.currenLogonUser";
    
    
    /**
     * Variable utilizada como nombre de sesi&oacute;n para usuario
     * conectado
     */
    public static String PLATFORM_LOGGED_USER_ATTR = "ebos.current.usuarioEbos";
    
    /**
     * Nombre del tema con el que inicia el sistema, por default = 'aristo' 
     */
	public static final String tema = System.getProperty("ebos.session.tema", "aristo");
	
	/**
	 * Resource Bundle name por default de un modulo 
	 */
	public static final String MODULE_BUNDLE_NAME = Constantes.DOMAIN_NAME+".generic.resources.generic";
	
	/**
	 * Resource bundle not prefix not found
	 */
	public static final String PREFIX_NOT_FOUND = "???";
	
	public static final boolean LOGGING = Boolean.parseBoolean(System.getProperty("ebos.session_timeout.logging", "TRUE"));
        
}
