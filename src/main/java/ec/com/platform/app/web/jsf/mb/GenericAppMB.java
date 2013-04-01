package ec.com.platform.app.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.app.core.service.AppS;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.generic.web.jsf.mb.GenericMB;
import ec.com.platform.util.Constantes;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class GenericAppMB<T extends Generic<T>> extends GenericMB<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	public GenericAppMB() {
        super();
    }
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".app.resources.app";
    }
	
	@Getter @Setter
    @ManagedProperty(value = "#{appS}")
    protected AppS appS;
}
