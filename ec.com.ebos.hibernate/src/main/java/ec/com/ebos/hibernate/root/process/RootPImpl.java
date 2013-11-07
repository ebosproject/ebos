package ec.com.ebos.hibernate.root.process;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.hibernate.PropertyValueException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ec.com.ebos.core.admin.exception.AdministracionException;
import ec.com.ebos.core.aspect.annotation.UniqueIndex;
import ec.com.ebos.core.aspect.annotation.UniqueIndexes;
import ec.com.ebos.core.aspect.exception.ExceptionAspectHandlerException;
import ec.com.ebos.core.context.EbosContext;
import ec.com.ebos.core.master.session.SessionBean;
import ec.com.ebos.core.orm.crud.Crud;
import ec.com.ebos.core.orm.crud.CrudService;
import ec.com.ebos.core.orm.crud.FinderService;
import ec.com.ebos.core.orm.crud.GenericCriteria;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.orm.crud.PaginationParams;
import ec.com.ebos.core.root.exception.RootException;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.root.process.RootP;
import ec.com.ebos.core.security.exception.SecurityException;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.FacesUtils;
import ec.com.ebos.core.util.MessageUtils;
import ec.com.ebos.core.util.ObjectUtils;
import ec.com.ebos.core.util.type.JsfMessage;
import ec.com.ebos.hibernate.admin.process.AdministracionPImpl;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;
import ec.com.ebos.hibernate.security.process.SecurityPImpl;


/**
 * Superclase de las implementaciones de la capa Process
 * @param <X> tipo del Entity base del modulo
 * @param <E> tipo de la excepcion
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public abstract class RootPImpl<X, E extends Exception> extends ProxyFactoryBean implements RootP, Serializable, JsfMessage{

	private static final long serialVersionUID = -204770942577453524L;

	/**
	 * Dependencias
	 */
	
	@Setter
	@Autowired
	@Qualifier("crudService")
	private CrudService crud;
	
	@Setter
	@Autowired
	@Qualifier("finderService")
	private FinderService finder;
	
	/**
	 * Inicializa target y attributes
	 */
	@PostConstruct
	public void init(){
		setTarget(this);
		setInterceptorNames(new String[]{"transactionInterceptor"});
	}
		

	//////////////// Acceso Generico a FinderService ////////////////////
	
	/**
	 * Llama a {@link FinderService#load(Class, Serializable)}
	 */
	protected <T extends X> T load(Serializable id, Class<T> entityType) {
		T obj = finder.load(entityType, id);
		return obj;
	} 
	
	/**
	 * Llama a {@link FinderService#get(Serializable, Class)}
	 */
	protected <T extends X> T get(Serializable id, Class<T> entityType) {
		T obj = finder.get(id, entityType);
		return obj;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria)}
	 */
	protected <T extends X> T findFirstByCriteria(GenericCriteria<T> criteria) {
		T value = ObjectUtils.getFirstOrNull(findByCriteria(criteria));
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria)}
	 */
	protected <T extends X> List<T> findByCriteria(GenericCriteria<T> criteria) {
		List<T> list = finder.findByCriteria(criteria);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, boolean)}
	 */
	protected <T extends X> List<T> findByCriteria(GenericCriteria<T> criteria, boolean pNolimitRecords) {
		List<T> list = finder.findByCriteria(criteria, pNolimitRecords);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, boolean)}
	 */
	@SuppressWarnings("unchecked")
	protected <T extends X, V> List<V> findByCriteria(GenericCriteria<T> criteria, Class<V> clazz, boolean pNolimitRecords) {
		List<V> list = (List<V>) finder.findByCriteria(criteria, pNolimitRecords);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, Class)}
	 */
	protected <T extends X, V> List<V> findByCriteria(GenericCriteria<T> criteria, Class<V> clazz) {
		List<V> list = finder.findByCriteria(criteria, clazz);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteriaWithProjection(GenericCriteria, Class)}
	 */
	protected <T extends X, V> List<V> findByCriteriaWithProjection(GenericCriteria<T> criteria, Class<V> clazz) {
		List<V> list = finder.findByCriteriaWithProjection(criteria, clazz);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, Class)}
	 */
	protected <T extends X, V> V findFirstByCriteria(GenericCriteria<T> criteria, Class<V> clazz) {
		V value = ObjectUtils.getFirstOrNull(findByCriteria(criteria, clazz, true));
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteriaWithProjection(GenericCriteria, Class)}
	 */
	protected <T extends X, V> V findFirstByCriteriaWithProjection(GenericCriteria<T> criteria, Class<V> clazz) {
		V value = ObjectUtils.getFirstOrNull(findByCriteriaWithProjection(criteria, clazz));
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, Class)}
	 * y agrega un rowcount para obtener el numero total de registros
	 * que devuelve la consulta del criteria
	 * @author Victor Viejo
	 */
	protected <T extends X> Integer countByCriteria(GenericCriteria<T> criteria) {
		if (criteria == null) {
			return 0;
		}
		criteria.setProjection(Projections.rowCount());
		Integer value = findFirstByCriteria(criteria, Integer.class);
		return value;
	}

	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, Class)}
	 * y agrega una proyeccion sum de la propiedad dada sobre los registros
	 * que devuelve la consulta del criteria
	 * @author Victor Viejo
	 */
	protected <T extends X> Double sumByCriteria(GenericCriteria<T> criteria, String propertyName) {
		if (criteria == null) {
			return 0D;
		}
		criteria.setProjection(Projections.sum(propertyName));
		Double value = findFirstByCriteria(criteria, Double.class);
		if (value == null) {
			value = 0D;
		}
		return value;
	}
	
	protected <T extends X> Long maxByCriteria(GenericCriteria<T> criteria, String propertyName) {
		if (criteria == null) {
			return 0L;
		}
		criteria.setProjection(Projections.max(propertyName));
		Long value = findFirstByCriteria(criteria, Long.class);
		return value;
	}

	protected <T extends X> Date maxDateByCriteria(GenericCriteria<T> criteria, String propertyName) {
		if (criteria == null) {
			return null;
		}
		criteria.setProjection(Projections.max(propertyName));
		Date value = findFirstByCriteria(criteria, Date.class);
		return value;
	}

	protected <T extends X> Date minDateByCriteria(GenericCriteria<T> criteria, String propertyName) {
		if (criteria == null) {
			return null;
		}
		criteria.setProjection(Projections.min(propertyName));
		Date value = findFirstByCriteria(criteria, Date.class);
		return value;
	}

	protected <T extends X> String minStringByCriteria(GenericCriteria<T> criteria, String propertyName) {
		criteria.setProjection(Projections.min(propertyName));
		String value = findFirstByCriteria(criteria, String.class);
		return value;
	}
	protected <T extends X> Long minLongByCriteria(GenericCriteria<T> criteria, String propertyName) {
		criteria.setProjection(Projections.min(propertyName));
		Long value = findFirstByCriteria(criteria, Long.class);
		return value;
	}
	protected <T extends X> Long maxLongByCriteria(GenericCriteria<T> criteria, String propertyName) {
		criteria.setProjection(Projections.max(propertyName));
		Long value = findFirstByCriteria(criteria, Long.class);
		return value;
	}

	protected <T extends X> String maxStringByCriteria(GenericCriteria<T> criteria, String propertyName) {
		criteria.setProjection(Projections.max(propertyName));
		String value = findFirstByCriteria(criteria, String.class);
		return value;
	}

	protected <T extends X> BigDecimal sumBigByCriteria(GenericCriteria<T> criteria, String propertyName) {
		if (criteria == null) {
			return BigDecimal.ZERO;
		}
		criteria.setProjection(Projections.sum(propertyName));
		BigDecimal value = findFirstByCriteria(criteria, BigDecimal.class);
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, int, int)}
	 */
	protected <T extends X> List<T> findByCriteria(GenericCriteria<T> criteria, int firstResult, int maxResult) {
		List<T> list = finder.findByCriteria(criteria, firstResult, maxResult);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, int, int)}
	 */
	protected <T extends X> T findFirstByCriteria(GenericCriteria<T> criteria, int firstResult, int maxResult) {
		T value = ObjectUtils.getFirstOrNull(findByCriteria(criteria, firstResult, maxResult));
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, PaginationParams)}
	 */
	protected <T extends X> List<T> findByCriteria(GenericCriteria<T> criteria, PaginationParams pagination) {
		List<T> list = finder.findByCriteria(criteria, pagination);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteria(GenericCriteria, Pagination)}
	 * @author Eduardo Plua Alay
	 */
	protected <T extends X> List<T> findByCriteria(GenericCriteria<T> criteria, Pagination pagination) {
		List<T> list = finder.findByCriteria(criteria, pagination);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findAll(Class, int)}
	 */
	protected <T extends X> List<T> findAll(Class<T> entityType, int max) {
		List<T> list = finder.findAll(entityType, max);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findAll(Class)}
	 */
	protected <T extends X> List<T> findAll(Class<T> entityType) {
		List<T> list = finder.findAll(entityType);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findAll(Class, String, boolean)}
	 */
	protected <T extends X> List<T> findAll(Class<T> entityType, String propertyName, boolean ascending) {
		List<T> list = finder.findAll(entityType, propertyName, ascending);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByCriteriaWithAliasToBeanTransformation(GenericCriteria, Class)}
	 */
	protected <T extends X, V extends X> List<V> findByCriteriaWithAliasToBeanTransformation(GenericCriteria<T> criteria, Class<V> clazz) {
		List<V> list = finder.findByCriteriaWithAliasToBeanTransformation(criteria, clazz);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#pagerFromEntity(Class, int, int)}
	 */
	protected <T extends X> List<T> pagerFromEntity(Class<T> clazz, int firstResult, int maxResults) {
		List<T> list = finder.pagerFromEntity(clazz, firstResult, maxResults);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByQuery(String)}
	 */
	protected List<?> findByQuery(String queryString) {
		List<?> list = finder.findByQuery(queryString);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findByQuery(String, Object[])}
	 */
	protected <V> List<V> findByQuery(String queryString, Class<V> clazz, Object... values) {
		@SuppressWarnings("unchecked")
		List<V> list = (List<V>) finder.findByQuery(queryString, values);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findByQuery(String, Object[], boolean)}
	 */
	protected <V> List<V> findByQuery(String queryString, Class<V> clazz, boolean pNolimitRecords, Object... values) {
		@SuppressWarnings("unchecked")
		List<V> list = (List<V>) finder.findByQuery(queryString, values, pNolimitRecords);
		return list;
	}

	/**
	 * Llama a {@link FinderService#findByQuery(String)}
	 */
	protected Object findFirstByQuery(String queryString) {
		Object value = ObjectUtils.getFirstOrNull(findByQuery(queryString));
		return value;
	}
	
	/**
	 * Llama a {@link FinderService#findAllWithoutSizeLimit(Class)}
	 */
	protected <T extends X> List<T> findAllWithoutSizeLimit(Class<T> entityType) {
		List<T> list = finder.findAllWithoutSizeLimit(entityType);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findAllWithoutSizeLimit(Class, String, boolean)}
	 */
	protected <T extends X> List<T> findAllWithoutSizeLimit(Class<T> entityType, String propertyName, boolean ascending) {
		List<T> list = finder.findAllWithoutSizeLimit(entityType, propertyName, ascending);
		return list;
	}
	
	/**
	 * Llama a {@link FinderService#findByNamedQueryAndNamedParam(String, String, Object)}
	 */
	protected List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) {
		List<?> list = finder.findByNamedQueryAndNamedParam(queryName, paramName, value);
		return list;
	}

	//////////////// Acceso Generico a Crud /////////////////

	/**
	 * Llama a {@link Crud#create(Object)}
	 * @param 
	 */
	protected <T extends X> T create(T entity) {
		// validar indices
		validateUniqueness(entity);
		// crear
		crud.create(entity);
		return entity;
	}

	/**
	 * Llama a {@link Crud#update(Object)}
	 * @param 
	 */
	protected <T extends X> T update(T entity) {
		// validar indices
		validateUniqueness(entity);
		// actualizar
		crud.update(entity);
		return entity;
	}

	/**
	 * Llama a {@link Crud#saveOrUpdate(Object)}
	 * @param 
	 */
	protected <T extends X> T saveOrUpdate(T entity) {
		// validar indices
		validateUniqueness(entity);
		// guardar
		crud.saveOrUpdate(entity);
		return entity;
	}
	
	/**
	 * Llama a {@link Crud#Save(Object) || Crud#merge(Object)}
	 * @param 
	 */
	protected <T extends X> T saveOrMerge(T entity) {
		// validar indices
		validateUniqueness(entity);
		// guardar o merge(retached object in ssession)
		if(EntityUtils.isPersistent((Entidad) entity)){
			crud.merge(entity);
		} else {
			crud.save(entity);
		}
		
		return entity;
	}

	/**
	 * Llama a {@link Crud#delete(Object)}
	 * @param 
	 */
	protected <T extends X> void delete(T entity) {
		crud.delete(entity);
	}

	/**
	 * Llama a {@link Crud#deleteAll(Object)}
	 * @param col
	 */
	protected <T extends X> void deleteAll(Collection<T> col) {
		crud.deleteAll(col);
	}
	
	/**
	 * Llama a {@link Crud#flush()}
	 */
	protected void flush() {
		crud.flush();
	}
	
	/**
	 * Llama a {@link Crud#merge()}
	 */
	@SuppressWarnings("unchecked")
	protected <T extends X> T merge(T entity) {
		return (T) crud.merge(entity);
	}
	
	/**
	 * Llama a {@link Crud#refresh()}
	 */
	protected <T extends X> T refresh(T entity) {
		crud.refresh(entity);
		return entity;
	}
	
	/**
	 * Llama a {@link Crud#evict()}.
	 * Desvincula el entity dado de la session de Hibernate.
	 * Permite manejar mas de una referencia a una entity,
	 * evitando errores tipo
	 * {@link org.hibernate.NonUniqueObjectException}.<br><br>
	 * Uso 1, cuando se hace los cambios manualmente:<pre>
	 * T oldEntity = findById(..); // obtener  original
	 * evict(oldEntity); // desvincularlo de la session
	 * T newEntity = findById(..); // obtener  para modificar
	 * ... // realizar cambios y validaciones
	 * update(newEntity); // guardar  modificado</pre>
	 * Uso 2, cuando los cambios ocurren primero:<pre>
	 * T newEntity = findById(..); // obtener  para modificar (pudo ocurrir en el MB)
	 * // realizar cambios (pudo ocurrir en el MB)
	 * evict(newEntity); // desvincularlo de la session
	 * T oldEntity = findById(..); // obtener  original
	 * ... // realizar validaciones
	 * newEntity = merge(newEntity); // combinar nuevo  con el original
	 * update(newEntity); // guardar</pre>
	 * @see http://stackoverflow.com/questions/183013/hibernate-comparing-current-previous-record/186561#186561
	 */
	protected <T extends X> T evict(T entity) {
		crud.evict(entity);
		return entity;
	}
	
	/**
	 * Llama a {@link Crud#getCurrentSession()}, que llama a {@link Session#connection()}
	 * @author Eduardo Plua Alay
	 */
	@SuppressWarnings("deprecation")
	protected Connection getConnection() {
		return crud.getCurrentSession().connection();
	}

	/**
	 * @see https://forum.hibernate.org/viewtopic.php?f=1&t=976550
	 * @return
	 * @throws E
	 */
	@SuppressWarnings("deprecation")
	protected String getServerURL() throws E {
		try {
			String url = crud.getCurrentSession().connection().getMetaData().getURL(); //TODO (epa): agregar soporte getHostname para Postgress
			String[] tokens = url.substring(url.indexOf("@") + 1).split(":");
			String hostname = tokens[0];
			String sid = tokens[2];
			InetAddress address = InetAddress.getByName(hostname);
			return address.getHostAddress() + ":" + sid;
		} catch (Exception e) {
			throw wrapException(e);
		}
	}

	/**
	 * Ejecuta el query SQL dado y retorna la lista de resultados.
	 * @see http://docs.jboss.org/hibernate/core/3.5/reference/en/html/querysql.html
	 * @return
	 * @throws E
	 */
	protected <T> List<T> findBySqlQuery(String queryString, Class<T> clazz) throws E {
		try {
			Session session = crud.getCurrentSession();
			SQLQuery q = session.createSQLQuery(queryString);
			
			@SuppressWarnings("unchecked")
			List<T> list = (List<T>) q.list();
			return list;
		} catch (Exception e) {
			throw wrapException(e);
		}
	}

	/**
	 * Encapsula la exception y lanza una nueva de tipo E
	 * 
	 * @param e
	 * @return E
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	protected E wrapException(Exception e) throws RuntimeException {
		e.printStackTrace();
		try {
			Exception x = null;
			if (this instanceof AdministracionPImpl) {
				x = new AdministracionException();
			} else if (this instanceof SecurityPImpl) {
				x = new SecurityException();
			}

			x.initCause(e);
			return (E) x;
		} catch (Exception y) {
			// no deberia suceder
			throw new RuntimeException(y);
		}
	}

	/**
	 * Encapsula un mensaje de exito
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapSuccessMessage(String keySummary, Object... params) {
//		getApp().wrapSuccessMessage(buildMessage(keySummary, params));
//	}

	/**
	 * Encapsula un mensaje de informacion
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapInfoMessage(String keySummary, Object... params) {
//		getApp().wrapInfoMessage(buildMessage(keySummary, params));
//	}

	/**
	 * Encapsula un mensaje de advertencia
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapWarningMessage(String keySummary, Object... params) {
//		getApp().wrapWarningMessage(buildMessage(keySummary, params));
//	}

	/**
	 * Encapsula un mensaje de error
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapErrorMessage(String keySummary, Object... params) {
//		getApp().wrapErrorMessage(buildMessage(keySummary, params));
//	}

	/**
	 * Encapsula un mensaje de error fatal
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapFatalMessage(String keySummary, Object... params) {
//		getApp().wrapFatalMessage(buildMessage(keySummary, params));
//	}

	/**
	 * Encapsula un mensaje privado
	 * @param keySummary
	 * @param params
	 */
//	protected void wrapPrivateMessage(String sender, String recipient, String keySummary, Object... params) {
//		getSesionUsuario().getAdmin().sendPrivateMessage(buildMessage(keySummary, params), sender, recipient);
//	}

//	/**
//	 * Devuelve el {@link SesionUsuarioMB} del hilo de ejecucion actual
//	 * @return {@link SesionUsuarioMB}
//	 * @see http://stackoverflow.com/questions/3320674/spring-how-do-i-inject-an-httpservletrequest-into-a-request-scoped-bean
//	 * @see org.springframework.web.context.request.RequestContextListener
//	 * @see org.springframework.web.context.request.RequestContextHolder
//	 */
//	protected SesionUsuarioMB getSesionUsuario() {
//		SesionUsuarioMB sesionUsuario = (SesionUsuarioMB) HTTPUtils.getSessionAttribute(SesionUsuarioMB.BEAN_NAME);
//		if (sesionUsuario == null) {
//			throw new SeguridadException("iniciarSesion.error.sesionNoValida");
//		}
//		return sesionUsuario;
//	}

//	/**
//	 * Devuelve el {@link ApplicationMB} del hilo de ejecucion actual
//	 * @return {@link ApplicationMB}
//	 */
//	private ApplicationMB getApp() {
//		return (ApplicationMB) HTTPUtils.getSessionAttribute("app");
//	}

//	/**
//	 * Devuelve el {@link AbstractServiceMB} del hilo de ejecucion actual
//	 * @return {@link AbstractServiceMB}
//	 */
//	protected AbstractServiceMB getBean() {
//		// el aspecto debe obtener el AbstractServiceMB
//		return null;
//	}


	public void sendMail(String subject, String msg, String from, String ... to){
		/*ApplicationContext context = 
	        new ClassPathXmlApplicationContext("Spring-Mail.xml");*/

		//SendMail mm = (SendMail) context.getBean("sendMail");
		if(from==null){
			from = "noreply@ebos.com.ec";
		}
		msg = msg + "\r\n\r\n\r\neBos: eBos, noreply";
//		sendMail.sendMail(subject, msg, from, to);
	}
	
	public void callStoreProcedure(String nameProcedure, Object ... params)
	throws E, SQLException{
        Configuration c = new Configuration().configure();
        SessionFactory sf = c.buildSessionFactory();
        Session session = sf.openSession();
        String numParams = "";
        
        if(params.length!=0)
        	numParams = "?";
        for (int i=1; i<params.length ; i++) {
        	numParams+=",?";
		}
        
        session.beginTransaction();
        Connection con = getConnection(); // .ob session.connection();  // obtain JDBC connection from Session object
        CallableStatement cs = con.prepareCall("{ call "+nameProcedure+"("+numParams+") }");
        int i=0;
        for (Object object : params) {
        	if(object instanceof Long) {
        		cs.setLong(i, (Long)object);
        	} if(object instanceof Double) {
        		cs.setDouble(i, (Double)object);
        	} if(object instanceof BigDecimal) {
        		cs.setBigDecimal(i, (BigDecimal)object);
        	} if(object instanceof String) {
        		cs.setString(i, (String)object);
        	}
        	i++;
		}
        cs.execute();  // call stored procedure

        session.getTransaction().commit();
        session.close();
        sf.close();
	}

	private Object callFunction(String nameProcedure, int type, Object ... params) throws SecurityException {
        //Configuration c = new Configuration().configure();
        //SessionFactory sf = c.buildSessionFactory();
        //Session session = sf.openSession();
		String numParams = "";

		if (params.length != 0)
			numParams = "?";
		for (int i = 1; i < params.length; i++) {
			numParams += ",?";
		}

        //session.beginTransaction();
        Connection con = getConnection(); // .ob session.connection();  // obtain JDBC connection from Session object
        Object result = null;
		try {
			CallableStatement cs = con.prepareCall("{ ? = call " + nameProcedure + "(" + numParams + ") }");
			cs.registerOutParameter(1, type);
			int i = 2;
			for (Object object : params) {
				if (object instanceof Long) {
					cs.setLong(i, (Long) object);
				} else if (object instanceof Double) {
					cs.setDouble(i, (Double) object);
				} else if (object instanceof BigDecimal) {
					cs.setBigDecimal(i, (BigDecimal) object);
				} else if (object instanceof String) {
					cs.setString(i, (String) object);
				} else if (object instanceof java.sql.Time) {
					cs.setTime(i, (java.sql.Time) object);
				} else if (object instanceof java.sql.Timestamp) {
					cs.setTimestamp(i, (java.sql.Timestamp) object);
				} else if (object instanceof java.sql.Date) {
					cs.setDate(i, (java.sql.Date) object);
				} else if (object instanceof Date) {
					cs.setTimestamp(i, new java.sql.Timestamp(((Date)object).getTime()));
				}
				i++;
			}
			cs.execute(); // call stored procedure
			result = cs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
//			throw new CorporativoException("callFunction.fatal.ErrorEjecutarFuncion", nameProcedure, e.getMessage());
		}
        //session.getTransaction().commit();
        //session.close();
        //sf.close();
        return result;
	}

	protected Long callFunctionLong(String nameProcedure, Object ... params) throws SecurityException {
		return (Long) callFunction(nameProcedure, Types.BIGINT, params);
	}

	protected Double callFunctionDouble(String nameProcedure, Object ... params) throws SecurityException {
		return (Double) callFunction(nameProcedure, Types.DOUBLE, params);
	}
	
	protected BigDecimal callFunctionBigDecimal(String nameProcedure, Object ... params) throws SecurityException {
		return (BigDecimal) callFunction(nameProcedure, Types.DECIMAL, params);
	}

	protected String callFunctionString(String nameProcedure, Object ... params) throws SecurityException {
		return (String) callFunction(nameProcedure, Types.VARCHAR, params);
	}

	/**
	 * Devuelve true si existe un registro del tipo especificado cuyos campos
	 * tengan los valores indicados
	 * @param clazz
	 * @param fieldNames
	 * @param values
	 * @return
	 */
	protected final <T extends X> boolean checkExists(Class<T> clazz, String fieldNames, Object... values) {
		if (StringUtils.isBlank(fieldNames) || ObjectUtils.isEmpty(values)) {
			// campos y valores requeridos
			return false;
		}
		String[] fields = fieldNames.split(",");
		if (fields.length != values.length) {
			// validar igual numero de nombres de campos y valores
			throw new IllegalArgumentException();
		}
		GenericCriteria<T> criteria = GenericCriteria.forClass(clazz);
		for (int i = 0; i < values.length; i++) {
			criteria.addEqualsNullSafe(fields[i], values[i]);
		}
		int count = countByCriteria(criteria);
		return (count > 0);
	}

	/**
	 * Verifica si existe un registro del tipo del  especificado
	 * que cumpla con los indices definidos en el tipo.
	 * Devuelve el nombre del primer indice para el cual los campos
	 * del  tienen valores existentes.
	 * @param 
	 * @return
	 */
	protected final <T extends X> String checkExists(T entity) {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) entity.getClass();
		UniqueIndexes annotation = clazz.getAnnotation(UniqueIndexes.class);
		if (annotation != null) {
			UniqueIndex[] indexes = annotation.value();
			if (!ObjectUtils.isEmpty(indexes)) {
				for (UniqueIndex index : indexes) {
					GenericCriteria<T> criteria = GenericCriteria.forClass(clazz);
					try {
						Field f = ObjectUtils.getField(clazz, index.idFieldName());
						f.setAccessible(true);
						Object value = f.get(entity);
						criteria.addNotEqualsIfNotNull(index.idFieldName(), value);
					} catch (Exception e) {
						throw new PropertyValueException(null, clazz.getName(), index.idFieldName());
					}
					String[] fields = index.fieldNames();
					for (String field : fields) {
						try {
							Field f = ObjectUtils.getField(clazz, field);
							f.setAccessible(true);
							Object value = f.get(entity);
							criteria.addEqualsNullSafe(field, value);
						} catch (Exception e) {
							throw new PropertyValueException(null, clazz.getName(), field);
						}
					}
					int count = countByCriteria(criteria);
					if (count > 0) {
						return index.indexName();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Lanza una excepcion si existe al menos un registro del tipo especificado cuyos campos
	 * tengan los valores indicados. Se utiliza el nombre del indice como clave del mensaje
	 * de la excepcion.
	 * @param indexName
	 * @param clazz
	 * @param fieldNames
	 * @param values
	 * @throws ConstraintViolationException
	 */
	protected final <T extends X> void validateUniqueness(String indexName, Class<T> clazz,
			String fieldNames, Object... values) throws ConstraintViolationException {
		if (checkExists(clazz, fieldNames, values)) {
			throw new ConstraintViolationException(null, null, indexName);
		}
	}

	/**
	 * Lanza una excepcion si existe al menos un registro del tipo del  especificado
	 * que cumple con al menos uno de los indices del tipo.
	 * Se utiliza el nombre del indice como clave del mensaje de la excepcion.
	 * @param 
	 * @throws ConstraintViolationException
	 */
	protected final <T extends X> void validateUniqueness(T entity) throws ConstraintViolationException {
		String indexName = checkExists(entity);
		if (indexName != null) {
			throw new ConstraintViolationException(null, null, indexName);
		}
	}

	
	/**
	 * Construye un mensaje utilizando el archivo de propiedades
	 * del proyecto correspondiente al modulo
	 * @param key
	 * @param args
	 * @return
	 */
	private String buildMessage(String key, Object... args) {
		return MessageUtils.getFormattedMessage(FacesUtils.getLabel(key), args);
	}
	
    public void putSuccess(String key, Object... args) {        
       getSessionBean().putSuccess(buildMessage(key, args));
    }

    public void putWarning(String key, Object... args) {
    	getSessionBean().putWarning(buildMessage(key, args));        
    }

    public void putError(String key, Object... args) {
    	getSessionBean().putError(buildMessage(key, args));        
    }
    
	public void putError(ExceptionAspectHandlerException e) {
		getSessionBean().setSuccess(false);
		
		e.printStackTrace();
		MessageUtils.clearFacesMessages(FacesMessage.SEVERITY_WARN);
		
		if (e instanceof RootException && !((RootException) e).isFatal()) {
			getSessionBean().putError(e.getKey(), e.getMessage());
		} else {
			getSessionBean().putFatal(e.getKey(), e.getMessage());
		}
	}
    
    public void putFatal(String key, Object... args) {
    	getSessionBean().putFatal(buildMessage(key, args));        
    }

//    /**
//     * Devuelve el {@link SessionBean} del hilo de ejecucion actual
//     *
//     * @return {@link SessionBean}
//     * @see
//     * http://stackoverflow.com/questions/3320674/spring-how-do-i-inject-an-httpservletrequest-into-a-request-scoped-bean
//     * @see org.springframework.web.context.request.RequestContextListener
//     * @see org.springframework.web.context.request.RequestContextHolder
//     */
    protected SessionBean getSessionBean() {
    	SessionBean sessionBean = EbosContext.getBean(SessionBean.BEAN_NAME);
        if (sessionBean == null) {
            throw new SecurityException("session.error.sessionNoValid");
        }
        return sessionBean;
    }

    protected Auditoria getAuditoria(){
    	return new HibernateAuditoria();
    }

}
