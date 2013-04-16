package ec.com.ebos.app.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.app.core.service.AppS;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.generic.web.jsf.mb.GenericMB;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24 
 */
public abstract class GenericAppMB<T extends Entidad<T>> extends GenericMB<T> {

	private static final long serialVersionUID = -5560084142240463124L;

	public GenericAppMB() {
        super();
    }

	@Getter @Setter
    @ManagedProperty(value = "#{appS}")
    protected AppS appS;
}
