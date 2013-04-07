package ec.com.platform.bitacora.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.administracion.core.servicio.AdministracionS;
import ec.com.platform.generic.model.Entidad;
import ec.com.platform.generic.web.jsf.mb.GenericMB;
import ec.com.platform.util.Constantes;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-12
 * 
 */
public abstract class GenericBitacoraMB<T extends Entidad<T>> extends GenericMB<T> {
	
	private static final long serialVersionUID = -6616673494138961943L;

	public GenericBitacoraMB(){
        super();
    }
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".bitacora.resources.bitacora";
    }
    
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;

}
