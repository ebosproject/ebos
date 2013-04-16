package ec.com.ebos.administracion.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.administracion.core.servicio.AdministracionS;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.generic.web.jsf.mb.GenericMB;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class GenericAdministracionMB<T extends Entidad<T>> extends GenericMB<T> {
	
	private static final long serialVersionUID = 1269243985863391052L;

	public GenericAdministracionMB(){
        super();
    }
	
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;
	
}
