package ec.com.platform.util.type;

/** 
 * @author Eduardo Plua Alay
 */
public interface EnhancedUserType extends UserType {
	/**
	 * Return an SQL literal representation of the value
	 */
	public String objectToSQLString(Object value);
	
	/**
	 * Return a string representation of this value, as it
	 * should appear in an XML document
	 */
	public String toXMLString(Object value);
	/**
	 * Parse a string representation of this value, as it
	 * appears in an XML document
	 */
	public Object fromXMLString(String xmlValue);
}
