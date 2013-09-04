package ec.com.ebos.aspect.core.process;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.LazyInitializationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.aspect.core.exception.SecurityAspectException;
import ec.com.ebos.orm.crud.CrudException;
import ec.com.ebos.orm.crud.FinderException;
import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.root.web.jsf.bean.RootBean;
import ec.com.ebos.util.DateUtils;
import ec.com.ebos.util.MessageUtils;
import ec.com.ebos.util.StringUtils;

/**
 * Exception aspects
 * 
 * @author Eduardo Plua Alay
 * @since 2013-09-04
 */
@Aspect
@Component
public class ExceptionAspectPImpl {

	
	private static final Logger logger = Logger.getLogger(ExceptionAspectPImpl.class);

	@Pointcut("execution(public * *(..))")
	public void anyPublicMethodExecution() {
	}
	

//	@Pointcut("call(* get*(..)) || call(* is*(..))")
//	public void anyGetterMethod() {}

//	@Pointcut("execution(new(..))")
//	public void anyConstructorExecution() {}

//	@Pointcut("execution(* set*(..))")
//	public void anySetterMethodExecution() {}

//	@Pointcut("call(* ec.com.ecuaquimica.swiss..*(..))")
//	public void anySwissMethod() {}

	@Pointcut("execution(public * ec.com.ebos.*.web.jsf.bean.*Bean.*(..))")
	public void anyBeanPublicMethodExecution() {}

//	/**
//	 * Encapsula posibles excepciones lanzadas durante la ejecucion de metodos
//	 * publicos de beans que sean subclase de {@link RootBean}. No se
//	 * intercepta constructores ni metodos que retornen Entities que sean subclases
//	 * de {@link Entidad}.
//	 * 
//	 * @see #handleException(RootBean, Throwable)
//	 * @param pjp
//	 *            {@link ProceedingJoinPoint}
//	 * @param bean
//	 *            {@link RootBean}
//	 * @return Object
//	 */
//	@Around("anyPublicMethodExecution() && this(bean)")
//	public Object interceptUnhandledExceptionsOnMBMethod(
//			ProceedingJoinPoint pjp, RootBean<?> bean) {
//		try {
//			if(logger.isDebugEnabled()){
//				logger.debug(bean);
//			}
//			if(logger.isInfoEnabled()){
//				logger.info(bean);
//			}
//			if(logger.isTraceEnabled()){
//				logger.trace(bean);
//			}
//			return pjp.proceed();
//		} catch (Throwable t) {
//			handleException(bean, t);
//			return null;
//		}
//	}

	
	@Around("anyBeanPublicMethodExecution()")
	public void loginAround(ProceedingJoinPoint joinPoint) {

		try{
			System.out.println("logAround() is running!");
			System.out.println("hijacked method : " + joinPoint.getSignature().getName());
			System.out.println("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
			
			System.out.println("Around before is running!");
			
			joinPoint.proceed();
			
			System.out.println("Around after is running!");
			
			System.out.println("******");

		} catch(Throwable t){
			//handleException(bean, t);
		}
		
	}
	
//	/**
//	 * Encapsula posibles excepciones lanzadas durante la ejecucion de metodos
//	 * publicos de objetos tipo DataList. No se intercepta constructores ni
//	 * metodos que retornen Entities que sean subclases de {@link Entidad}.
//	 * 
//	 * @see #handleException(RootBean, Throwable)
//	 * @param pjp
//	 *            {@link ProceedingJoinPoint}
//	 * @param dataTable
//	 *            {@link DataList}
//	 * @return Object
//	 */
//	@Around("anyPublicMethodExecution() && this(dataList) && !cflow(adviceexecution())")
//	public Object interceptUnhandledExceptionsOnDataListMethod(
//			ProceedingJoinPoint pjp, DataTable<?> dataTable) {
//		try {
//			return pjp.proceed();
//		} catch (Throwable t) {
//			handleException(dataTable.getBean(), t);
//			return null;
//		}
//	}


	/**
	 * Encapsula una excepcion en un mensaje de error inesperado en un bean que
	 * sea subclase de {@link RootBean}.
	 * 
	 * @param bean
	 *            {@link RootBean}
	 * @param t
	 *            Throwable
	 * @return Throwable
	 */
	static Throwable handleException(RootBean<?> bean, Throwable t) {
		String message = null;
		RootException.StackTraceLevel stackTraceLevel = RootException.StackTraceLevel.FULL;
		if (t instanceof SecurityAspectException) {
			message = t.getMessage();
		} else {
			String errorKey = null;
			if (t instanceof CrudException || t instanceof FinderException) { // si es CRUD o FINDER, usar causa
				t = t.getCause();
			}
			if (t instanceof DataAccessException) { // si es error de acceso a BD, verificar causa
				if (t instanceof DataIntegrityViolationException) { // restriccion
					errorKey = "ExceptionAspect.RestriccionViolada";
				} else if (t instanceof InvalidDataAccessResourceUsageException) { // error SQL
					errorKey = "ExceptionAspect.ErrorAccesoDatos";
				} else {
					errorKey = "ExceptionAspect.ErrorAccesoDatos";
				}
				t = ((DataAccessException) t).getCause();
			}
			if (t instanceof ConstraintViolationException) { // constraint
				stackTraceLevel = RootException.StackTraceLevel.BRIEF;
				errorKey = "ExceptionAspect.RestriccionViolada";
				String constraintName = ((ConstraintViolationException) t)
						.getConstraintName();
				if (constraintName != null) {
					message = MessageUtils.getLabel(constraintName);
					if (!message.contains(constraintName)) {
						stackTraceLevel = RootException.StackTraceLevel.NONE;
					}
				} else {
					Throwable r = ((ConstraintViolationException) t).getCause();
					message = r.getMessage();
					if (message != null) {
						constraintName = StringUtils.substringBetween(message, "(", ")");
						if (constraintName != null) {
							message = MessageUtils.getLabel(constraintName);
							if (!message.contains(constraintName)) {
								stackTraceLevel = RootException.StackTraceLevel.NONE;
							}
						} else {
							message = r.getMessage();
						}
					}
				}
			} else if (t instanceof PropertyValueException) { // null or
																// transient
																// value
				message = MessageUtils.getLabel(t.getClass().getName()
						,StringUtils.substringAfterLast(((PropertyValueException) t).getEntityName(),".")
						,((PropertyValueException) t).getPropertyName());
				
			} else if (t instanceof LazyInitializationException) { 
				message = MessageUtils.getLabel(t.getClass().getName());
			} else if (t instanceof SQLGrammarException) { // SQL
				message = MessageUtils.getLabel(t.getClass().getName());
			} else {
				// mensaje de la excepcion
				message = t.getLocalizedMessage();
				if (message == null) {
					// mensaje en base a la clase de la excepcion
					message = MessageUtils.getLabel(t.getClass().getName());
				}
			}
			if (errorKey == null) {
				errorKey = (t instanceof RootException ? "ExceptionAspect.Error"
						: "ExceptionAspect.UnexpectedError");
			}
			message = MessageUtils.getLabel(errorKey, message);
		}
		ExceptionAspectHandlerException e = new ExceptionAspectHandlerException(
				message);
		e.initCause(t);
		e.setStackTrace(stackTraceLevel);
		String key = StringUtils.EMPTY;
		if (t instanceof RootException) {
			key = ((RootException) t).getKey();
			if (StringUtils.isNotEmpty(key)) {
				key = "(" + key + ") ";
			}
			if (((RootException) t).isFatal()) {
				e.fatal();
			}
		}
		bean.putError(e);
		
		System.out.println(DateUtils.getFormattedTimestamp()
				+ " [" + bean.getSessionBean() + " "
				+ bean.getClass().getSimpleName() + "] "
				+ ">>> EXCEPTION HANDLED: " + key + e.getLocalizedMessage());
		return e;
	}

}