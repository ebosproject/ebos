package ec.com.ebos.aspect.core.process;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.LazyInitializationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import ec.com.ebos.aspect.core.exception.ExceptionAspectHandlerException;
import ec.com.ebos.aspect.core.exception.SecurityAspectException;
import ec.com.ebos.context.EbosContext;
import ec.com.ebos.orm.crud.CrudException;
import ec.com.ebos.orm.crud.FinderException;
import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.root.web.jsf.bean.RootBean;
import ec.com.ebos.security.core.service.SecurityS;
import ec.com.ebos.util.HTTPUtils;
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
public class ExceptionAspect {
	
	@Getter @Setter
    @Autowired
    @Qualifier(SecurityS.BEAN_NAME)
    private SecurityS securityS;

	
	private static final Logger logger = Logger.getLogger(ExceptionAspect.class);

	

	@Pointcut("execution(* ec.com.ebos.*.core.service.*S.*(..))")
	private void eBosServiceLayerMethods() {}
	
	@Pointcut("execution(* ec.com.ebos.admin.core.service.AdministracionS.getMessageResource(..))")
	private void messageResourceMethod() {}
	
	@Around("eBosServiceLayerMethods() && !messageResourceMethod()")
	public Object exceptionInterceptor(ProceedingJoinPoint pjp) {
		boolean error = false; 
		try{
			return pjp.proceed();
		} catch(Throwable t){
			handleException(t,securityS);
			error = true;
			return null;
		} finally {
			EbosContext.addCallbackParam("error", error);
		}
		
	}
	
	
	/**
	 * Encapsula una excepcion en un mensaje de error para los {@link #eBosServiceLayerMethods()}
	 * 
	 * @param securityS {@link SecurityS}
	 * @param t Throwable
	 * @return Throwable
	 */
	static Throwable handleException(Throwable t, SecurityS securityS) {
		String message = null, errorKey = null;
		
		RootException.StackTraceLevel stackTraceLevel = RootException.StackTraceLevel.FULL;
		if (t instanceof SecurityAspectException) {
			message = t.getMessage();
		} else {
			
			if (t instanceof CrudException || t instanceof FinderException) { // si es CRUD o FINDER, usar causa
				t = t.getCause();
			}
			if (t instanceof DataAccessException) { // si es error de acceso a BD, verificar causa
				if (t instanceof DataIntegrityViolationException) { // restriccion
					errorKey = "ec.com.ebos.aspect.DataIntegrityViolationException";
				} else if (t instanceof InvalidDataAccessResourceUsageException) { // error SQL
					errorKey = "ec.com.ebos.aspect.InvalidDataAccessResourceUsageException";
				} else {
					errorKey = "ec.com.ebos.aspect.DataAccessException";
				}
				t = ((DataAccessException) t).getCause();
			}
			if (t instanceof ConstraintViolationException) { // constraint
				stackTraceLevel = RootException.StackTraceLevel.BRIEF;
				errorKey = "ec.com.ebos.aspect.ConstraintViolationException";
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
			} else if (t instanceof PropertyValueException) { // null or transient value
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
				errorKey = (t instanceof RootException ? "ec.com.ebos.aspect.RootException"
						: "ec.com.ebos.aspect.UnexpectedException");
			}
			//message = MessageUtils.getLabel(errorKey, message);
		}
		ExceptionAspectHandlerException e = new ExceptionAspectHandlerException(message, errorKey);
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
		
		securityS.putError(e);
		
		logger.error("(" + HTTPUtils.getRemoteAddr(((HttpServletRequest) EbosContext.webContext().getRequest())) 
				+ ") [" + securityS.getSessionBean() + " "
				+ securityS.getSessionBean().getClass().getSimpleName() + "] --> EXCEPTION HANDLED: " + e.getKey() + ": " + e.getLocalizedMessage());
		
		return e;
	}

}