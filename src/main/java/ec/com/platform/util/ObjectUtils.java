package ec.com.platform.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para trabajar sobre objetos
 * @update Eduardo Plua Alay
 */
public class ObjectUtils {
	
	private static final String DEFAULT_GETTER = "getDescripcion";

	/**
	 * Compara dos objetos en base a su propio criterio, pero retorna solamente -1, 0, +1
	 * @param <T>
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static <T extends Comparable<T>> int simpleCompare(T o1, T o2) {
		int result = o1.compareTo(o2);
		return result != 0 ? result < 0 ? -1 : 1 : 0;
	}

	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia los campos indicados del objeto original.
	 * @param <T> Tipo del objeto
	 * @param obj objeto original
	 * @param fields String...
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T buildInstanceWithFields(T obj, String... fields) {
		T newDto = null;
		try {
			newDto = ((Class<T>) obj.getClass()).newInstance();
			for (String field : fields) {
				Field f = obj.getClass().getDeclaredField(field);
				f.setAccessible(true);
				f.set(newDto, f.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia los campos indicados del objeto original.
	 * @param <D> Tipo del objeto destino
	 * @param <T> Tipo del objeto original
	 * @param clazz clase del objeto destino
	 * @param obj objeto original
	 * @param fields String...
	 * @return D
	 */
	public static <D, T> D convertWithFields(Class<D> clazz, T obj, String... fields) {
		D newDto = null;
		try {
			newDto = clazz.newInstance();
			for (String field : fields) {
				Field oField = obj.getClass().getDeclaredField(field);
				Field dField = clazz.getDeclaredField(field);
				oField.setAccessible(true);
				dField.setAccessible(true);
				dField.set(newDto, oField.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia todos los campos posibles del objeto original
	 * (si se llaman igual y si son de tipos compatibles),
	 * exceptuando los indicados (opcional).
	 * @param <D> Tipo del objeto destino
	 * @param <T> Tipo del objeto original
	 * @param clazz clase del objeto destino
	 * @param obj objeto original
	 * @param excludedFields String... Campos a excluir
	 * @return D
	 */
	public static <D, T> D convertWithAllFields(Class<D> clazz, T obj, String... excludedFields) {
		D newDto = null;
		List<String> excludedFieldNames = Arrays.asList(excludedFields);
		try {
			newDto = clazz.newInstance();
			List<Field> oFields = getAllFields(obj.getClass());
			for (Field oField : oFields) {
				if (excludedFieldNames.contains(oField.getName())) // no copiar campos excluidos
					continue;
				if (Modifier.isStatic(oField.getModifiers())) // no copiar campos estaticos
					continue;
				try {
					Field dField = getField(clazz, oField.getName());
					if (Modifier.isStatic(dField.getModifiers())) // no copiar campos estaticos
						continue;
					if (!dField.getType().isAssignableFrom(oField.getType())) // no copiar tipos no compatibles
						continue;
					oField.setAccessible(true);
					dField.setAccessible(true);
					dField.set(newDto, oField.get(obj));
				} catch (NoSuchFieldException e) {
					continue;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
	 * Return the set of fields declared at all level of class hierarchy
	 * @see http://stackoverflow.com/questions/3567372/access-to-private-inherited-fields-via-reflection-in-java
	 */
	private static List<Field> getAllFields(Class<?> clazz) {
		return getAllFieldsRec(clazz, new ArrayList<Field>());
	}

	private static List<Field> getAllFieldsRec(Class<?> clazz, List<Field> list) {
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			getAllFieldsRec(superClazz, list);
		}
		list.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return list;
	}
	
	/**
	 * Returns a Field object that reflects the specified declared field of the class or interface represented by the Class object provided
	 * or its superclasses or superinterfaces.
	 * @param clazz
	 * @param name String that specifies the simple name of the desired field
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				return getField(superClazz, name);
			}
		}
        throw new NoSuchFieldException(name);
	}
	
	/**
	 * Returns a Method object that reflects the getter for the specified declared field (with given fieldName)
	 * of the class or interface represented by the Class object provided
	 * or its superclasses or superinterfaces.
	 * @param clazz
	 * @param fieldName String that specifies the simple name of the desired field
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getGetter(Class<?> clazz, String fieldName) throws NoSuchMethodException {
		try {
			return clazz.getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
		} catch (Exception e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				return getGetter(superClazz, fieldName);
			}
		}
        throw new NoSuchMethodException(fieldName);
	}

	/**
	 * Returns a Method object that reflects the setter for the specified declared field (with given fieldName and fieldClass)
	 * of the class or interface represented by the Class object provided
	 * or its superclasses or superinterfaces.
	 * @param clazz
	 * @param fieldName String that specifies the simple name of the desired field
	 * @param fieldClass Class of the desired field
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getSetter(Class<?> clazz, String fieldName, Class<?> fieldClass) throws NoSuchMethodException {
		try {
			return clazz.getDeclaredMethod("set" + StringUtils.capitalize(fieldName), fieldClass);
		} catch (Exception e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				return getSetter(superClazz, fieldName, fieldClass);
			}
		}
        throw new NoSuchMethodException(fieldName);
	}

	/**
	 * Obtiene un campo de un objeto
	 * @param object objeto
	 * @param field nombre del campo
	 * @return
	 * @since 2010-12-02
	 */
	private static Object getField(Object object, String field) {
		try {
			// Intentar retornar el campo
			return object.getClass().getDeclaredField(field).get(object);
		} catch (Exception e1) {
			try {
				// Intentar retornar el resultado del metodo getter del campo
				String getCriteria = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
				return object.getClass().getDeclaredMethod(getCriteria).invoke(object);
			} catch (Exception e2) {
				try {
					// Intentar retornar el resultado del metodo
					return object.getClass().getDeclaredMethod(field).invoke(object);
				} catch (Exception e3) {
					try {
						// Intentar obtener la clave, asumiendo que es un Map
						return ((Map<?,?>)object).get(field);
					} catch (Exception e4) {
						try {
							// Intentar obtener el campo correspondiente al metodo generico
							return object.getClass().getDeclaredMethod(DEFAULT_GETTER).invoke(object);
						} catch (Exception e5) {
							// Retornar el objeto
							return object;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Compara dos objetos en base a cualquier criterio
	 * @param o1
	 * @param o2
	 * @param criteria Criterio de comparacion
	 * @param asc <code>true</code> si se quiere comparar en orden ascendente
	 * @return
	 * @since 2010-03-29
	 */
	private static int extendedCompare(Object o1, Object o2, String criteria, boolean asc) {
//		try {
//			// Intentar comparar sobre el campo correspondiente al criterio dado
//			return (asc ? 1 : -1) *
//					o1.getClass().getDeclaredField(criteria).get(o1).toString().compareTo(
//					o2.getClass().getDeclaredField(criteria).get(o2).toString());
//		} catch (Exception e1) {
//			try {
//				// Intentar comparar sobre el metodo getter del campo correspondiente al criterio dado
//				String getCriteria = "get" + criteria.substring(0, 1).toUpperCase() + criteria.substring(1);
//				return (asc ? 1 : -1) *
//						o1.getClass().getDeclaredMethod(getCriteria).invoke(o1).toString().compareTo(
//						o2.getClass().getDeclaredMethod(getCriteria).invoke(o2).toString());
//			} catch (Exception e2) {
//				try {
//					// Intentar comparar sobre el metodo correspondiente al criterio dado
//					return (asc ? 1 : -1) *
//							o1.getClass().getDeclaredMethod(criteria).invoke(o1).toString().compareTo(
//							o2.getClass().getDeclaredMethod(criteria).invoke(o2).toString());
//				} catch (Exception e3) {
//					try {
//						// Intentar comparar sobre claves, asumiendo que es un Map
//						return (asc ? 1 : -1) *
//								((Map<?,?>)o1).get(criteria).toString().compareTo(
//										((Map<?,?>)o2).get(criteria).toString());
//					} catch (Exception e4) {
//						try {
//							// Intentar comparar sobre el metodo generico
//							return (asc ? 1 : -1) *
//									o1.getClass().getDeclaredMethod(DEFAULT_GETTER).invoke(o1).toString().compareTo(
//									o2.getClass().getDeclaredMethod(DEFAULT_GETTER).invoke(o2).toString());
//						} catch (Exception e5) {
//							// Intentar comparar sobre el valor toString de los objetos
//							return (asc ? 1 : -1) *
//									o1.toString().compareTo(o2.toString());
//						}
//					}
//				}
//			}
//		}
		return (asc ? 1 : -1) * getField(o1, criteria).toString().compareTo(getField(o2, criteria).toString());
	}
	
	/**
	 * Construye un {@link Comparator} para cualquier pareja de Object
	 * @param criteria Criterio de comparacion
	 * @param asc <code>true</code> si se quiere comparar en orden ascendente
	 * @return
	 * @since 2009-06-09, 2010-08-12
	 */
	public static Comparator<Object> getComparator(String criteria, boolean asc) {
		return new CriteriaComparator(criteria, asc);
	}
	private static class CriteriaComparator implements Comparator<Object>, Serializable {

		private static final long serialVersionUID = 1L;
		private String criteria;
		private boolean asc;

		public CriteriaComparator(String criteria, boolean asc) {
			this.criteria = criteria;
			this.asc = asc;
		}

		public int compare(Object o1, Object o2) {
			return extendedCompare(o1, o2, this.criteria, this.asc);
		}
		
	}
	
	/**
	 * Construye un {@link Comparator} para los values apuntados por la pareja de keys en el map.
	 * @param map Mapa base para la comparacion
	 * @param criteria Criterio de comparacion
	 * @param asc <code>true</code> si se quiere comparar en orden ascendente
	 * @return
	 * @since 2010-03-29, 2010-08-12
	 */
	public static Comparator<Object> getMappedValueComparator(Map<?, ?> map, String criteria, boolean asc) {
		return new MappedValueComparator(map, criteria, asc);
	}
	private static class MappedValueComparator extends CriteriaComparator {

		private static final long serialVersionUID = 1L;
		private Map<?,?> map;

		public MappedValueComparator(Map<?, ?> map, String criteria, boolean asc) {
			super(criteria, asc);
			this.map = map;
		}

		public int compare(Object k1, Object k2) {
			return super.compare(this.map.get(k1), this.map.get(k2));
		}
	}
	
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isAnyNull(Object... objects) {
		if (objects == null) {
			return true;
		}
		for (Object object : objects) {
			if (object == null)
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean areAllNotNull(Object... objects) {
		return !isAnyNull(objects);
	}

	/**
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isAnyNotNull(Object... objects) {
		if (objects == null) {
			return false;
		}
		for (Object object : objects) {
			if (object != null)
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean areAllNull(Object... objects) {
		return !isAnyNotNull(objects);
	}

	/**
	 * 
	 * @param string varargs array
	 * @return <code>true</code> if any of the given strings is null or empty,
	 * <code>false</code> otherwise, i.e. all given strings are not null and not empty
	 */
	public static boolean isAnyEmpty(String... string) {
		for (String str : string) {
			if (StringUtils.isEmpty(str))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param string varargs array
	 * @return <code>true</code> if any of the given strings is null or blank,
	 * <code>false</code> otherwise, i.e. all given strings are not null and not blank
	 */
	public static boolean isAnyBlank(String... string) {
		for (String str : string) {
			if (StringUtils.isBlank(str))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param string varargs array
	 * @return <code>true</code> if all given strings are not null and not blank,
	 * <code>false</code> otherwise, i.e. any of the given strings is null or blank
	 */
	public static boolean areAllNotBlank(String... string) {
		return !isAnyBlank(string);
	}

	/**
	 * @param col {@link Collection}
	 * @return true if col is null or empty.
	 */
	public static boolean isEmpty(Collection<?> col) {
		return (col == null || col.isEmpty());
	}
	
	/**
	 * @param array Object[]
	 * @return true if array is null or empty.
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}
	
	/**
	 * Devuelve el primer elemento de la coleccion,
	 * o null si la coleccion es nula o esta vacia
	 * @param <T> tipo de objetos de la coleccion
	 * @param col Collection&lt;T>
	 * @return T
	 * @see http://stackoverflow.com/questions/1671378/java-get-first-item-from-a-collection
	 * @see http://hanuska.blogspot.com/2006/09/first-element-in-list.html
	 * @author Luis Tama Wong
	 */
	public static <T> T getFirstOrNull(Collection<T> col) {
		if (isEmpty(col)) {
			return null;
		} else {
			if (List.class.isInstance(col)) {
				// aprovechar el metodo get(index)
				return ((List<T>)col).get(0);
			} else {
				// retornar primer elemento del arreglo (ya no, muy costoso)
				//return (T)col.toArray()[0];
				// retornar primer elemento del iterator
				return col.iterator().next();
			}
		}
	}

	/**
	 * Devuelve el primer elemento de la coleccion si es el unico en ella,
	 * o null si la coleccion es nula, esta vacia o tiene mas de un elemento
	 * @param <T> tipo de objetos de la coleccion
	 * @param col Collection&lt;T>
	 * @return T
	 * @see http://stackoverflow.com/questions/1671378/java-get-first-item-from-a-collection
	 * @see http://hanuska.blogspot.com/2006/09/first-element-in-list.html
	 * @author Luis Tama Wong
	 */
	public static <T> T getUniqueOrNull(Collection<T> col) {
		if (isEmpty(col) || col.size() != 1) {
			return null;
		} else {
			if (List.class.isInstance(col)) {
				// aprovechar el metodo get(index)
				return ((List<T>)col).get(0);
			} else {
				// retornar primer elemento del arreglo (ya no, muy costoso)
				//return (T)col.toArray()[0];
				// retornar primer elemento del iterator
				return col.iterator().next();
			}
		}
	}

	/**
	 * Based on EmailValidator from Hibernate and
	 * http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
	 * @since 2010-08-14
	 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	public static boolean isValidEmail(String email, boolean nullable) {
		if (email == null)
			return nullable;
		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}
	public static boolean isValidEmail(String email) {
		return isValidEmail(email, false);
	}
	
	
	
	/**
	 * String manupulation Utils
	 */
	
	private static final String PLAIN_ASCII = "AaEeIiOoUu" // grave
		+ "AaEeIiOoUuYy" // acute
		+ "AaEeIiOoUuYy" // circumflex
		+ "AaOoNn" // tilde
		+ "AaEeIiOoUuYy" // umlaut
		+ "Aa" // ring
		+ "Cc" // cedilla
	;
	
	private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
			+ "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
			+ "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
			+ "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
			+ "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
			+ "\u00C5\u00E5" + "\u00C7\u00E7"
	;

	// private constructor, can't be instanciated!
	private ObjectUtils() {
	}

	// remove accentued from a string and replace with ascii equivalent
	public static String convertNonAscii(String s) {
		if (s == null)
			return null;
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			int pos = UNICODE.indexOf(c);
			if (pos > -1) {
				sb.append(PLAIN_ASCII.charAt(pos));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]) {
		String s = "The result : �,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�";
		System.out.println(ObjectUtils.convertNonAscii(s));
		//System.out.println(ObjectUtils.convertNonAscii(null));
		// output :
		// The result : E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c
	}

}
