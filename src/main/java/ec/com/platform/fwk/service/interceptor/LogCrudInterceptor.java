package ec.com.platform.fwk.service.interceptor;

import ec.com.platform.fwk.service.LogFwkPLatform;
import ec.com.platform.fwk.service.LogSvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

/**
 * @author mbermude
 */
public class LogCrudInterceptor implements MethodInterceptor {

	private static final Log log = LogFactory.getLog(LogCrudInterceptor.class);
	private String entidadesNoLog;
	private List entities = new ArrayList();
	private SessionFactory sessionFactory;
	private LogSvc logSvc;

	private static final String CREAR = "create";

	private static final String ACTUALIZAR = "update";

	private static final String ELIMINAR = "delete";

	private static final String ACC_CREAR = "crear";

	private static final String ACC_ACTUALIZAR = "actualizar";

	private static final String ACC_ELIMINAR = "eliminar";
	

	/**
	 * Metodo (getter) de acceso a logSvc.
	 * @return Retorna logSvc.
	 */
	public LogSvc getLogSvc() {
		return logSvc;
	}

	/**
	 * Metodo (setter) de Modfificacion de logSvc
	 * @param pLogSvc logSvc a Asignar.
	 */
	public void setLogSvc(LogSvc pLogSvc) {
		logSvc = pLogSvc;
	}

	/*
	 * 
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object rval = invocation.proceed();
		String name = invocation.getMethod().getName();
		String accion = null;

		if (StringUtils.containsIgnoreCase(name, CREAR))
			accion = ACC_CREAR;
		else if (StringUtils.containsIgnoreCase(name, ACTUALIZAR))
			accion = ACC_ACTUALIZAR;
		else if (StringUtils.containsIgnoreCase(name, ELIMINAR))
			accion = ACC_ELIMINAR;

		if (accion != null) {
			
			try {
				Object arg0 = invocation.getArguments()[0];
				if (!(arg0 instanceof String)) {
					writeToLog(arg0, accion);
				} else {
					writeToLogBulk((String) arg0);
				}
			} catch (Exception e) {
				log.error(" No se puede escribir el LOG CRUD " + e);
			}			
		}
		return rval;
	}

	public void writeToLog(Object pObj, String pAccion) {
		String entityName = pObj.getClass().getName();
		if(entities.contains(entityName)) return;
		String pkgName = pObj.getClass().getPackage().getName();
		if(entities.contains(pkgName)) return;
		String tabla = StringUtils.EMPTY;
		ClassMetadata cl = sessionFactory.getClassMetadata(pObj.getClass());
		if (cl instanceof org.hibernate.persister.entity.SingleTableEntityPersister) {
			org.hibernate.persister.entity.SingleTableEntityPersister md = (org.hibernate.persister.entity.SingleTableEntityPersister) cl;
			tabla = md.getTableName();
			
		}
		String entidad = "["+tabla+"]" + ":" + ReflectionToStringHbmBean.toString(pObj);		
		LogFwkPLatform logFwkSat = new LogFwkPLatform(LogSvc.LOG_CRUD, entidad ,pAccion);
		logSvc.writeToLog(logFwkSat);
		
	}
	
	public void writeToLogBulk(String pStr) {
		LogFwkPLatform logFwkSat = new LogFwkPLatform(LogSvc.LOG_CRUD, pStr ,"Actualizacion-Bloque");
		logSvc.writeToLog(logFwkSat);	
	}
	

	/**
	 * Metodo (getter) de acceso a sessionFactory.
	 * 
	 * @return Retorna sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Metodo (setter) de Modfificacion de sessionFactory
	 * 
	 * @param pSessionFactory
	 *            sessionFactory a Asignar.
	 */
	public void setSessionFactory(SessionFactory pSessionFactory) {
		sessionFactory = pSessionFactory;
	}



	/**
	 * Metodo (setter) de Modfificacion de entidadesNoLog
	 * @param pEntidadesNoLog entidadesNoLog a Asignar.
	 */
	public void setEntidadesNoLog(String pEntidadesNoLog) {
		entidadesNoLog = pEntidadesNoLog;
		if (entidadesNoLog != null || StringUtils.isEmpty(entidadesNoLog)) {
			entidadesNoLog = StringUtils.deleteWhitespace(entidadesNoLog);
			entities = Arrays.asList(StringUtils.split(entidadesNoLog, ","));
		}
		
	}

	

}
