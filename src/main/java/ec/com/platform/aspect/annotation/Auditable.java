package ec.com.platform.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para indicar que un DTO es sujeto de auditoria por modificaciones y eliminaciones.
 * Debe indicarse el tipo de la clase DTO que manejara la auditoria,
 * la cual debe extender de {@link AbstractAuditDTO}.
 * @see AuditAspectGImpl
 * @author Luis Tama Wong
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

//    /**
//     * Tipo de la clase DTO que manejara la auditoria del DTO anotado.
//     * Debe extender de {@link AbstractAuditDTO}.
//     */
//	
//    Class<? extends AbstractAuditDTO<?, ?>> value();
    
}