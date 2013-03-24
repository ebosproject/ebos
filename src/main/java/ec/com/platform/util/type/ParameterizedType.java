package ec.com.platform.util.type;

import java.util.Properties;

/**
 * @author Eduardo Plua Alay
 */
public interface ParameterizedType {

	/**
	 * Gets called by Hibernate to pass the configured type parameters to
	 * the implementation.
	 */
	public void setParameterValues(Properties parameters);
}