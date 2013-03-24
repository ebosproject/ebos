package ec.com.platform.util.type;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * @author Eduardo Plua Alay
 */
@Target({})
@Retention(RUNTIME)
public @interface Parameter {
	String name();

	String value();
}
