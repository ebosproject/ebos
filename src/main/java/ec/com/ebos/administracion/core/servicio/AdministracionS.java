package ec.com.ebos.administracion.core.servicio;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.administracion.model.Configuracion;
import ec.com.ebos.administracion.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionS extends Serializable {

    //
    //Parametros
    //
    public List<Parametros> obtenerParametrosList(Parametros param);

    public Parametros guardarParametros(Parametros param);

    //
    //Configuracion
    //
    public Configuracion obtenerConfiguracion();

    public Configuracion guardarConfiguracion(Configuracion configuracionActual);

}
