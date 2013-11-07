package ec.com.ebos.core.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Anotacion para indicar que un parametro de un metodo o el valor retornado por un metodo
 * corresponde a un  que debe verificarse mediante permisos de seguridad
 * a traves de un alias.
 * Debe indicarse el nombre del alias en el valor de esta anotacion,
 * el cual debe corresponder al alias obtenido del nombre de la clase
 * definido en {@link PermisosRaiz#getNombreClase()},
 * pero solamente a partir del caracter "@".
 * @see SecurityAspectGImpl#interceptSetterFrom(ProceedingJoinPoint, SecurityAspectGImpl.SecurityEnabled, SecurityAspectGImpl.SecurityEnabled)
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {

    /**
     * Nombre del alias, que es obtenido del nombre de la clase
     * definido en {@link PermisosRaiz#getNombreClase()},
     * pero solamente a partir del caracter "@".
     */
    String value();
    
    /**
     * Caracter delimitador entre el nombre de la clase y el alias.
     */
    public static final String DELIMITER = "@";
}