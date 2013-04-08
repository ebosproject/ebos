package ec.com.ebos.util;

/**
 * Utilidades para trabajar con cadenas
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class StringUtils {
   
    public static String empty = "";       
    
    public static String[] split(String value, String regex){
    	return value.split(regex);
    }

}
