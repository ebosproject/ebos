package ec.com.platform.seguridad.web.jsf.mb;

import ec.com.platform.generic.model.Generic;
import ec.com.platform.generic.web.jsf.mb.GenericMB;
import ec.com.platform.util.Constantes;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class GenericSeguridadMB<T extends Generic<T>> extends GenericMB<T> {

	private static final long serialVersionUID = -6974386615362007305L;

	public GenericSeguridadMB() {
        super();
    }
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".seguridad.resources.seguridad";
    }
}
