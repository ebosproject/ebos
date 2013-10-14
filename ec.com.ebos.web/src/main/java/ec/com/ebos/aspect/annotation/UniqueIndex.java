package ec.com.ebos.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para un indice unique para un .
 * Debe indicarse el nombre del indice en el valor de esta anotacion,
 * y los nombres de los campos a validar.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIndex {

    /**
     * Nombre del indice
     */
    String indexName();
    
    /**
     * Nombres de los campos a validar
     */
    String[] fieldNames();
    
    /**
     * Nombre del campo identificador (default "id"),
     * para no validar un registro contra si mismo
     * si se trata de un update
     */
    String idFieldName() default "id";
}