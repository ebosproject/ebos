package ec.com.ebos.web.conta.jsf.bean;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ec.com.ebos.core.conta.service.ContaS;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.web.root.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @date 2013-03-24 
 */
public abstract class ContaBean<T extends Entidad> extends RootBean<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	@Getter @Setter
	@Autowired
    @Qualifier(ContaS.BEAN_NAME)
    protected ContaS contaS;
	
	public ContaBean() {
        super();
    }
	
}
