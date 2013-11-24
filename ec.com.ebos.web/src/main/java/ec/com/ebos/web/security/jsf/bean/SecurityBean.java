package ec.com.ebos.web.security.jsf.bean;

import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.web.root.jsf.bean.RootBean;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class SecurityBean<T extends Entidad> extends RootBean<T> {

	private static final long serialVersionUID = -6974386615362007305L;

	public SecurityBean() {
        super();
    }
}
