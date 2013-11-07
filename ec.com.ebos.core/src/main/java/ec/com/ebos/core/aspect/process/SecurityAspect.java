package ec.com.ebos.core.aspect.process;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.EbosContext;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.master.session.SessionBean;
import ec.com.ebos.core.security.model.Usuario;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.HTTPUtils;

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
		
		ExternalContext context = EbosContext.getExternalContext();
		
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