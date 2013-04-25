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
    public List<Parametros> obtenerParametrosList(Parametros param);

    public Parametros guardarParametros(Parametros param);

    //
    //Configuracion
    //
    public Configuracion obtenerConfiguracion();

    public Configuracion guardarConfiguracion(Configuracion configuracion);

}
