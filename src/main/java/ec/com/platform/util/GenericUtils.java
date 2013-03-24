package ec.com.platform.util;


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

import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.StringValuedEnum;


/**
 * Utilidades para trabajar sobre objetos que implementan {@link Generic} y sus subinterfaces
 * @author Eduardo Plua Alay
 */
public class GenericUtils {

	/**
	 * Verifica que el dto dado sea persistente, es decir que no sea null y su id no sea null
	 * @param dto
	 * @return true si el dto es persistente, false en caso contrario
	 */
	public static <T extends Generic<T>> boolean isPersistent(Generic<T> dto) {
		return (dto != null && dto.getId() != null);
	}

	/**
	 * Verifica que todos los dto dados sean persistente, es decir que no sean null y sus id no sean null
	 * @param dtos
	 * @return true si todos los dto dados son persistentes, false en caso contrario
	 * @see #isPersistent(GenericEntity)
	 */
	public static boolean areAllPersistent(Generic<?>... dtos) {
		if (ObjectUtils.isEmpty(dtos)) {
			return false;
		}
		for (Generic<?> dto : dtos) {
			if (!isPersistent(dto)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifica que al menos uno de los dto dados sea persistente, es decir que no sea null y su id no sea null
	 * @param dtos
	 * @return true si al menos uno de los dto dados es persistente, false en caso contrario
	 */
	public static boolean isAnyPersistent(Generic<?>... dtos) {
		if (ObjectUtils.isEmpty(dtos)) {
			return false;
		}
		for (Generic<?> dto : dtos) {
			if (isPersistent(dto)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica que ninguno de los dto dados sea persistente, es decir que sean null o sus id sean null
	 * @param dtos
	 * @return true si ninguno de los dto dados es persistente, false en caso contrario
	 * @see GenericUtils#isAnyPersistent(GenericEntity...)
	 */
	public static boolean areAllNotPersistent(Generic<?>... dtos) {
		return !isAnyPersistent(dtos);
	}

	/**
	 * Crea una nueva instancia del tipo del DTO indicado.
	 * Copia el id del DTO original.
	 * @param <T> Tipo del DTO, extiende de {@link GenericEntity}
	 * @param dto DTO original
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Generic<T>> T buildInstanceWithId(Generic<T> dto) {
		T newDto = null;
		try {
			newDto = ((Class<T>) dto.getClass()).newInstance();
			newDto.setId(dto.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
	 * Crea una lista de ids en base a una lista de DTOs
	 * @param dtoList
	 * @return
	 */
	public static <T extends Generic<T>> List<Long> buildIdList(Collection<T> dtoList) {
		List<Long> ids = new ArrayList<Long>();
		if (!ObjectUtils.isEmpty(dtoList)) {
			for (T dto : dtoList) {
				ids.add(dto.getId());
			}
		}
		return ids;
	}
	
	/**
	 * Crea una lista de ids en base a un arreglo de DTOs
	 * @param dtoList
	 * @return
	 */
	public static <T extends Generic<T>> List<Long> buildIdList(T... dtoList) {
		List<Long> ids = new ArrayList<Long>();
		if (!ObjectUtils.isEmpty(dtoList)) {
			for (T dto : dtoList) {
				ids.add(dto.getId());
			}
		}
		return ids;
	}
	
	/**
	 * Crea una lista de valores en base a un arreglo de objetos Generic
	 * @param genericList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Generic> List<Object> buildValueList(T... genericList) {
		List<Object> values = new ArrayList<Object>();
		if (!ObjectUtils.isEmpty(genericList)) {
			for (T dto : genericList) {
				values.add(dto.getValue());
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
	 * Filtra un mapa de objetos que implementan {@link GenericEntity}, indexados por claves de tipo objeto,
	 * de modo que el label de cada elemento contenga el patron dado
	 * @param map Map&lt;Object, T>
	 * @param pattern String
	 * @param wildcard String
	 * @return 
	 */
	public static <T extends Generic<?>> Map<Object, T> filterMapByContains(
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
	 * Filtra una coleccion de objetos que implementan {@link Generic} de modo que el label de cada elemento contenga
	 * todos los tokens separados por espacios presentes en el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Generic> List<T> filterCollectionByTokens(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		// Filter converted to ASCII, lower-cased, escaped, trimmed, parentheses escaped for the RegExp
		String filter = ObjectUtils.convertNonAscii(pattern.trim().toUpperCase()).replaceAll("\\s\\s*", " ").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		// RegExp that applies AND operation over all written words, separated by spaces
		String regexp = "^(?=.*" + filter.replaceAll("\\s", ")(?=.*") + ").*";
		List<T> filtered = new ArrayList<T>();
		for (T dto : col) {
			String label = ObjectUtils.convertNonAscii(dto.getLabel().toString().toUpperCase());
			if (label.matches(regexp)) {
				filtered.add(dto);
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
		for (String dto : col) {
			String label = ObjectUtils.convertNonAscii(dto.toUpperCase());
			if (label.matches(regexp)) {
				filtered.add(dto);
			}
		}
		return filtered;
	}
	/**
	 * Filtra una coleccion de objetos que implementan {@link Generic} de modo que el label de cada elemento contenga el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Generic> List<T> filterCollectionByContains(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		List<T> filtered = new ArrayList<T>();
		for (T dto : col) {
			String label = ObjectUtils.convertNonAscii(dto.getLabel().toString().toUpperCase());
			if (label.contains(ObjectUtils.convertNonAscii(pattern.toUpperCase()))) {
				filtered.add(dto);
			}
		}
		return filtered;
	}

	/**
	 * Filtra una coleccion de objetos que implementan {@link Generic} de modo que el label de cada elemento comience con el patron dado
	 * @param col Collection&lt;T>
	 * @param pattern String
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Generic> List<T> filterCollectionByStartsWith(List<T> col, String pattern) {
		if (pattern == null || "".equals(pattern))
			return col;
		List<T> filtered = new ArrayList<T>();
		for (T dto : col) {
			String label = ObjectUtils.convertNonAscii(dto.getLabel().toString().toUpperCase());
			if (label.startsWith(ObjectUtils.convertNonAscii(pattern.toUpperCase()))) {
				filtered.add(dto);
			}
		}
		return filtered;
	}
	

	/**
	 * Convertidor JSF de GenericEntity a String y viceversa.
	 * Es soportado por un mapa interno de IDs vs DTOs, el cual se contruye a partir de una coleccion de DTOs.
	 *  
	 * @param &lt;T extends {@link GenericEntity}>
	 * @see http://balusc.blogspot.com/2007/09/objects-in-hselectonemenu.html
	 * @author Luis Tama Wong
	 * @deprecated El mapa no funciona
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static class MappedConverter<T extends Generic<?>> implements Converter, java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4244823765401748927L;

		private final HashMap<Long, T> map;

		/**
		 * Constructor de este Converter, que alimenta el mapa interno en base a una coleccion de objetos que extienen de GenericEntity.
		 * @param col Collection&lt;T extends GenericEntity>
		 * @deprecated El mapa no funciona
		 */
		@Deprecated
		public MappedConverter(final List<T> col) {
			map = new HashMap<Long, T>(col.size());
			for (T dto : col) {
				map.put(dto.getId(), dto);
			}
		}
			
		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
			if (value == null) return "";
			if (value instanceof String) return (String) value;
			if (Generic.class.isInstance(value)) {
				Generic<?> dto = (Generic<?>) value;
				return dto.getId() == null ? "" : dto.getId().toString();
			}
			throw new IllegalArgumentException("This converter only handles instances of GenericEntity");
		}
		
		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			Generic<?> dto = null;
			if (value != null) {
				try {
					Long id = new Long(value);
					dto = map.get(id);
				} catch (NumberFormatException nfe) {
					dto = null;
				}
				if (dto == null) {
					System.out.println("There is no entity with id: " + value);
					// throw new IllegalArgumentException("There is no entity with id:  " + id);
				}
			}
			return dto;
		}

	}

}
