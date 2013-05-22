package ec.com.ebos.admin.web.jsf.bean;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class AdminBean<T extends Entidad<T>> extends RootBean<T> {
	
	private static final long serialVersionUID = 1269243985863391052L;

	public AdminBean(){
        super();
    }
	
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;
	
}
