package ec.com.platform.administracion.core.servicio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.platform.administracion.core.gestor.AdministracionG;
import ec.com.platform.administracion.model.Configuracion;
import ec.com.platform.administracion.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
@Service("administracionS")
public class AdministracionSImpl implements AdministracionS{

	private static final long serialVersionUID = 1967015954113371599L;

	@Getter @Setter
	@Autowired
    @Qualifier("administracionG")
    private AdministracionG administracionG = null;


    //
    //Parametros
    //
    
    @Override
    public List<Parametros> obtenerParametrosList(Parametros param) {
        return administracionG.obtenerParametrosList(param);
    }

    @Override
    public Parametros guardarParametros(Parametros param) {
        return administracionG.guardarParametros(param);
    }
    
    //
    //Configuracion
    //

    @Override
    public Configuracion obtenerConfiguracion() {
        return administracionG.obtenerConfiguracion();
    }
    
    @Override
    public Configuracion guardarConfiguracion(Configuracion configuracion) {
        return administracionG.guardarConfiguracion(configuracion);
    }


}
