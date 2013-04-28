package ec.com.ebos.master.web.resources;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import ec.com.ebos.master.core.service.MasterS;
import ec.com.ebos.master.model.Bundle;
import ec.com.ebos.master.spring.utils.SpringUtils;
import ec.com.ebos.util.Constantes;


/**
 * Custom ResourceBundle para acceder a propiedades en la BD
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/15
 */
public class DatabaseDrivenResourceBundle extends ResourceBundle {
 
    private MasterS masterS;
 
    public DatabaseDrivenResourceBundle() {
    	masterS = SpringUtils.getBean("masterS", MasterS.class);
    }
    
    @Override
    protected Object handleGetObject(String key) {
        final Bundle messageResource = masterS
                .getMessageResource(key, Bundle.Localidad.valueOf(FacesContext.getCurrentInstance()
                        .getViewRoot().getLocale().toString()));        
        
        if (messageResource != null) {
            return messageResource.getValor();
        }
        return new StringBuilder(Constantes.PREFIX_NOT_FOUND).append(key).append(Constantes.PREFIX_NOT_FOUND).toString();
    }
 
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(masterS
                .getCodeMessageResourceList(Bundle.Localidad.valueOf(FacesContext.getCurrentInstance()
                        .getViewRoot().getLocale().toString())));
    }
 
}