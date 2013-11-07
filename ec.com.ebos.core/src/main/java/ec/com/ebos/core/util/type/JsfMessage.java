package ec.com.ebos.core.util.type;


/**
 *
 * @author Eduardo Plua Alay
 */
public interface JsfMessage {

    public void putError(String key, Object... args);

    public void putFatal(String key, Object... args);

    public void putSuccess(String key, Object... args);

    public void putWarning(String key, Object... args);
    
}
