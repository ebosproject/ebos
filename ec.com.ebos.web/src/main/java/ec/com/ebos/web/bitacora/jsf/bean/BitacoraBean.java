package ec.com.ebos.web.bitacora.jsf.bean;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.core.admin.service.AdministracionS;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.web.root.jsf.bean.RootBean;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
public abstract class BitacoraBean<T extends Entidad> extends RootBean<T> {
	
	private static final long serialVersionUID = -6616673494138961943L;

	public BitacoraBean(){
        super();
    }
	
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;

}
