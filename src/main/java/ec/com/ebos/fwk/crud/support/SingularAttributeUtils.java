package ec.com.ebos.fwk.crud.support;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Utilidades para SingularAttribute
 * 
 * @author Eduardo Plua Alay
 */
public class SingularAttributeUtils {

	/**
	 * Obtiene el nombre de un SingularAttribute dado
	 * 
	 * @see http
	 *      ://stackoverflow.com/questions/7077464/how-to-get-singularattribute
	 *      -mapped-value-of-a-persistent-object
	 */
	public static <T, F> String getValue(SingularAttribute<T, F> field) {
		try {

			Member member = field.getJavaMember();
			if (member instanceof Field) {
				return ((Field) member).getName();
			} else {
				throw new IllegalArgumentException(
						"Unexpected java member type. Expecting field, found: "
								+ member);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}