package ec.com.ebos.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 * Anotacion para indicar que un metodo de un
 * bean que sea subclase de {@link RootBean} 
 * corresponde a un servicio que debe inicializarse y ser
 * controlado mediante permisos de seguridad.
 * Debe indicarse el nombre del servicio en el valor de esta anotacion,
 * el cual debe corresponder al codigo del servicio definido en {@link Servicio#getCodigo()}.
 * @see SecurityAspectGImpl#interceptInitServiceMethodFromMB(GenericOMB, InitService)
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitService {

    /**
     * Nombre del servicio, que debe corresponder al {@link Servicio#getCodigo()}.
     */
    String value();
}