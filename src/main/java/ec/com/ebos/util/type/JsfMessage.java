package ec.com.ebos.util.type;

import javax.faces.application.FacesMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface JsfMessage {

    public void putError(String key, Object... args);

    public void putFatal(String key, Object... args);

    public void putMessage(FacesMessage.Severity severity, String key, Object... args);

    public void putSuccess(String key, Object... args);

    public void putWarning(String key, Object... args);
    
}
