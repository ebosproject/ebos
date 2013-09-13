package ec.com.ebos.master.web.resources;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.context.EbosContext;
import ec.com.ebos.util.Constantes;


/**
 * Custom ResourceBundle para acceder a propiedades en la BD
 * 
 * @author Eduardo Plua Alay
 * @since 2013/02/15
 */
public class DatabaseDrivenResourceBundle extends ResourceBundle {
 
    private AdministracionS administracionS;
 
    public DatabaseDrivenResourceBundle() {
    	administracionS = EbosContext.getBean(AdministracionS.BEAN_NAME, AdministracionS.class);
    }
    
    @Override
    protected Object handleGetObject(String key) {
    	Bundle bundle = new Bundle();
    	bundle.setCodigo(key);
    	bundle.setLocalidad(Bundle.Localidad.valueOf(FacesContext.getCurrentInstance()
                        .getViewRoot().getLocale().toString()));
        final Bundle messageResource = administracionS.getMessageResource(bundle);        
        
        if (messageResource != null) {
            return messageResource.getValor();
        }
        return new StringBuilder(Constantes.PREFIX_NOT_FOUND).append(key).append(Constantes.PREFIX_NOT_FOUND).toString();
    }
 
    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(administracionS
                .getCodeMessageResourceList(Bundle.Localidad.valueOf(FacesContext.getCurrentInstance()
                        .getViewRoot().getLocale().toString())));
    }
 
}