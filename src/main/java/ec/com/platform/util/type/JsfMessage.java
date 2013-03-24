package ec.com.platform.util.type;

import javax.faces.application.FacesMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface JsfMessage {

    public void wrapErrorMessage(String summary);

    public void wrapFatalMessage(String summary);

    public void wrapMessage(FacesMessage.Severity severity, String summary, String... args);

    public void wrapSuccessMessage(String summary);

    public void wrapWarningMessage(String summary);
    
}
