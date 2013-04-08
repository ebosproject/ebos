package ec.com.ebos.fwk.service.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import ec.com.ebos.fwk.service.LogFwkPLatform;
import ec.com.ebos.fwk.service.LogSvc;
import ec.com.ebos.fwk.service.Printer;

/**
 * @author mbermude
 */
public class LogServicioInterceptor implements MethodInterceptor {

	
	private String fuente = LogSvc.LOG_INTCPT;
	/**
	 * Cache de instancias de Printers
	 */
	private static Map<String, Object> allPrintersObj = new HashMap<String, Object>();

	/**
	 * propiedad para Spring Printers de conversion
	 * qclass_nombre_de_objeto_a_convertir=qclass_de_Printer_(implementa ec.com.ebos.fwk.servicio.Printer) 
	 */
	private Properties printers;
	
	/**
	 * propiedad para Spring metodos a los que se les realizara seguimiento
	 */
	private String metodos;

	/**
	 * propiedad para Spring nombre del Servicio al que se le hace seguimiento
	 */
	private String nombreSvc;
	
	
	/**
	 * Propiedades internas
	 */
	private List<String> metodosList=new LinkedList<String>();
	private LogSvc logSvc;
	private boolean logTodos;

	/**
	 * Metodo (getter) de acceso a printers.
	 * 
	 * @return Retorna printers.
	 */
	public Properties getPrinters() {
		return printers;
	}

	/**
	 * Metodo (setter) de Modfificacion de printers
	 * 
	 * @param pPrinters
	 *            printers a Asignar.
	 *            recibe los parametros de conversion par aun Properties
	 *            para Spring
	 *            &lt;value&gt;
	 *            qclass_nombre_de_objeto_a_convertir=qclass_de_Printer_(implementa ec.com.ebos.fwk.servicio.Printer)
	 *            qclass_nombre_de_Otro_a_convertir=qclass_de_OtroPrinter_(implementa ec.com.ebos.fwk.servicio.Printer) 
	 *            &lt;/value&gt;
	 *            
	 *            	 */
	@SuppressWarnings("rawtypes")
	public void setPrinters(Properties pPrinters) {
		printers = pPrinters;
		if (printers != null) {
			Enumeration enumP = printers.keys();
			while (enumP.hasMoreElements()) {
				String entity = (String) enumP.nextElement();
				String converter = printers.getProperty(entity);
				try {
					Object printer = Class.forName(converter).newInstance();
					if (printer instanceof Printer) {
						allPrintersObj.put(entity, printer);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * Metodo (getter) de acceso a logSvc.
	 * 
	 * @return Retorna logSvc.
	 */
	public LogSvc getLogSvc() {
		return logSvc;
	}

	/**
	 * Metodo (setter) de Modfificacion de logSvc
	 * 
	 * @param pLogSvc
	 *            logSvc a Asignar.
	 */
	public void setLogSvc(LogSvc pLogSvc) {
		logSvc = pLogSvc;
	}

	/**
	 * Metodo (getter) de acceso a metodos.
	 * 
	 * @return Retorna metodos.
	 */
	public String getMetodos() {
		return metodos;
	}

	/**
	 * Metodo (setter) de Modfificacion de metodos
	 * 
	 * @param pMetodos
	 *            metodos a Asignar.
	 *          recibe los nombres de los metodos separados por comas
	 *          o '*' para todos 
	 *           
	 */
	public void setMetodos(String pMetodos) {
		metodos = pMetodos;
		if (!StringUtils.isEmpty(metodos)) {
			metodos = StringUtils.deleteWhitespace(pMetodos);
			metodosList = Arrays.asList(StringUtils.split(metodos, ","));
			logTodos = "*".equals(metodos);
		}
	}

	/**
	 * Metodo (getter) de acceso a nombreSvc.
	 * 
	 * @return Retorna nombreSvc.
	 */
	public String getNombreSvc() {
		return nombreSvc;
	}

	/**
	 * Metodo (setter) de Modfificacion de nombreSvc
	 * 
	 * @param pNombreSvc
	 *            nombreSvc a Asignar.
	 */
	public void setNombreSvc(String pNombreSvc) {
		nombreSvc = pNombreSvc;
	}

	/*
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object rval = invocation.proceed();
		String name = invocation.getMethod().getName();
		if (logTodos || metodosList.contains(name)) {
			String entity = arguments2Entity(invocation.getArguments());
			writeToLog(entity, name);
		}
		return rval;
	}

	public void writeToLog(String pObj, String pAccion) {

		LogFwkPLatform logFwkSat = new LogFwkPLatform(fuente, pObj, pAccion);
		logSvc.writeToLog(logFwkSat);
	}

	private String arguments2Entity(Object[] pArguments) {
		String entityStr = StringUtils.EMPTY;
		if (pArguments.length > 0) {
			StringBuffer buffer = new StringBuffer("{");
			for (int i = 0; i < pArguments.length; i++) {
				Object obj = pArguments[i];
				String key = obj.getClass().getName();
				Object pPrinter = allPrintersObj.get(key);
				String str = null;
				if (pPrinter != null)
					str = ((Printer) pPrinter).print(obj);
				else
					str = ReflectionToStringHbmBean.toString(obj);
				buffer.append("[" + str + "]");
			}
			buffer.append("}");
			entityStr = buffer.toString();
		}
		return entityStr;
	}

	/**
	 * Metodo (getter) de acceso a fuente.
	 * @return Retorna fuente.
	 */
	public String getFuente() {
		return fuente;
	}

	/**
	 * Metodo (setter) de Modfificacion de fuente
	 * @param pFuente fuente a Asignar.
	 */
	public void setFuente(String pFuente) {
		fuente = pFuente;
	}

}
