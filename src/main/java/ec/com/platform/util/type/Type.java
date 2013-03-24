package ec.com.platform.util.type;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * @author Eduardo Plua Alay
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface Type {
	String type();

	Parameter[] parameters() default {};
}