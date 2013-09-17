package ec.com.ebos.conta.web.jsf.bean;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.conta.core.service.ContaS;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class ContaBean<T extends Entidad<T>> extends RootBean<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	@Getter @Setter
    @ManagedProperty(value = "#{contaS}")
    protected ContaS contaS;
	
	public ContaBean() {
        super();
    }
	
}
