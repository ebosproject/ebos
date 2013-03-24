package ec.com.platform.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para especificar una lista de indices unique para un DTO
 * @author Luis Tama Wong
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIndexes {

    /**
     * Indices
     */
    UniqueIndex[] value() default {};
    
}