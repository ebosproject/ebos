package ec.com.ebos.master.web.jsf.bean;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ec.com.ebos.core.master.service.MasterS;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class MasterBean<T extends Entidad> extends RootBean<T> {

	private static final long serialVersionUID = -5560084142240463124L;
	
	@Getter @Setter
	@Autowired
	@Qualifier(MasterS.BEAN_NAME)
    protected MasterS masterS;
	
	public MasterBean() {
        super();
    }
	
}
