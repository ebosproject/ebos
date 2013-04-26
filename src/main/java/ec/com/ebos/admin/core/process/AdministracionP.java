package ec.com.ebos.admin.core.process;

import java.util.List;

import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.admin.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionP {

    //
    //Parametros
    //
    public List<Parametros> getParametrosList(Parametros param);

    public Parametros saveParametros(Parametros param);

    //
    //Configuracion
    //
    public Configuracion getConfiguracion();

    public Configuracion saveConfiguracion(Configuracion configuracion);

}
