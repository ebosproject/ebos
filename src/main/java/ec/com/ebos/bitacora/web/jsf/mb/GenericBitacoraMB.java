package ec.com.ebos.bitacora.web.jsf.mb;

import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.administracion.core.servicio.AdministracionS;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.generic.web.jsf.mb.GenericMB;
import ec.com.ebos.util.Constantes;

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
	
	@Getter @Setter
    @ManagedProperty(value = "#{administracionS}")
    protected AdministracionS administracionS;

}
