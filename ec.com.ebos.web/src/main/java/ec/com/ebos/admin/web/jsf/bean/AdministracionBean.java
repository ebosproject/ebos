package ec.com.ebos.admin.web.jsf.bean;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.web.jsf.bean.RootBean;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class AdministracionBean<T extends Entidad<T>> extends RootBean<T> {
	
	private static final long serialVersionUID = 1269243985863391052L;

	@Getter @Setter
    @Autowired
	@Qualifier(AdministracionS.BEAN_NAME)
    protected AdministracionS administracionS;
	
	public AdministracionBean(){
        super();
    }
	
}
