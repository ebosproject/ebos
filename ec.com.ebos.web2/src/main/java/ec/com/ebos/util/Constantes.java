package ec.com.ebos.util;



/**
 * <p>Clase que cotiene el valor de variables globales para toda la plataforma</p>
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class Constantes {

    /**
     * Domain name system
     */
    public static final String DOMAIN_NAME = "ec.com.ebos";
    
    /**
     * Session timeout.logon, default 1800 (30 min)
     */
    public static final int SESSION_TIMEOUT_LOGON = Integer.parseInt(System.getProperty("ebos.session.timeout.logon", "1800"));
    
    /**
     * Session timeout.logout, default 60 (1 min)
     */
    public static final int SESSION_TIMEOUT_LOGOUT = Integer.parseInt(System.getProperty("ebos.session.timeout.logout", "60"));

    /**
     * Session show log events
     */
    public static final boolean SESSION_LOGGING = Boolean.parseBoolean(System.getProperty("ebos.session.logging", "TRUE"));
    
    /**
     * Current user session var
     */
    public static final String SESSION_ATTR_CURRENTUSER = "ebos.session.attr.currentuser";
    
    /**
     * Suffix ".xhtml"
     */
    public static final String XHTML_SUFFIX = ".xhtml";
    /**
     * Suffix ".jsf"
     */
    public static final String JSF_SUFFIX = ".jsf";
    
    /**
     * Session theme, default 'aristo' 
     */
	public static final String THEME_KEY = "theme";
    
    /**
     * Session theme, default 'aristo' 
     */
	public static final String THEME = System.getProperty("ebos.session.tema", "aristo");
	
	/**
	 * Resource bundle name default 
	 */
	public static final String MODULE_BUNDLE_NAME = Constantes.DOMAIN_NAME+".generic.resources.generic";
	
	/**
	 * Resource bundle prefix not found
	 */
	public static final String PREFIX_NOT_FOUND = "???";

	/**
	 * Growl component widget name into north.xhtml
	 */
	public static final String wgtGrowlHome = "wgtGrwHome";

	/**
	 * Nombre de variable de {@link DatabaseDrivenResourceBundle} del contexto jsf
	 */
	public static final String RESOURCE_BUNDLE_VAR = "msg";
	
}
