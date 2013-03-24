package ec.com.platform.app.web.resources;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import ec.com.platform.app.core.service.AppS;
import ec.com.platform.app.model.MessageResource;
import ec.com.platform.app.spring.utils.SpringUtils;


/**
 * Custom ResourceBundle para acceder a propiedades en la BD
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/15
 */
public class DatabaseDrivenResourceBundle extends ResourceBundle {
 
    private static final String PREFIX_NOT_FOUND = "???";
 
    private AppS appS;
 
    public DatabaseDrivenResourceBundle() {
    	appS = SpringUtils.getBean("appS", AppS.class);
    }
    
    @Override
    protected Object handleGetObject(String key) {
        final MessageResource messageResource = appS
                .obtenerMessageResourcePorCodeYLocale(key, FacesContext.getCurrentInstance()
                        .getViewRoot().getLocale().getLanguage());
        if (messageResource != null) {
            return messageResource.getValor();
        }
        return new StringBuilder(PREFIX_NOT_FOUND).append(key).append(PREFIX_NOT_FOUND).toString();
    }
 
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(appS
                .obtenerCodeMessageResourcePorLocale(FacesContext.getCurrentInstance().getViewRoot()
                        .getLocale().getLanguage()));
    }
 
}