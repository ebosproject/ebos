package ec.com.platform.util;

/**
 * Utilidades para trabajar con cadenas
 * @author Eduardo Plua Alay
 */
public class StringUtils {
   
    public static String empty = "";       
    
    public static String[] split(String value, String regex){
    	return value.split(regex);
    }

}
