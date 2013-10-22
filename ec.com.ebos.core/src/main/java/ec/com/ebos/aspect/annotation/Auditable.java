package ec.com.ebos.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ec.com.ebos.root.model.Entidad;

/**
 * Anotacion para indicar que una {@link Entidad} es sujeto de auditoria por modificaciones y eliminaciones
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 *
 * @since 2013/08/29
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    
}