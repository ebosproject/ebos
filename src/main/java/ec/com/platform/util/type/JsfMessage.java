package ec.com.platform.util.type;

import javax.faces.application.FacesMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface JsfMessage {

    public void wrapErrorMessage(String key, Object... args);

    public void wrapFatalMessage(String key, Object... args);

    public void wrapMessage(FacesMessage.Severity severity, String key, Object... args);

    public void wrapSuccessMessage(String key, Object... args);

    public void wrapWarningMessage(String key, Object... args);
    
}
