package ec.com.platform.util;

/**
 * Utilidades para trabajar con numeros
 * @author Eduardo Plua Alay
 */
public class NumberUtils {
    
    /**
     * Valida si un String puede ser convertido en Long
     * @param number String a validar
     * @return true si se puede convertir String
     */
    public static boolean tryParseLong(String number){
        try{
            parseLong(number);
            return true;
        } catch(NumberFormatException ex){
            return false;
        }                
    }

    public static Long parseLong(String number) {
        return Long.parseLong(number);
    }

}
