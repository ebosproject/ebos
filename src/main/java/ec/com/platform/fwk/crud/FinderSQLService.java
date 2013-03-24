package ec.com.platform.fwk.crud;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import ec.com.platform.fwk.crud.support.ParametrosProcedimiento;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-05 
 */
public interface FinderSQLService {
	
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
	public Long nextValue(String pNombreSecuencia) throws FinderSQLException;

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
			ParametrosProcedimiento[] pParametros) throws FinderSQLException;

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
	public Map<?, ?> findByFuncion(String pNombreFuncion,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException;

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
			throws FinderSQLException;

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
			throws FinderSQLException;

	/**
	 * Ejecuta un query con los parametros asociados
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
			RowMapper<?> pRowMapper) throws FinderSQLException;

	// public Object executeQueryForOject(String pSqlQuery, RowMapper
	// pRowMapper)
	// throws FinderSQLException;
	//
	// public Object executeQueryForOject(String pSqlQuery, Object[] pArgs,
	// RowMapper pRowMapper) throws FinderSQLException;

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
			ResultSetExtractor<?> pExtractor) throws FinderSQLException;

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
			throws FinderSQLException;
	
	
	
	/**
	 * Ejecuta un procedimiento de la base de datos
	 * fuera de la Transaccion actual existente
	 * si la invocacion al SP hace commit o rollback
	 * se maneja independiente de la Tx actual
	 * 
	 * para invocar el procedimiento dentro de la transacion actual 
	 * ver findByProcedimiento
	 * @param pNombreProcedimiento
	 *            Nombre del procediemiento
	 * @param pParametros
	 *            Arreglo de parametros recibido por dicho procedimiento
	 * @return Mapa con los valores retornados por el procediminto
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al invocar el procedimiento
	 */
	public Map<?, ?> findByProcedimientoNoTx(String pNombreProcedimiento,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException;

	/**
	 * Ejecuta una funcion de la Base de datos
	 * fuera de la Transaccion actual existente
	 * si la invocacion al SP hace commit o rollback
	 * se maneja independiente de la Tx actual
	 * 
	 * para invocar el procedimiento dentro de la transacion actual 
	 * ver findByFuncion
	 * @param pNombreFuncion
	 *            Nombre de la funcion
	 * @param pParametros
	 *            Arreglo de parametros recibido por la funcion
	 * @return Mapa con los valores retornados por la funcion
	 * @throws FinderSQLException
	 *             Si ocurre alguna excepcion al invocar la funcion
	 */
	public Map<?, ?> findByFuncionNoTx(String pNombreFuncion,
			ParametrosProcedimiento[] pParametros) throws FinderSQLException;

}
