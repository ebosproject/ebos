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
    public List<Parametros> getParametrosList(Parametros param) {
        return administracionP.getParametrosList(param);
    }

    @Override
    public Parametros saveParametros(Parametros param) {
        return administracionP.saveParametros(param);
    }
    
    //
    //Configuracion
    //

    @Override
    public Configuracion getConfiguracion() {
        return administracionP.getConfiguracion();
    }
    
    @Override
    public Configuracion saveConfiguracion(Configuracion configuracion) {
        return administracionP.saveConfiguracion(configuracion);
    }


}
