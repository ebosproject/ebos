package ec.com.platform.fwk.crud.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import ec.com.platform.fwk.crud.FinderSQLException;
import ec.com.platform.fwk.crud.FinderSQLService;
import ec.com.platform.fwk.crud.support.ParametrosProcedimiento;
import ec.com.platform.fwk.crud.support.StoreProcedureUtility;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-04
 */
public class FinderSQLServiceImpl extends TransactionProxyFactoryBean implements FinderSQLService {

	private static final long serialVersionUID = 1315613985673368485L;
	
	private static final String varSeqName = "${SECUENCIA}";

	private static final String endSenteceSeq = "Select " + varSeqName
			+ ".NEXTVAL AS next from dual";
	
	private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate = null;
	
	public FinderSQLServiceImpl(DataSource dataSource, Properties transactionAttributes) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		setTarget(this);
		setTransactionAttributes(transactionAttributes);
	}

	@Override
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		super.setTransactionManager(transactionManager);
	}
	

	// Clase interna que representa un RowMapper por default que retorna una
	// Lista de arreglos
	RowMapper<?> rowMapper = new RowMapper<Object>() {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Object mapRow(ResultSet resultSet, int pArg1)
				throws SQLException {
			List data = new ArrayList();
			while (resultSet.next()) {
				List datos = new ArrayList();
				int colCount = resultSet.getMetaData().getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					datos.add(resultSet.getString(i));// Se puede cambiar a
					// object con
					// resultSet.getObject(columnIndex)
				}
				data.add(datos.toArray());
			}
			return data;
		}

	};

	/**
	 * Retorna el siguiente valor de una secuencia
	 * 
	 * @param pNombreSecuencia
	 *            Nombre de la secuencia
	 * @return Valor del siguiente numero
	 * 
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al tratar de obtener la secuencia
	 */
	public Long nextValue(String pNombreSecuencia) throws FinderSQLException {
		try {
			String sql = StringUtils.replace(endSenteceSeq, varSeqName,
					pNombreSecuencia);
			long val = jdbcTemplate.queryForLong(sql);
			return new Long(val);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
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

	public Map<?,?> findByProcedimiento(String pNombreProcedimiento,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		try {
			StoreProcedureUtility storeProcedureUtility = new StoreProcedureUtility(
					jdbcTemplate.getDataSource(), pNombreProcedimiento,
					pParametros);
			return storeProcedureUtility.execute();
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
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
	public Map<?,?> findByFuncion(String pNombreFuncion,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		try {
			StoreProcedureUtility storeProcedureUtility = new StoreProcedureUtility(
					jdbcTemplate.getDataSource(), pNombreFuncion,
					pParametros, true);
			return storeProcedureUtility.execute();
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	/**
	 * Ejecuta un query sin parametros en la Base de datos
	 * 
	 * @param pSqlQuery
	 *            Cadena con el query a ejecutar
	 * @param pRowMapper
	 *            Clase indicar como se debe mapear el resultado obtenido en el
	 *             correspondiente
	 * @return Lista de 's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public List<?> executeQuery(String sqlQuery, RowMapper<?> pMapper)
			throws FinderSQLException {
		try {
			return jdbcTemplate.queryForList(sqlQuery);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
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
		try {
			return jdbcTemplate.queryForList(pSqlQuery, pArgs);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
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
	 *             correspondiente
	 * @return Lista de 's
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al ejecutar el query
	 */
	public List<?> executeQuery(String pSqlQuery, Object[] pArgs, RowMapper<?> pRowMapper) throws FinderSQLException {
		try {
			return jdbcTemplate.query(pSqlQuery, pArgs, pRowMapper);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	/***************************************************************************
	 * Metodos para exponer a futuro en caso de necesitarse
	 */
	public Object executeQueryForObject(String pSqlQuery, Object[] pArgs, RowMapper<?> pRowMapper) throws FinderSQLException {
		try {
			if (pRowMapper == null) {
				return jdbcTemplate.queryForObject(pSqlQuery, pArgs, rowMapper);
			}
			return jdbcTemplate.queryForObject(pSqlQuery, pArgs, pRowMapper);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	public Object executeQueryForObject(String pSqlQuery, RowMapper<?> pRowMapper)
			throws FinderSQLException {
		try {
			if (pRowMapper == null) {
				return jdbcTemplate.queryForObject(pSqlQuery, rowMapper);
			}
			return jdbcTemplate.queryForObject(pSqlQuery, pRowMapper);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	public Object executeQuery(String pSqlQuery, Object[] pArgs, ResultSetExtractor<?> pResultSetExtractor) throws FinderSQLException {
		try {
			return jdbcTemplate.query(pSqlQuery, pArgs, pResultSetExtractor);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	public Object executeQuery(String pSqlQuery, ResultSetExtractor<?> pExtractor) throws FinderSQLException {
		try {
			return jdbcTemplate.query(pSqlQuery, pExtractor);
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	public Map<?, ?> findByFuncionNoTx(String pNombreFuncion, ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		try {
			StoreProcedureUtility storeProcedureUtility = new StoreProcedureUtility(
					jdbcTemplate.getDataSource(), pNombreFuncion,
					pParametros, true);
			return storeProcedureUtility.execute();
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	public Map<?, ?> findByProcedimientoNoTx(String pNombreProcedimiento, ParametrosProcedimiento[] pParametros) throws FinderSQLException {
		try {
			StoreProcedureUtility storeProcedureUtility = new StoreProcedureUtility(
					jdbcTemplate.getDataSource(), pNombreProcedimiento,
					pParametros);
			return storeProcedureUtility.execute();
		} catch (Exception e) {
			throw new FinderSQLException(e);
		}
	}

	
}
