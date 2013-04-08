package ec.com.ebos.administracion.core.gestor;

import java.util.List;

import ec.com.ebos.administracion.model.Configuracion;
import ec.com.ebos.administracion.model.Parametros;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionG {

    //
    //Parametros
    //
    public List<Parametros> obtenerParametrosList(Parametros param);

    public Parametros guardarParametros(Parametros param);

    //
    //Configuracion
    //
    public Configuracion obtenerConfiguracion();

    public Configuracion guardarConfiguracion(Configuracion configuracion);

}
