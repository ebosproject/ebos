package ec.com.ebos.admin.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.admin.core.process.AdministracionP;
import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.admin.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
@Service("administracionS")
public class AdministracionSImpl implements AdministracionS{

	private static final long serialVersionUID = 1967015954113371599L;

	@Getter @Setter
	@Autowired
    @Qualifier("administracionP")
    private AdministracionP administracionP = null;


    //
    //Parametros
    //
    
    @Override
    public List<Parametros> obtenerParametrosList(Parametros param) {
        return administracionP.obtenerParametrosList(param);
    }

    @Override
    public Parametros guardarParametros(Parametros param) {
        return administracionP.guardarParametros(param);
    }
    
    //
    //Configuracion
    //

    @Override
    public Configuracion obtenerConfiguracion() {
        return administracionP.obtenerConfiguracion();
    }
    
    @Override
    public Configuracion guardarConfiguracion(Configuracion configuracion) {
        return administracionP.guardarConfiguracion(configuracion);
    }


}
