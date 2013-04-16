package ec.com.ebos.seguridad.web.jsf.mb;

import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.generic.web.jsf.mb.GenericMB;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class GenericSeguridadMB<T extends Entidad<T>> extends GenericMB<T> {

	private static final long serialVersionUID = -6974386615362007305L;

	public GenericSeguridadMB() {
        super();
    }
}
