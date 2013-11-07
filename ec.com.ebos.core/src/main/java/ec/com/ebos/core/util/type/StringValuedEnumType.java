package ec.com.ebos.core.util.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
 
/**
 * Tipo de dato para mapeo entre Strings SQL que representan constantes y Enums Java en archivo .hbm.xml de Hibernate.
 * Modo de uso con anotaciones JPA/Hibernate:
 * <pre>
 * &#64;Column(name="ESTADO", nullable = false)
 * &#64;Type(type = ec.com.ebos.util..Generic.Estado.TYPE)
 * private ec.com.ebos.util..Generic.Estado estado;</pre>
 * Codigo anterior en HBM:
 * <pre>
 * &lt;property name="valor">
 *  &lt;column name="VALOR" length="2" not-null="true" />
 *  &lt;type name="ec.com.ebos.util.type.StringValuedEnumType">
 *   &lt;param name="enumClassName">ec.com.ebos.corporativo..Clase$TipoDatoEnum&lt;/param>
 *  &lt;/type>
 * &lt;/property></pre>
 * Codigo anterior con String:
 * <pre>
 * &lt;property name="valor" type="string">
 *  &lt;column name="VALOR" length="2" not-null="true" />
 * &lt;/property></pre>
 * @update Eduardo Plua Alay
 * @see StringValuedEnum
 * @see ExampleEnum
 * @see http://community.jboss.org/wiki/Java5StringValuedEnumUserType
 */
@SuppressWarnings("unchecked")
public class StringValuedEnumType <T extends Enum<T> & StringValuedEnum<T>> 
        implements EnhancedUserType, ParameterizedType{
    
    /**
     * Enum class for this particular user type.
     */
    private Class<T> enumClass;
 
    /**
     * Value to use if null.
     */
    @Getter @Setter
    private String defaultValue;
    
    /** Creates a new instance of StringValuedEnumType */
    public StringValuedEnumType() {
    }
    
	public void setParameterValues(Properties parameters) {
		if (parameters == null) {
			parameters = new Properties();
		}
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null && StringValuedEnum.class.isAssignableFrom(enclosingClass)) {
			enumClass = (Class<T>) enclosingClass;
		} else {
			String enumClassName = parameters.getProperty("enumClassName");
			try {
				// Validates the class but does not eliminate the cast
				enumClass = (Class<T>) Class.forName(enumClassName).asSubclass(Enum.class).asSubclass(StringValuedEnum.class);
			} catch (ClassNotFoundException cnfe) {
                try {
                    throw new Exception("Enum class not found", cnfe);
                } catch (Exception ex) {
                    Logger.getLogger(StringValuedEnumType.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		}
		setDefaultValue(parameters.getProperty("defaultValue"));
	}
    
    /**
     * The class returned by <tt>nullSafeGet()</tt>.
     * @return Class
     */
    @SuppressWarnings("rawtypes")
	public Class returnedClass() {
        return enumClass;
    }
 
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }
    
    public boolean isMutable() {
        return false;
    }
 
    /**
     * Retrieve an instance of the mapped class from a JDBC resultset. Implementors
     * should handle possibility of null values.
     *
     * @param rs a JDBC result set
     * @param names the column names
     * @param owner the containing entity
     * @return Object
     * @throws SQLException
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws SQLException {
        String value = rs.getString( names[0] );
        if (value==null) {
            value = getDefaultValue();
            if (value==null){ //no default value
                return null;
            } 
        }
        String name = StringValuedEnumReflect.getNameFromValue(enumClass, value);
        Object res = rs.wasNull() ? null : Enum.valueOf(enumClass, name);
        
        return res;
    }
 
    /**
     * Write an instance of the mapped class to a prepared statement. Implementors
     * should handle possibility of null values. A multi-column type should be written
     * to parameters starting from <tt>index</tt>.
     *
     * @param st a JDBC prepared statement
     * @param value the object to write
     * @param index statement parameter index
     * @throws SQLException
     */   
    public void nullSafeSet(PreparedStatement st, Object value, int index)
    throws SQLException {
        if (value==null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString( index, ((T) value).getValue() );
        }
    }
    
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }
    
    public Serializable disassemble(Object value) {
        return (Enum<T>) value;
    }
        
    public Object deepCopy(Object value) {
        return value;
    }
 
    public boolean equals(Object x, Object y) {
        return x==y;
    }
    
    public int hashCode(Object x) {
        return x.hashCode();
    }
 
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }
 
    public String objectToSQLString(Object value) {
        return '\'' + ((T) value).getValue() + '\'';
    }
    
    public String toXMLString(Object value) {
        return ((T) value).getValue();
    }
 
    public Object fromXMLString(String xmlValue) {
        String name = StringValuedEnumReflect.getNameFromValue(enumClass, xmlValue);
        return Enum.valueOf(enumClass, name);
    }
        
}
