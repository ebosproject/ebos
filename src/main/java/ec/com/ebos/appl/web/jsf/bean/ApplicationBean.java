package ec.com.ebos.appl.web.jsf.bean;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.appl.core.service.ApplS;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class ApplicationBean<T extends Entidad<T>> extends RootBean<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	public ApplicationBean() {
        super();
    }

	@Getter @Setter
    @ManagedProperty(value = "#{applS}")
    protected ApplS applS;
}
