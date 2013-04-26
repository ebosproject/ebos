package ec.com.ebos.admin.core.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.admin.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionS extends Serializable {

    //
    //Parametros
    //
    public List<Parametros> getParametrosList(Parametros param);

    public Parametros saveParametros(Parametros param);

    //
    //Configuracion
    //
    public Configuracion getConfiguracion();

    public Configuracion saveConfiguracion(Configuracion configuracionActual);

}
