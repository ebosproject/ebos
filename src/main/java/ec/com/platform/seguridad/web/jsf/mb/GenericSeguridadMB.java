package ec.com.platform.seguridad.web.jsf.mb;

import ec.com.platform.generic.model.Entidad;
import ec.com.platform.generic.web.jsf.mb.GenericMB;
import ec.com.platform.util.Constantes;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class GenericSeguridadMB<T extends Entidad<T>> extends GenericMB<T> {

	private static final long serialVersionUID = -6974386615362007305L;

	public GenericSeguridadMB() {
        super();
    }
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".seguridad.resources.seguridad";
    }
}
