package ec.com.ebos.core.util.type;


/**
 * Utility class designed to inspect StringValuedEnums.
 * @update Eduardo Plua Alay
 * @see http://community.jboss.org/wiki/Java5StringValuedEnumUserType
 */
public class StringValuedEnumReflect {
    
    /**
     * Don't let anyone instantiate this class.
     * @throws UnsupportedOperationException Always.
     */
    private StringValuedEnumReflect() {
        throw new UnsupportedOperationException("This class must not be instanciated.");
    }
    
    /**
     * All Enum constants (instances) declared in the specified class. 
     * @param enumClass Class to reflect
     * @return Array of all declared EnumConstants (instances).
     */
    private static <T extends Enum<T>> T[] 
            getValues(Class<T> enumClass){
        return enumClass.getEnumConstants();
    }
    
    /**
     * All possible string values of the string valued enum.
     * @param enumClass Class to reflect.
     * @return Available string values.
     */
    public static <T extends Enum<T> & StringValuedEnum<T>> String[] 
            getStringValues(Class<T> enumClass){ 
        T[] values = getValues(enumClass);
        String[] result = new String[values.length];
        for (int i=0; i<values.length; i++){
            result[i] = values[i].getValue();
        }
        return result;
    }
    
    /**
     * Name of the enum instance which hold the especified string value.
     * If value has duplicate enum instances than returns the first occurency.
     * @param enumClass Class to inspect.
     * @param value String.
     * @return name of the enum instance.
     */
    protected static <T extends Enum<T> & StringValuedEnum<T>> String 
            getNameFromValue(Class<T> enumClass, String value){
        T[] values = getValues(enumClass);
        for (T v : values){
            if (v.getValue().equals(value)){
                return v.name();
            }
        }
        return "";
    }

    /**
     * Enum instance which hold the especified string value.
     * @param enumClass Class to inspect.
     * @param value String.
     * @return enum instance
     */
	public static <T extends Enum<T> & StringValuedEnum<T>> T getEnumFromValue(
			Class<T> enumClass, String value) {
        T[] values = getValues(enumClass);
        for (T v : values){
            if (v.getValue().equals(value)){
                return v;
            }
        }
        return null;
	}

	/**
	 * Value of the especified enum instance.
	 * @param enumInstance enum instance
	 * @return value of the enum instance, null if enumConstant is null
	 */
	public static <T extends Enum<T> & StringValuedEnum<T>> String getValueFromEnum(
			T enumInstance) {
		return (enumInstance == null ? null : enumInstance.getValue());
	}

	/**
	 * Label key of the specified enum instance,
	 * based on parent class simple name,
	 * enum class simple name
	 * and enum name.
	 * @param enumInstance
	 * @return
	 */
	public static <T extends Enum<T> & StringValuedEnum<T>> String getLabelKeyFromEnum(
			T enumInstance) {
		Class<T> enumClass = enumInstance.getDeclaringClass();
		Class<?> parentClass = enumClass.getDeclaringClass();
		return ((parentClass != null ? parentClass.getSimpleName() : "") +
				"." + enumClass.getSimpleName()).toLowerCase() +
				"." + enumInstance.name();
	}

}
