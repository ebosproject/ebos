package ec.com.ebos.core.context;

/**
 * Tipos de Scopes soportados por Spring Framework
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 *
 */
public final class BeanScopes {
	
	/**
	 * Retorna una unica instancia de un bean por el contenedor Spring IoC. Valor por defecto.
	 */
	public static final String SINGLETON = "singleton";
	
	/**
	 * Retorna una nueva instancia de un bean cada vez que es requerido
	 */
	public static final String PROTOTYPE = "prototype";
	
	/**
	 * Retorna una unica instancia por HTTP request. Valido solo en el contexto de una aplicacion web
	 */
	public static final String REQUEST = "request";
	
	/**
	 * Retorna una unica instancia por HTTP session. Valido solo en el contexto de una aplicacion web
	 */
	public static final String SESSION = "session";
	
	
	/**
	 * Retorna una unica instancia por global HTTP session. Valido solo en el contexto de una aplicacion web
	 */
	public static final String GLOBAL_REQUEST = "globalSession";
	
	
}
