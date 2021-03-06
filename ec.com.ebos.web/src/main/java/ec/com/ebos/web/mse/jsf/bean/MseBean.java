package ec.com.ebos.web.mse.jsf.bean;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ec.com.ebos.core.mse.service.MseS;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.web.root.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class MseBean<T extends Entidad> extends RootBean<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	@Getter @Setter
	@Autowired
    @Qualifier(MseS.BEAN_NAME)
    protected MseS mseS;
	
	public MseBean() {
        super();
    }
	
}
