package ec.com.platform.aspect;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import ec.com.platform.app.model.Persona;
import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.seguridad.model.Usuario;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.DateUtils;
import ec.com.platform.util.HTTPUtils;

/**
 * Aspectos de Securidad
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/13
 */
@Aspect
@Component
public class SecurityAspect {

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
	
//	@Around("execution(* ec.com.platform.seguridad.core.gestor.SeguridadG.iniciarSesion(..))")
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
	
	@AfterReturning(pointcut="execution(* ec.com.platform.seguridad.core.gestor.SeguridadG.iniciarSesion(..))",
			returning= "result")
	public boolean login(JoinPoint joinPoint, boolean result) throws Throwable {
		try {
			Usuario usuario = ((SesionUsuarioMB) joinPoint.getArgs()[0]).getUsuario();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			String STATE = "FAILED ";
			
			if (result) {
				Map<String, Object> sessionMap = context.getSessionMap();
				sessionMap.put(Constantes.LOGGED_USER_ATTR, Boolean.TRUE);
				STATE = "OK ";
			}

			Persona persona = usuario.getEmpresaPersona().getPersona();
			System.out.println(DateUtils.getFormattedTimestamp()
					+ " ["
					+ HTTPUtils.getRemoteAddr(((HttpServletRequest) context
							.getRequest())) + "]" + " >>> LOGIN " + STATE
					+ usuario.getId() + "::" + persona.getNombres() + " "
					+ persona.getApellidos());
			
			return true;
		} catch (Exception ex) {
			System.out.println("No se puede conectar con servidor, pongase en contacto con el administrador del sistema"
					+ ex.getMessage());
			return false;
		}
	}

}