package ec.com.ebos.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ec.com.ebos.root.model.IEntidad;
import ec.com.ebos.util.type.StringValuedEnum;


/**
 * Utilidades para trabajar sobre objetos que implementan {@link IEntidad} y sus subinterfaces
 * @update <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class EntityUtils {

	/**
	 * Verifica que el entity dado sea persistente, es decir que no sea null y su id no sea null
	 * @param 
	 * @return true si la entity  es persistente, false en caso contrario
	 */
	public static <T extends IEntidad> boolean isPersistent(IEntidad entity) {
		return (entity != null && entity.getId() != null);
	}

	/**
	 * Verifica que todos los entity dados sean persistente, es decir que no sean null y sus id no sean null
	 * @param entities
	 * @return true si todos los  dados son persistentes, false en caso contrario
	 * @see #isPersistent(IEntidad)
	 */
	public static boolean areAllPersistent(IEntidad... entities) {
		if (ObjectUtils.isEmpty(entities)) {
			return false;
		}
		for (IEntidad entity : entities) {
			if (!isPersistent(entity)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifica que al menos uno de los entities dados sea persistente, es decir que no sea null y su id no sea null
	 * @param entity
	 * @return true si al menos uno de los entity dados es persistente, false en caso contrario
	 */
	public static boolean isAnyPersistent(IEntidad... entities) {
		if (ObjectUtils.isEmpty(entities)) {
			return false;
		}
		for (IEntidad entity: entities) {
			if (isPersistent(entity)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica que ninguno de los entity dados sea persistente, es decir que sean null o sus id sean null
	 * @param entities
	 * @return true si ninguno de los entity dados es persistente, false en caso contrario
	 * @see EntityUtils#isAnyPersistent(IEntidad...)
	 */
	public static boolean areAllNotPersistent(IEntidad... entities) {
		return !isAnyPersistent(entities);
	}

	/**
	 * Crea una nueva instancia del tipo del entity indicado.
	 * Copia el id del  original.
	 * @param <T>
	 * @param <T> Tipo del entity, extiende de {@link IEntidad}
	 * @param  entity original
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IEntidad> T buildInstanceWithId(IEntidad entity) {
		T newEntity = null;
		try {
			newEntity = ((Class<T>) entity.getClass()).newInstance();
			newEntity.setId(entity.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newEntity;
	}
	
	/**
	 * Crea una lista de ids en base a una lista de entities
	 * @param entityList
	 * @return
	 */
	public static <T extends IEntidad> List<Long> buildIdList(Collection<T> entityList) {
		List<Long> ids = new ArrayList<Long>();
		if (!ObjectUtils.isEmpty(entityList)) {
			for (T entity: entityList) {
				ids.add(entity.getId());
			}
		}
		return ids;
	}
	
	/**
	 * Crea una lista de ids en base a un arreglo de entities
	 * @param List
	 * @return
	 */
	public static <T extends IEntidad> List<Long> buildIdList(T... entityList) {
		List<Long> ids = new ArrayList<Long>();
		if (!ObjectUtils.isEmpty(entityList)) {
			for (T  entity: entityList) {
				ids.add(entity.getId());
			}
		}
		return ids;
	}
	
	/**
	 * Crea una lista de valores en base a un arreglo de Entity
	 * @param entityList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends IEntidad> List<Object> buildValueList(T... entityList) {
		List<Object> values = new ArrayList<Object>();
		if (!ObjectUtils.isEmpty(entityList)) {
			for (T  entity: entityList) {
				values.add(entity.getValue());
			}
		}
		return values;
	}
	
	/**
	 * Map para ser usado en f:selectItems, en base a constantes de un Enum que implemente
	 * {@link StringValuedEnum}. Las entradas cumplen con la regla:<br>
	 * (key=item's label , value=item's value)
	 * 
	 * @param enumType Class de un enum que implemente {@link StringValuedEnum}
	 * @since 2008-07-18
	 */
	public static <T extends Enum<T> & StringValuedEnum<T>> Map<String, T> buildLabeledEnumMap(
			Class<T> enumType) {
		return buildLabeledEnumMap(enumType.getEnumConstants());
	}
	
	/**
	 * Map para ser usado en f:selectItems, en base a constantes de un Enum que implemente
	 * {@link StringValuedEnum}. Las entradas cumplen con la regla:<br>
	 * (key=item's label , value=item's value)
	 * 
	 * @param enumConstants Una o mas constantes de un enum que implemente {@link StringValuedEnum}
	 * @since 2010-12-08
	 */
	public static <T extends Enum<T> & StringValuedEnum<T>> Map<String, T> buildLabeledEnumMap(T... enumConstants) {
		LinkedHashMap<String, T> map = new LinkedHashMap<String, T>();
		for (T enumConstant : enumConstants) {
			map.put(enumConstant.getLabel(), enumConstant);
		}
		return Collections.unmodifiableMap(map);
	}
	
	/**
	 * Filtra un mapa de objetos que implementan {@link IEntidad}, indexados por claves de tipo objeto,
	 * de modo que el label de cada elemento contenga el patron dado
	 * @param map Map&lt;Object, T>
	 * @param pattern String
	 * @param wildcard String
	 * @return 
	 */
	public static <T extends IEntidad> Map<Object, T> filterMapByContains(
			Map<Object, T> map, String pattern, String wildcard) {
		Map<Object, T> filtered = new HashMap<Object, T>();
		if (map != null) {
			for (Map.Entry<Object, T> entry : map.entrySet()) {
				if (wildcard.equals(pattern)) {
					filtered.put(entry.getKey(), entry.getValue());
				//} else if (ObjectUtils.convertNonAscii(entry.getValue().getLabel().toUpperCase()).contains( TODO : no se encuentra label property
                                } else if (ObjectUtils.convertNonAscii(entry.getValue().toString().toUpperCase()).contains(
						ObjectUtils.convertNonAscii(pattern.toUpperCase()))) {
					filtered.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return filtered;
	}
	
	/**
	 * Filtra una coleccion de objetos que implementan {@link IEntidad} de modo que el label de cada elemento contenga
	 * todos los tokens separados por espacios presentes en el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends IEntidad> List<T> filterCollectionByTokens(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		// Filter converted to ASCII, lower-cased, escaped, trimmed, parentheses escaped for the RegExp
		String filter = ObjectUtils.convertNonAscii(pattern.trim().toUpperCase()).replaceAll("\\s\\s*", " ").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		// RegExp that applies AND operation over all written words, separated by spaces
		String regexp = "^(?=.*" + filter.replaceAll("\\s", ")(?=.*") + ").*";
		List<T> filtered = new ArrayList<T>();
		for (T  entity: col) {
			String label = ObjectUtils.convertNonAscii(entity.getLabel().toString().toUpperCase());
			if (label.matches(regexp)) {
				filtered.add(entity);
			}
		}
		return filtered;
	}

	public static List<String> filterCollectionStringByTokens(List<String> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		// Filter converted to ASCII, lower-cased, escaped, trimmed, parentheses escaped for the RegExp
		String filter = ObjectUtils.convertNonAscii(pattern.trim().toUpperCase()).replaceAll("\\s\\s*", " ").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		// RegExp that applies AND operation over all written words, separated by spaces
		String regexp = "^(?=.*" + filter.replaceAll("\\s", ")(?=.*") + ").*";
		List<String> filtered = new ArrayList<String>();
		for (String  entity: col) {
			String label = ObjectUtils.convertNonAscii(entity.toUpperCase());
			if (label.matches(regexp)) {
				filtered.add(entity);
			}
		}
		return filtered;
	}
	/**
	 * Filtra una coleccion de objetos que implementan {@link IEntidad} de modo que el label de cada elemento contenga el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends IEntidad> List<T> filterCollectionByContains(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		List<T> filtered = new ArrayList<T>();
		for (T  entity: col) {
			String label = ObjectUtils.convertNonAscii(entity.getLabel().toString().toUpperCase());
			if (label.contains(ObjectUtils.convertNonAscii(pattern.toUpperCase()))) {
				filtered.add(entity);
			}
		}
		return filtered;
	}

	/**
	 * Filtra una coleccion de objetos que implementan {@link IEntidad} de modo que el label de cada elemento comience con el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends IEntidad> List<T> filterCollectionByStartsWith(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		List<T> filtered = new ArrayList<T>();
		for (T  entity: col) {
			String label = ObjectUtils.convertNonAscii(entity.getLabel().toString().toUpperCase());
			if (label.startsWith(ObjectUtils.convertNonAscii(pattern.toUpperCase()))) {
				filtered.add(entity);
			}
		}
		return filtered;
	}
	

	/**
	 * Convertidor JSF de Entity a String y viceversa.
	 * Es soportado por un mapa interno de IDs vs s, el cual se contruye a partir de una coleccion de s.
	 *  
	 * @param &lt;T extends {@link IEntidad}>
	 * @see http://balusc.blogspot.com/2007/09/objects-in-hselectonemenu.html
	 * @deprecated El mapa no funciona
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static class MappedConverter<T extends IEntidad> implements Converter, java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4244823765401748927L;

		private final HashMap<Long, T> map;

		/**
		 * Constructor de este Converter, que alimenta el mapa interno en base a una coleccion de objetos que extienen de IEntidad.
		 * @param col Collection&lt;T extends IEntidad>
		 * @deprecated El mapa no funciona
		 */
		@Deprecated
		public MappedConverter(final List<T> col) {
			map = new HashMap<Long, T>(col.size());
			for (T  entity: col) {
				map.put(entity.getId(), entity);
			}
		}
			
		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
			if (value == null) return "";
			if (value instanceof String) return (String) value;
			if (IEntidad.class.isInstance(value)) {
				IEntidad entity = (IEntidad) value;
				return entity.getId() == null ? "" : entity.getId().toString();
			}
			throw new IllegalArgumentException("This converter only handles instances of IEntidad");
		}
		
		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			IEntidad  entity = null;
			if (value != null) {
				try {
					Long id = new Long(value);
					entity = map.get(id);
				} catch (NumberFormatException nfe) {
					entity = null;
				}
				if (entity == null) {
					System.out.println("There is no entity with id: " + value);
					// throw new IllegalArgumentException("There is no entity with id:  " + id);
				}
			}
			return entity;
		}

	}

}
