package ec.com.ebos.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Anotacion para indicar que un metodo de una implementacion de capa S o G
 * corresponde a un servicio que debe verificarse mediante permisos de seguridad.
 * Debe indicarse el nombre del servicio en el valor de esta anotacion,
 * el cual debe corresponder al codigo del servicio definido en {@link Servicio#getCodigo()}.
 * @see SecurityAspectGImpl#interceptCheckServiceMethodOnSLayer(ProceedingJoinPoint, RootBean, CheckService)
 * @see SecurityAspectGImpl#interceptCheckServiceMethodOnGLayer(ProceedingJoinPoint, RootBean, CheckService)
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckService {

	public static final String NO_SERVICE = "<NO_SERVICE>";
	
    /**
     * Nombre del servicio, que debe corresponder al {@link Servicio#getCodigo()}.
     */
    String value();
}