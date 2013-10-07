package ec.com.ebos.orm.crud;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.AliasToBeanResultTransformer;

import ec.com.ebos.orm.crud.support.SessionExpuestos;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-04
 * 
 */
public interface FinderService extends FinderSQLService, SessionExpuestos{
	/**
	 * No requiere lock, este es el que se usa por defecto
	 */
	public static final int LOCK_NONE = 0;
	/**
	 * Lock compartido, los objetos fueron leidos desde la base
	 * de datos en la transaccion actual, mas que del cache
	 */
	public static final int LOCK_READ = 5;
	/**
	 * Un lock de Upgrade. los objetos se obtienen con la 
	 * sentencia  SQL <tt>select ... for update</tt>.
	 */
	public static final int LOCK_UPGRADE = 10;
	/**
	 * Trata de obtener Lock para Upgrade usando Oracle-style
	 * <tt>select for update nowait</tt>. la semantica es similar a
	 * <tt>UPGRADE</tt>.
	 */
	public static final int LOCK_UPGRADE_NOWAIT = 11;
	/**
	 * Un lock  <tt>WRITE</tt> se obtiene cuando un objeto 
	 * es actualizado o insertado. Modo no valido para <tt>load()</tt>
	 * o <tt>lock()</tt>.
	 */
	public static final int LOCK_WRITE = 12;
	
	
	public Integer getMaxResults();
	
	/**
	 * Busca todas las entidades cuyo tipo es igual a entityType.
	 * La busqueda se restringe al valor getMaxResults del Servicio
	 * configurado en la inicializaion del servicio
	 * @param entityType
	 *            de entidad.
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public <T> List<T> findAll(Class<T> entityType) throws FinderException;

	
	/**
	 * Busca todas las entidades cuyo tipo es igual a entityType.
	 * La busqueda se restringe al valor getMaxResults del Servicio
	 * configurado en la inicializaion del servicio, con un limite de registros
	 * @param entityType
	 *            de entidad.
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public <T> List<T> findAll(Class<T> entityType, int pMaxRecords) throws FinderException;
		
	/**
	 * Busca una entidad cuyo id es el pasado por parametro.
	 * 
	 * @param id
	 * @param entityType
	 *            de entidad
	 * 
	 * @return entidad de tipo entityClass
	 * 
	 * @throws FinderException
	 */
	public <T> T get(Serializable id, Class<T> entityType)
			throws FinderException;

	/**
	 * Ejecuta una query cuyo nombre es queryName. Esta query debe estar
	 * configurada en el mapping de hibernate.
	 * La busqueda se restringe al valor getMaxResults del Servicio
	 * configurado en la inicializaion del servicio
	 * @param queryName
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName) throws FinderException;

	/**
	 * Ejecuta una query cuyo nombre es queryName. Esta query debe estar
	 * configurada en el mapping de hibernate.
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 * @param queryName
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName, boolean pNolimitRecords) throws FinderException;
	
	
	
	/**
	 * Ejecuta una query cuyo nombre es queryName y la cual posee un binding
	 * "?". Esta query debe estar configurada en el mapping de hibernate.<br>
	 * 
	 * @param queryName
	 * @param value
	 *            el valor del binding
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName, Object value)
			throws FinderException;

	/**
	 * Ejecuta una query cuyo nombre es queryName y la cual posee un binding
	 * "?". Esta query debe estar configurada en el mapping de hibernate.<br>
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 * @param queryName
	 * @param value
	 *            el valor del binding
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName, Object value, boolean pNolimitRecords)
			throws FinderException;

	
	
	
	/**
	 * Ejecuta una query cuyo nombre es queryName y la cual posee bindings "?".
	 * Esta query debe estar configurada en el mapping de hibernate.<br>
	 * 
	 * @param queryName
	 * @param values
	 *            valores correspondientes a los bindings.
	 * 
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName, Object[] values)
			throws FinderException;

	/**
	 * Ejecuta una query cuyo nombre es queryName y la cual posee bindings "?".
	 * Esta query debe estar configurada en el mapping de hibernate.<br>
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 *
	 * @param queryName
	 * @param values
	 *            valores correspondientes a los bindings.
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta
	 * @return List
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQuery(String queryName, Object[] values, boolean pNolimitRecords)
			throws FinderException;
	
	
	/**
	 * Ejecuta un criteria.
	 * 
	 * @param criteria
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public List<?> findByCriteria(DetachedCriteria criteria)
			throws FinderException;

	/**
	 * Ejecuta un generic criteria.
	 * @param <T>
	 * @param criteria {@link GenericCriteria}
	 * @return
	 * @throws FinderException
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria)
			throws FinderException;

	/**
	 * Ejecuta un generic criteria aplicando projection.
	 * @param <T>
	 * @param criteria {@link GenericCriteria}
	 * @return
	 * @throws FinderException
	 */
	public <T> List<T> findByCriteriaWithProjection(GenericCriteria<T> criteria)
			throws FinderException;

	/**
	 * Ejecuta un generic criteria y hace un cast del resultado.
	 * @param <T>
	 * @param <V>
	 * @param criteria {@link GenericCriteria}
	 * @param clazz
	 * @return
	 * @throws FinderException
	 */
	public <T,V> List<V> findByCriteria(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException;

	/**
	 * Ejecuta un generic criteria aplicando projection y hace un cast del resultado.
	 * @param <T>
	 * @param <V>
	 * @param criteria {@link GenericCriteria}
	 * @param clazz
	 * @return
	 * @throws FinderException
	 */
	public <T,V> List<V> findByCriteriaWithProjection(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException;

	/**
	 * Ejecuta un generic criteria, transformando el resultado mediante un {@link AliasToBeanResultTransformer}.
	 * @param <T>
	 * @param <V>
	 * @param criteria {@link GenericCriteria}
	 * @param clazz
	 * @return
	 * @throws FinderException
	 */
	public <T,V> List<V> findByCriteriaWithAliasToBeanTransformation(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException;

	
	/**
	 * Ejecuta un criteria.
	 * 
	 * @param criteria
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public List<?> findByCriteria(DetachedCriteria criteria,int firstResult,int maxResult)
			throws FinderException;

	/**
	 * 
	 * @param <T>
	 * @param criteria
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws FinderException
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, int firstResult, int maxResult)
		throws FinderException;

	/**
	 * 
	 * @param <T>
	 * @param criteria
	 * @param pagination
	 * @return
	 * @throws FinderException
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, PaginationParams pagination)
		throws FinderException;
	
	/**
	 * @param criteria
	 * @param pagination
	 * @return
	 * @throws FinderException
	 * @author Eduardo Plua Alay
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, Pagination pagination)
			throws FinderException;

	/**
	 * Ejecuta un criteria.
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 * @param criteria
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public List<?> findByCriteria(DetachedCriteria criteria, boolean pNolimitRecords)
			throws FinderException;
	
	/**
	 * 
	 * @param <T>
	 * @param criteria
	 * @param pNolimitRecords
	 * @return
	 * @throws FinderException
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, boolean pNolimitRecords)
			throws FinderException;

	/**
	 * Ejecuta un criteria y devuelve el primer resultado. Si no hay resultado
	 * devuelve <code>null</code>.
	 * 
	 * @param criteria
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public Object uniqueResult(DetachedCriteria criteria)
			throws FinderException;

	/**
	 * Itera una query.
	 * 
	 * @param queryString
	 * @param value
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public Iterator<?> iterate(String queryString, Object value)
			throws FinderException;

	/**
	 * Itera una query.
	 * 
	 * @param queryString
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public Iterator<?> iterate(String queryString) throws FinderException;

	/**
	 * Itera una query.
	 * 
	 * @param queryString
	 * @param value
	 *            value
	 * 
	 * @return
	 * 
	 * @throws FinderException
	 */
	public Iterator<?> iterate(String queryString, Object[] value)
			throws FinderException;

	/**
	 * Este metodo se encarga cargara una entidad en base a su id
	 * 
	 * @param entityType
	 *            Typo de la entidad a cargar
	 * @param entityId
	 *            Id de la entidad a cargar
	 * 
	 * @return Entidad que cumple con el criterio (que se identifica con el Id
	 *         enviado)
	 * 
	 * @throws FinderException
	 * 
	 * @since 10/08/2006 12:15:26 PM
	 */
	public <T> T load(Class<T> entityType, Serializable entityId)
			throws FinderException;

	/**
	 * Este metodo recibe un nombre de Query que debe estar mapeado en Hibernate
	 * y retornar un resultado de acuerdo a los parametros enviados y al tamano
	 * de pagina y al numero de pagina recibidos. page la pagina que deseo
	 * recibir pageSize el tamano de pagina
	 * 
	 * @param queryName
	 *            query configurada en el mapping de hibernate.
	 * @param values
	 *            parametros del query
	 * @param page
	 *            Numero de Pagina que se desea recibir
	 * @param pageSize
	 *            Tamano de Pagina
	 * 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 10/08/2006 12:15:26 PM
	 */
	public List<?> pagerNamedQuery(String queryName, Object[] values,
			int page, int pageSize) throws FinderException;

	
	/**
	 * Este metodo recibe un Entity class que debe estar mapeado en Hibernate
	 * y retornar un resultado de acuerdo a los parametros enviados desde el primer
	 * resultado hasta un tamano maximo
	 * 
	 * @param clazz
	 *            Entidad configurada en el mapping de hibernate.
	 * @param firstResult
	 *            Valor inicial para buscar
	 * @param maxResults
	 *            Maximo numero de resultados 
	 * 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 10/08/2006 12:15:26 PM
	 */
	public <T> List<T> pagerFromEntity(Class<T> clazz, int firstResult, int maxResults) throws FinderException;
	
	
	/**
	 * Este metodo recibe un Query(Cadena con el query HQL) y retornar un resultado
	 * 
	 * @param queryString
	 *            Cadena que contiene el query a ejecutar
	 * 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 06/09/2006 12:15:26 PM
	 */
	public List<?> findByQuery(String queryString) throws FinderException;

	/**
	 * Este metodo recive un Query(Cadena con el query) y retornar un resultado
	 * Puede quitarse el limite de devolucion de un numero de resultados
	 * @param queryString
	 *            Cadena que contiene el query a ejecutar
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 06/09/2006 12:15:26 PM
	 */
	public List<?> findByQuery(String queryString, boolean pNolimitRecords) throws FinderException;
	
	/**
	 * Este metodo recive un Query(Cadena con el query) y retornar un resultado
	 * de acuerdo a los parametros enviados
	 * 
	 * @param queryString
	 *            Cadena que contiene el query
	 * @param values
	 *            parametros del query
	 * 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 06/09/2006 12:15:26 PM
	 */
	public List<?> findByQuery(String queryString, Object[] values)
			throws FinderException;

	/**
	 * Este metodo recive un Query(Cadena con el query) y retornar un resultado
	 * de acuerdo a los parametros enviados
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 * @param queryString
	 *            Cadena que contiene el query
	 * @param values
	 *            parametros del query
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta 
	 * @return List lista con los resultados obtenidos
	 * 
	 * @throws FinderException
	 * 
	 * @since 06/09/2006 12:15:26 PM
	 */
	public List<?> findByQuery(String queryString, Object[] values, boolean pNolimitRecords)
			throws FinderException;
	
 	/**
	 * Retorna la session activa, 
	 * Por la especifiacion de la arquitectura la Session no se debe exponer
	 * en el caso de necesitar metodos de Session de Hibernate se deben usar los metodos
	 * expuestos para eso, ver la interface SessionExpuestos 
	 * 
	 * exponen los metodos para eso
	 * @eliminado
	 * @return Session
	 */
	//public Session getActiveSession();
 
	

	

	/**
	 * Metodo para realizacion de una consulta, que llama un NamedQuery, pero
	 * que como parametro recibe un data transfer object con la informacion de
	 * los parametros de la consulta
	 * 
	 * @param pQueryString
	 *            El nombre del NamedQuery
	 * @param p
	 *            Java Bean Object con las propiedades de consulta
	 * 
	 * @return List de Objetos
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQueryAndValueBean(String pQueryString,
			Object p) throws FinderException;

	
	/**
	 * Metodo para realizacion de una consulta, que llama un NamedQuery, pero
	 * que como parametro recibe un data transfer object con la informacion de
	 * los parametros de la consulta
	 * 
	 * @param pQueryString
	 *            El nombre del NamedQuery
	 * @param p
	 *            Java Bean Object con las propiedades de consulta
	 * @param pMaxResults
	 *            Numero maximo de registros en la consulta
	 * @return List de Objetos
	 * 
	 * @throws FinderException
	 */
//	public List findByNamedQueryAndValueBean(String pQueryString,
//			Object p, int pMaxResults) throws FinderException;
	
	/**
	 * Metodo para realizacion de una consulta, que llama un NamedQuery, y recibe
	 * un parametro nombrado y su valor, este parametro puede ser una lista en 
	 * el caso de que la consulta incluya una clausula IN por ejemplo.
	 * 
	 * @param queryName Nombre del named query
	 * @param paramName Nombre del parametro
	 * @param value Valor del Parametro
	 * @return Lista con los objetos encontrados
	 * 
	 * @throws FinderException
	 */
	public List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws FinderException;
	

	/**
	 * Metodo para realizacion de una consulta, que llama un NamedQuery, y recibe
	 * un arreglo de parametros nombrados y un arreglo de valores (objetos) correspondientes, 
	 * los valores pueden ser una lista en el caso de que la consulta incluya una(s) 
	 * clausula(s) IN por ejemplo.
	 * 
	 * @param queryName Nombre del named query
	 * @param paramNames Nombre del parametro
	 * @param values Valor del Parametro
	 * @return Lista con los objetos encontrados
	 * @throws FinderException
	 */
	public List<?> findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values) throws FinderException;

	/**
	 * Metodo para realizacion de una consulta, que llama un NamedQuery, y recibe
	 * un arreglo de parametros nombrados y un arreglo de valores (objetos) correspondientes, 
	 * los valores pueden ser una lista en el caso de que la consulta incluya una(s) 
	 * clausula(s) IN por ejemplo.
	 * Puede quitarse el limite de devoucion de un numero de resultados
	 * @param queryName Nombre del named query
	 * @param paramNames Nombre del parametro
	 * @param values Valor del Parametro
	 * @param pNolimitRecords Si es verdadero (true) se retorna el numero completo de registros de la consulta
	 * @return Lista con los objetos encontrados
	 * @throws FinderException
	 */
	public List<?> findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values, boolean pNolimitRecords) throws FinderException;

	
	public <T> T getEntity(Class<T> pClass, Serializable pId,  int pLockMode);
	
	public List<?> pagerQueryString(String pQueryString, Object[] pValues, int page, int pageSize) throws FinderException;
	
	public <T> List<T> findAll(Class<T> entityType, String propertyName, boolean ascending) throws FinderException;
	
	public <T> List<T> findAll(Class<T> entityType, String propertyName) throws FinderException;
	
	public <T> List<T> findAllWithoutSizeLimit(Class<T> entityType) throws FinderException;
	
	public <T> List<T> findAllWithoutSizeLimit(Class<T> entityType, String propertyName,
			boolean ascending) throws FinderException;

}
