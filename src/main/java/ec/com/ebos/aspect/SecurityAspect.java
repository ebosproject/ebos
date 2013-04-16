package ec.com.ebos.aspect;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ec.com.ebos.app.model.Persona;
import ec.com.ebos.app.web.jsf.mb.SessionMB;
import ec.com.ebos.seguridad.core.servicio.SeguridadS;
import ec.com.ebos.seguridad.model.Usuario;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.DateUtils;
import ec.com.ebos.util.HTTPUtils;

/**
 * Security aspects of the platform
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/13
 */
@Aspect
@Component
public class SecurityAspect {
	
	@Getter @Setter
    @Autowired
    @Qualifier("seguridadS")
    private SeguridadS seguridadS;

	/*@Before("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..))")
	public void logBefore(JoinPoint joinPoint) {

		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}*/

	/*@After("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..))")
	public void logAfter(JoinPoint joinPoint) {

		System.out.println("logAfter() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");

	}*/
	
	/*@AfterReturning(
			pointcut = "execution(* com.mkyong.customer.bo.CustomerBo.addCustomerReturnValue(..))",
			returning= "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {

		System.out.println("logAfterReturning() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("Method returned value is : " + result);
		System.out.println("******");

	}*/
	
	/*@AfterThrowing(
			pointcut = "execution(* com.mkyong.customer.bo.CustomerBo.addCustomerThrowException(..))",
			throwing= "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

		System.out.println("logAfterThrowing() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("Exception : " + error);
		System.out.println("******");

	}*/
	
//	@Around("execution(* ec.com.ebos.seguridad.core.gestor.SeguridadG.iniciarSesion(..))")
//	public void loginAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//		System.out.println("logAround() is running!");
//		System.out.println("hijacked method : " + joinPoint.getSignature().getName());
//		System.out.println("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
//		
//		System.out.println("Around before is running!");
//		joinPoint.proceed();
//		System.out.println("Around after is running!");
//		
//		System.out.println("******");
//
//	}
	
	@AfterReturning(pointcut="execution(* ec.com.ebos.seguridad.core.gestor.SeguridadG.iniciarSesion(..))",
			returning= "result")
	public void login(JoinPoint joinPoint, boolean result) throws Throwable {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
			Usuario usuario = ((SessionMB) joinPoint.getArgs()[0]).getUsuario();

			Persona persona = usuario.getEmpresaPersona().getPersona();
			System.out.println(DateUtils.getFormattedTimestamp()
					+ " (" + HTTPUtils.getRemoteAddr(((HttpServletRequest) context
					.getRequest())) + ")" + " --> LOGIN " + (result?"OK ":"FAILED ")
					+ usuario.getId() + "::" + persona.getNombres() + " "
					+ persona.getApellidos());
			
		} catch (Exception ex) {
		
			result = false;
			seguridadS.putError("sesion.error.app", ex.getMessage());
			System.err.println(ex);
		
		} finally {
			if (result) {
				Map<String, Object> sessionMap = context.getSessionMap();
				sessionMap.put(Constantes.SESSION_ATTR_CURRENTUSER, Boolean.TRUE);
			}
		}
	}

}