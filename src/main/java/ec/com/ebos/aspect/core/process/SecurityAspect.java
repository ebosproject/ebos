package ec.com.ebos.aspect.core.process;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ec.com.ebos.context.EbosContext;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.web.jsf.bean.SessionBean;
//import ec.com.ebos.security.core.service.SecurityS;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.HTTPUtils;
//import lombok.Getter;
//import lombok.Setter;
import org.apache.log4j.Logger;

/**
 * Security aspects
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/13
 */
@Aspect
@Component
public class SecurityAspect {
	
	private static final Logger logger = Logger.getLogger(SecurityAspect.class);
	
//	@Getter @Setter
//    @Autowired
//    @Qualifier(SecurityS.BEAN_NAME)
//    private SecurityS securityS;

	
	@Pointcut("execution(* ec.com.ebos.security.core.service.SecurityS.authLogin(..)) && args(sessionBean)")
	private void authLogin(SessionBean sessionBean){} 
	
	@AfterReturning(value="authLogin(sessionBean)", returning="returnValue")
	public void logAuthLogin(JoinPoint joinPoint, boolean returnValue, SessionBean sessionBean) throws Throwable {
		
		ExternalContext context = EbosContext.webContext();
		
		try {
			Usuario usuario = sessionBean.getUsuario();

			Persona persona = usuario.getEmpresaPersona().getPersona();
			logger.info("(" + HTTPUtils.getRemoteAddr(((HttpServletRequest) context.getRequest())) 
					+ ") [" + sessionBean + " "
					+ sessionBean.getClass().getSimpleName() + "] --> " 
					+ (returnValue?"LOGGED":"FAILED")
					+ " ID: "+ usuario.getId() + ", USER: " + persona.getFullName());
			
		} catch (Exception ex) {
		
			returnValue = false;
			sessionBean.putError("(" + HTTPUtils.getRemoteAddr(((HttpServletRequest) context.getRequest())) 
					+ ") [" + sessionBean + " "
					+ sessionBean.getClass().getSimpleName() + "] --> "+ex.getLocalizedMessage());
		
		} finally {
			if (returnValue) {
				Map<String, Object> sessionMap = context.getSessionMap();
				sessionMap.put(Constantes.SESSION_ATTR_CURRENTUSER, Boolean.TRUE);
			}
		}
	}

}