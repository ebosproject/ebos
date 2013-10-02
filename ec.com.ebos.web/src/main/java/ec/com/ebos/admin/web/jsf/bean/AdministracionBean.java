package ec.com.ebos.admin.web.jsf.bean;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.impl.RootBeanImpl;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class AdministracionBean<T extends Entidad<T>> extends RootBeanImpl<T> {
	
	private static final long serialVersionUID = 1269243985863391052L;

	public AdministracionBean(){
        super();
    }
	
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;
	
}
