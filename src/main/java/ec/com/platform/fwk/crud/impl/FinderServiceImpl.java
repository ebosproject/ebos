package ec.com.platform.fwk.crud.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import ec.com.platform.fwk.crud.FinderException;
import ec.com.platform.fwk.crud.FinderSQLException;
import ec.com.platform.fwk.crud.FinderSQLService;
import ec.com.platform.fwk.crud.FinderService;
import ec.com.platform.fwk.crud.GenericCriteria;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.fwk.crud.PaginationParams;
import ec.com.platform.fwk.crud.support.ParametrosProcedimiento;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-04
 */
public class FinderServiceImpl extends TransactionProxyFactoryBean implements FinderService {

	private static final long serialVersionUID = 7062230011964573755L;

	@Setter
	private FinderSQLService finderSql = null;
	
	private HibernateTemplate hibernateTemplate = null;
	
	private int maxRecords = 500;
	
	public FinderServiceImpl(SessionFactory sessionFactory,
			int transactionMaxRegistros, FinderSQLService finderSQLService,
			Properties transactionAttributes) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);		
		setMaxRegistros(transactionMaxRegistros);
		this.finderSql = finderSQLService;
		setTarget(this);
		setTransactionAttributes(transactionAttributes);
	}

	@Override
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		super.setTransactionManager(transactionManager);
	}
	

	/**
	 * Metodo (setter) de Modfificacion de maxRecords
	 * 
	 * @param pMaxRecords
	 *            maxRecords a Asignar.
	 */
	public void setMaxRegistros(int pMaxRecords) {
		maxRecords = pMaxRecords;
		hibernateTemplate.setMaxResults(maxRecords);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> entityType) throws FinderException {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(entityType);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			hibernateTemplate.setMaxResults(maxRecords);
			return (List<T>) hibernateTemplate.findByCriteria(criteria);
			//return hibernateTemplate.loadAll(entityType);
		} catch (Exception e) {
			throw new FinderException(e);
		}

	}
	
	public <T> List<T> findAll(Class<T> entityType, int pMaxRecords) throws FinderException {
		try {
			hibernateTemplate.setMaxResults(pMaxRecords);
			return (List<T>)hibernateTemplate.loadAll(entityType);
		} catch (Exception e) {
			throw new FinderException(e);
		}

	}
	
	public <T> T findById(Serializable id, Class<T> entityType)
			throws FinderException {
		try {
			return (T)hibernateTemplate.get(entityType, id);
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public List<?> findByNamedQuery(String queryName) throws FinderException {
		try {
			return hibernateTemplate.findByNamedQuery(queryName);
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public List<?> findByNamedQuery(String queryName, Object value)
			throws FinderException {
		try {
			return hibernateTemplate.findByNamedQuery(queryName, value);
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public List<?> findByNamedQuery(String queryName, Object[] values)
			throws FinderException {
		try {
			return hibernateTemplate.findByNamedQuery(queryName, values);

		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public List<?> findByCriteria(DetachedCriteria criteria)
			throws FinderException {
		return findByCriteriaWithResultTransformer(criteria, CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<T>();
		}
		return (List<T>) findByCriteriaWithResultTransformer(criteria.getCriteria(), CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteriaWithProjection(GenericCriteria<T> criteria)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<T>();
		}
		return (List<T>) findByCriteriaWithResultTransformer(criteria.getCriteria(), CriteriaSpecification.PROJECTION);
	}

	@SuppressWarnings("unchecked")
	public <T,V> List<V> findByCriteria(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<V>();
		}
		return (List<V>) findByCriteriaWithResultTransformer(criteria.getCriteria(), CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}

	@SuppressWarnings("unchecked")
	public <T,V> List<V> findByCriteriaWithProjection(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<V>();
		}
		return (List<V>) findByCriteriaWithResultTransformer(criteria.getCriteria(), CriteriaSpecification.PROJECTION);
	}

	@SuppressWarnings("unchecked")
	public <T,V> List<V> findByCriteriaWithAliasToBeanTransformation(GenericCriteria<T> criteria, Class<V> clazz)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<V>();
		}
		return (List<V>) findByCriteriaWithResultTransformer(criteria.getCriteria(), new AliasToBeanResultTransformer(clazz));
	}

	/**
	 * Ejecuta un generic criteria, con el ResultTransformer especificado
	 * @param criteria 
	 * @param resultTransformer
	 * @return
	 * @throws FinderException
	 * @author Luis Tama Wong
	 */
	private List<?> findByCriteriaWithResultTransformer(DetachedCriteria criteria, ResultTransformer resultTransformer)
			throws FinderException {
		try {
			criteria.setResultTransformer(resultTransformer);
			return hibernateTemplate.findByCriteria(criteria);

		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public Iterator<?> iterate(String queryString) throws FinderException {
		return hibernateTemplate.iterate(queryString);
	}

	public Iterator<?> iterate(String queryString, Object value)
			throws FinderException {
		return hibernateTemplate.iterate(queryString, value);
	}

	public Iterator<?> iterate(String queryString, Object[] values)
			throws FinderException {
		return hibernateTemplate.iterate(queryString, values);
	}

	public Object uniqueResult(DetachedCriteria criteria)
			throws FinderException {
		try {
			return criteria.getExecutableCriteria(getCurrentSession()).uniqueResult();

		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object load(Class pEntityType, Serializable pEntityId)
			throws FinderException {
		return hibernateTemplate.load(pEntityType, pEntityId);
	}

	public List<?> pagerNamedQuery(String pQueryName, Object[] pValues,
			int page, int pageSize) throws FinderException {
		Query queryObject = getCurrentSession().getNamedQuery(pQueryName);
		if (pValues != null) {
			for (int i = 0; i < pValues.length; i++) {
				queryObject.setParameter(i, pValues[i]);
			}
		}

		if (page >= 1 && pageSize > 0) {
			queryObject.setFirstResult((--page) * pageSize);
			queryObject.setMaxResults(pageSize);
		}

		return queryObject.list();

	}
	
	public List<?> pagerQueryString(String pQueryString, Object[] pValues,
			int page, int pageSize) throws FinderException {
		Query queryObject = getCurrentSession().createQuery(pQueryString);
		if (pValues != null) {
			for (int i = 0; i < pValues.length; i++) {
				queryObject.setParameter(i, pValues[i]);
			}
		}

		if (page >= 1 && pageSize > 0) {
			queryObject.setFirstResult((--page) * pageSize);
			queryObject.setMaxResults(pageSize);
		}

		return queryObject.list();

	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> pagerFromEntity(Class<T> clazz, int firstResult, int maxResults) throws FinderException {
		return (List<T>) getCurrentSession().createCriteria(clazz)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.list();
	}

	public List<?> findByQuery(String pQueryString) throws FinderException {
		return hibernateTemplate.find(pQueryString);
	}

	public List<?> findByQuery(String pQueryString, Object[] pValues)
			throws FinderException {
		return hibernateTemplate.find(pQueryString, pValues);
	}

	public List<?> findByNamedQueryAndValueBean(String pQueryString,
			Object pDTO) throws FinderException {
		return hibernateTemplate.findByNamedQueryAndValueBean(
				pQueryString, pDTO);
	}

	public <T> List<T> findAll(Class<T> entityType, String propertyName)
			throws FinderException {
		try {
			return (List<T>)findAll(entityType, propertyName, true);
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> entityType, String propertyName,
			boolean ascending) throws FinderException {
		try {
			Criteria criteria = getCurrentSession().createCriteria(entityType);
			criteria.setMaxResults(maxRecords);

			if (propertyName != null) {
				if (ascending)
					criteria.addOrder(Order.asc(propertyName));
				else
					criteria.addOrder(Order.desc(propertyName));
			}

			return (List<T>) criteria.list();
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}

	public List<?> findByNamedQueryAndNamedParam(String pQueryName,
			String paramName, Object pValue) throws FinderException {
		return hibernateTemplate.findByNamedQueryAndNamedParam(pQueryName,
				paramName, pValue);
	}

	public List<?> findByNamedQueryAndNamedParam(String pQueryName,
			String[] paramNames, Object[] pValues) throws FinderException {
		return hibernateTemplate.findByNamedQueryAndNamedParam(pQueryName,
				paramNames, pValues);
	}

	/*public List findByNamedQueryAndValueBean(String pQueryString,
			Object pDTO, int pMaxResults) throws FinderException {
		if (pMaxResults > 1000 || pMaxResults < 0)
//			throw new FinderException("Numero de registros invalido :"
//					+ pMaxResults);
			throw new FinderException();
		hibernateTemplate.setMaxResults(pMaxResults);
		List col = findByNamedQueryAndValueBean(pQueryString, pDTO);
		hibernateTemplate.setMaxResults(maxRecords);
		return col;
	}*/

	/** ****************************************************** */
	/**
	 * METODOS QUE NO LIMITAN EL NUMERO DE REGISTROS RESULTADO /* (non-Javadoc)
	 * 
	 */
	
	public <T> List<T> findAllWithoutSizeLimit(Class<T> entityType) throws FinderException {
		try {
			return (List<T>)hibernateTemplate.loadAll(entityType);
		} catch (Exception e) {
			throw new FinderException(e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllWithoutSizeLimit(Class<T> entityType, String propertyName,
			boolean ascending) throws FinderException {
		try {
			Criteria criteria = getCurrentSession().createCriteria(entityType);

			if (propertyName != null) {
				if (ascending)
					criteria.addOrder(Order.asc(propertyName));
				else
					criteria.addOrder(Order.desc(propertyName));
			}

			return (List<T>) criteria.list();
		} catch (Exception e) {
			throw new FinderException(e);
		}
	}
	
	public List<?> findByCriteria(DetachedCriteria pCriteria,
			boolean pNolimitRecords) throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate
					.findByCriteria(pCriteria);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByCriteria(pCriteria);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, boolean pNolimitRecords)
			throws FinderException {
		if (criteria == null) {
			return new ArrayList<T>();
		}
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0); // FIXME: considerar ResultTransformer
			List<T> result = (List<T>) hibernateTemplate
					.findByCriteria(criteria.getCriteria());
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByCriteria(criteria);
		}
	}

	public List<?> findByNamedQuery(String pQueryName,
			boolean pNolimitRecords) throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate.findByNamedQuery(
					pQueryName);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByNamedQuery(pQueryName);
		}
	}

	public List<?> findByNamedQuery(String pQueryName, Object pValue,
			boolean pNolimitRecords) throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate.findByNamedQuery(
					pQueryName, pValue);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByNamedQuery(pQueryName, pValue);
		}
	}

	public List<?> findByNamedQuery(String pQueryName, Object[] pValues,
			boolean pNolimitRecords) throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate.findByNamedQuery(
					pQueryName, pValues);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByNamedQuery(pQueryName, pValues);
		}
	}

	public List<?> findByNamedQueryAndNamedParam(String pQueryName,
			String[] paramNames, Object[] pValues, boolean pNolimitRecords)
			throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate
					.findByNamedQueryAndNamedParam(pQueryName, paramNames,
							pValues);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByNamedQueryAndNamedParam(pQueryName, paramNames,
					pValues);
		}
	}

	public List<?> findByQuery(String pQueryString, boolean pNolimitRecords)
			throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate.find(pQueryString);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByQuery(pQueryString);
		}
	}

	public List<?> findByQuery(String pQueryString, Object[] pValues,
			boolean pNolimitRecords) throws FinderException {
		if (pNolimitRecords) {
			hibernateTemplate.setMaxResults(0);
			List<?> result = hibernateTemplate.find(pQueryString,
					pValues);
			hibernateTemplate.setMaxResults(maxRecords);
			return result;
		} else {
			return findByQuery(pQueryString, pValues);
		}
	}

	public List<?> findByCriteria(DetachedCriteria pCriteria,
			int pFirstResult, int pMaxResult) throws FinderException {
		try {
			hibernateTemplate.setMaxResults(0);
			return hibernateTemplate.findByCriteria(pCriteria,
					pFirstResult, pMaxResult);
		} catch (Exception e) {
			throw new FinderException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(GenericCriteria<T> pCriteria,
			int pFirstResult, int pMaxResult) throws FinderException {
		if (pCriteria == null) {
			return new ArrayList<T>();
		}
		try {
			hibernateTemplate.setMaxResults(0);
			return (List<T>) hibernateTemplate.findByCriteria(pCriteria.getCriteria(),
					pFirstResult, pMaxResult);
		} catch (Exception e) {
			throw new FinderException(e);
		}

	}

	/**
	 * 
	 * @param <T>
	 * @param criteria
	 * @param pagination
	 * @return
	 * @throws FinderException
	 * @author Luis Tama Wong
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, PaginationParams pagination)
		throws FinderException {
		// obtener lista con el criteria con paginacion
		List<T> list = findByCriteria(criteria, pagination.getFirstRow(), pagination.getMaxRows());
		// obtener total de registros con el criteria, quitando paginacion
		Integer count = 0;
		if (criteria != null) {
			criteria.setProjection(Projections.rowCount());
			Criteria executableCriteria = criteria.getExecutableCriteria(getCurrentSession());
			executableCriteria.setFirstResult(0);
			executableCriteria.setMaxResults(1);
			count = (Integer) executableCriteria.uniqueResult();
		}
		pagination.setTotalRows(count);
		return list;
	}
	
	
	/**
	 * @author Eduardo Plua Alay
	 */
	public <T> List<T> findByCriteria(GenericCriteria<T> criteria, Paginacion paginacion)
			throws FinderException {
		// obtener lista con el criteria con paginacion
		List<T> list = findByCriteria(criteria, paginacion.getFirst(), paginacion.getPageSize());
		// obtener total de registros con el criteria, quitando paginacion
		Integer count = 0;
		if (criteria != null) {
			criteria.setProjection(Projections.rowCount());
			Criteria executableCriteria = criteria.getExecutableCriteria(getCurrentSession());
			executableCriteria.setFirstResult(0);
			executableCriteria.setMaxResults(1);
			count = (Integer) executableCriteria.uniqueResult();
		}
		paginacion.setRowCount(count);
		return list;
	}

	public Integer getMaxResults() {
		return new Integer(maxRecords);
	}

	public static Collection<Map<String, Object>> execute(Connection con, String pProcedure,
			String[] pParams) throws FinderException {
		CallableStatement cst = null;
		try {
			String sql = createSql(pProcedure, pParams);
			cst = con.prepareCall(sql);
			cst.registerOutParameter(1, -10);

			for (int i = 0; i < pParams.length; i++) {
				cst.setString((i + 2), pParams[i]);
			}
			cst.execute();
			return convert((ResultSet) cst.getObject(1), new ArrayList<Object>(),
					new HashMap<Object, Object>(), false);

		} catch (Exception e) {
			throw new FinderException(e);
		} finally {
			try {
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (Exception e1) {
				throw new FinderException(e1);
			}
		}

	}

	private static String createSql(String pProcedure, String[] pParams) {
		StringBuffer sql = new StringBuffer("{call ");
		sql.append(pProcedure + "(");
		sql.append("?,");
		for (int i = 0; i < pParams.length; i++) {
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")}");
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	public static Collection<Map<String, Object>> convert(ResultSet rs, Collection<Object> rowsCol,
			Map<Object, Object> columnsMap, boolean string) {
		Collection<Map<String, Object>> rows = null;
		if (rs != null) {
			try {
				Class<?> clazz = rowsCol.getClass();
				rows = (Collection<Map<String, Object>>) clazz.newInstance();
				clazz = columnsMap.getClass();

				Object clm = null;
				Map<String, Object> clms = null;
				ResultSetMetaData rsmd = null;
				rsmd = rs.getMetaData();

				int clmCnt = rsmd.getColumnCount();

				while (rs.next()) {
					clms = (Map<String, Object>) clazz.newInstance();

					for (int i = 1; i <= clmCnt; i++) {
						if (string) {
							clm = rs.getString(i);
						} else {
							clm = rs.getObject(i);
						}
						clms
								.put(new String(rsmd.getColumnName(i)
										.toLowerCase()), clm);
					}
					rows.add(clms);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return rows;
	}


	/**
	 * Retorna el siguiente valor de una secuencia
	 * 
	 * @param pNombreSecuencia
	 *            Nombre de la secuencia
	 * @return Valor del siguiente numero
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al tratar de obtener la secuencia
	 */
	public Long nextValue(String pNombreSecuencia) throws FinderSQLException {
		return finderSql.nextValue(pNombreSecuencia);
	}

	/**
	 * Ejecuta un procedimiento de la base de datos
	 * 
	 * @param pNombreProcedimiento
	 *            Nombre del procediemiento
	 * @param pParametros
	 *            Arreglo de parametros recibido por dicho procedimiento
	 * @return Mapa con los valores retornados por el procediminto
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al invocar el procedimiento
	 */
	public Map<?, ?> findByProcedimiento(String pNombreProcedimiento,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		return finderSql.findByProcedimiento(pNombreProcedimiento, pParametros);
	}

	/**
	 * Ejecuta una funcion de la Base de datos
	 * 
	 * @param pNombreFuncion
	 *            Nombre de la funcion
	 * @param pParametros
	 *            Arreglo de parametros recibido por la funcion
	 * @return Mapa con los valores retornados por la funcion
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al invocar la funcion
	 */
	public Map<?, ?> findByFuncion(String pNombreProcedimiento,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		return finderSql.findByFuncion(pNombreProcedimiento, pParametros);
	}
	

	public Map<?, ?> findByFuncionNoTx(String pNombreFuncion, ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		return finderSql.findByFuncionNoTx(pNombreFuncion, pParametros);
	}

	public Map<?, ?> findByProcedimientoNoTx(String pNombreProcedimiento, ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		return finderSql.findByProcedimientoNoTx(pNombreProcedimiento, pParametros);
	}

	/**
	 * Eejecuta un query con los parametros asociados
	 * 
	 * @param pSqlQuery
	 *            Cadena que representa el query
	 * @param pArgs
	 *            Arreglo de objetos con los parametros a enviar
	 * @param pRowMapper
	 *            Clase indicar como se debe mapear el resultado obtenido en el
	 *            DTO correspondiente
	 * @return Lista de DTO's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public List<?> executeQuery(String pSqlQuery, Object[] pArgs,
			RowMapper<?> pRowMapper) throws FinderSQLException {

		return finderSql.executeQuery(pSqlQuery, pArgs, pRowMapper);
	}

	/**
	 * Ejecuta un query sin parametros en la Base de datos
	 * 
	 * @param pSqlQuery
	 *            Cadena con el query a ejecutar
	 * @param pRowMapper
	 *            Clase indicar como se debe mapear el resultado obtenido en el
	 *            DTO correspondiente
	 * @return Lista de DTO's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public List<?> executeQuery(String pSqlQuery, RowMapper<?> pRowMapper)
			throws FinderSQLException {
		return finderSql.executeQuery(pSqlQuery, pRowMapper);
	}

	/**
	 * Metodo que ejecuta un query y recibe los parametros asociados
	 * 
	 * @param pSqlQuery
	 *            Cadena que representa el query
	 * @param pArgs
	 *            Arreglo de objetos con los parametros a enviar
	 * @return Lista de mapas con los resultados
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public List<?> executeQuery(String pSqlQuery, Object[] pArgs, boolean pRestrict)
			throws FinderSQLException {
		return finderSql.executeQuery(pSqlQuery, pArgs, pRestrict);
	}

	/**
	 * Ejecuta un query con los parametros asociados
	 * 
	 * @param pSqlQuery
	 *            Cadena que representa el query
	 * @param pArgs
	 *            Arreglo de objetos con los parametros a enviar
	 * @param pResultSetExtractor
	 *            Clase indicar como se debe mapear el resultado obtenido en el
	 *            DTO correspondiente
	 * @return Lista de DTO's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public Object executeQuery(String pSqlQuery, Object[] pArgs,
			ResultSetExtractor<?> pExtractor) throws FinderSQLException {
		return finderSql.executeQuery(pSqlQuery, pArgs, pExtractor);
	}

	/**
	 * Ejecuta un query sin parametros en la Base de datos
	 * 
	 * @param pSqlQuery
	 *            Cadena con el query a ejecutar
	 * @param pResultSetExtractor
	 *            Clase indicar como se debe mapear el resultado obtenido en el
	 *            DTO correspondiente
	 * @return Lista de DTO's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public Object executeQuery(String pSqlQuery, ResultSetExtractor<?> pExtractor)
			throws FinderSQLException {
		return finderSql.executeQuery(pSqlQuery, pExtractor);
	}

	// Para implementar a futuro en caso de ser necesario
	// public Object executeQueryForOject(String pSqlQuery, RowMapper
	// pRowMapper)
	// throws FinderSQLException {
	// return finderSql.executeQueryForOject(pSqlQuery, pRowMapper);
	// }
	//
	// public Object executeQueryForOject(String pSqlQuery, Object[] pArgs,
	// RowMapper pRowMapper) throws FinderSQLException {
	// return finderSql.executeQueryForOject(pSqlQuery, pArgs, pRowMapper);
	// }

	public Session getCurrentSession() {
    	return hibernateTemplate.getSessionFactory().getCurrentSession();
    }
	
	public void evict(Object pEntity) {
		getCurrentSession().evict(pEntity);
	}

	public void flush() {
		getCurrentSession().flush();
	}

	public void refresh(Object pEntity) {
		getCurrentSession().refresh(pEntity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getEntity(Class pClass, Serializable pId, int pLockMode) {
		LockMode lock = null;
		switch (pLockMode) {
		case LOCK_READ:
			lock = LockMode.READ;
			break;
		case LOCK_UPGRADE:
			lock = LockMode.UPGRADE;
			break;
		case LOCK_UPGRADE_NOWAIT:
			lock = LockMode.UPGRADE_NOWAIT;
			break;
		case LOCK_WRITE:
			lock = LockMode.WRITE;
			break;
		default:
			lock = LockMode.NONE;
			break;
		}
		return getCurrentSession().get(pClass, pId, lock);
	}
	/** ************************************************************************** */


}
