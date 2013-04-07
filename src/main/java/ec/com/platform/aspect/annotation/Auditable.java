package ec.com.platform.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para indicar que un  es sujeto de auditoria por modificaciones y eliminaciones.
 * Debe indicarse el tipo de la clase  que manejara la auditoria,
 * la cual debe extender de {@link AbstractAuditEntity}.
 * @see AuditAspectGImpl
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

//    /**
//     * Tipo de la clase  que manejara la auditoria del  anotado.
//     * Debe extender de {@link AbstractAudit}.
//     */
//	
//    Class<? extends AbstractAudit<?, ?>> value();
    
}