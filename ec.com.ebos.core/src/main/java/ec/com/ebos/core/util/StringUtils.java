package ec.com.ebos.core.util;


/**
 * Utilidades para trabajar con cadenas
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class StringUtils {
   
	public static final String EMPTY = "";       
    
    public static String[] split(String str, String separatorChar){
    	return org.apache.commons.lang.StringUtils.split(str, separatorChar);
    }
    
    public static String concat(String... array){
    	return org.apache.commons.lang.StringUtils.join(array);
    }
    
    public static boolean isBlank(String str) {
    	return org.apache.commons.lang.StringUtils.isBlank(str);
    }

	public static String substringBetween(String str, String open, String close) {
		return org.apache.commons.lang.StringUtils.substringBetween(str, open, close);
	}

	public static Object substringAfterLast(String str, String separator) {
		return org.apache.commons.lang.StringUtils.substringAfterLast(str, separator);
	}

	public static boolean isNotEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isNotEmpty(str);
	}
	
	

}
