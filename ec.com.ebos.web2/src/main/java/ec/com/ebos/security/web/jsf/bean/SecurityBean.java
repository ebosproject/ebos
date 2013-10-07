package ec.com.ebos.security.web.jsf.bean;

import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.impl.RootBeanImpl;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public abstract class SecurityBean<T extends Entidad<T>> extends RootBeanImpl<T> {

	private static final long serialVersionUID = -6974386615362007305L;

	public SecurityBean() {
        super();
    }
}
