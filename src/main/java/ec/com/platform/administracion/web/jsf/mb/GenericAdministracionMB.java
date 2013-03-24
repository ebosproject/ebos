package ec.com.platform.administracion.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.administracion.core.servicio.AdministracionS;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.generic.web.jsf.mb.GenericMB;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class GenericAdministracionMB<T extends Generic<T>> extends GenericMB<T> {
	
	private static final long serialVersionUID = 1269243985863391052L;

	public GenericAdministracionMB(){
        super();
    }
    
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;

}
