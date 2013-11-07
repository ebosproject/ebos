package ec.com.ebos.core.admin.process;

import java.util.List;

import ec.com.ebos.core.admin.model.Bundle;
import ec.com.ebos.core.admin.model.Configuracion;
import ec.com.ebos.core.admin.model.Objeto;
import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.admin.model.Parametros;
import ec.com.ebos.core.admin.model.Bundle.Localidad;
import ec.com.ebos.core.orm.crud.Pagination;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionP {

    //
    // Bundle
    //
	public Bundle getMessageResource(String codigo, Localidad localidad);
	
	public List<String> getCodeMessageResourceList(Localidad localidad);
	
	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination);

	public Bundle buildBundle();
	
	public Bundle loadBundle(Long id);
	
	public Bundle saveBundle(Bundle messageResource);

	public void deleteBundle(Bundle activeEntity);

	
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

    //
    // Opcion
    //
    public List<Opcion> findOpcionList(Opcion opcion, Pagination pagination);

    public Opcion createOpcion();

    public Opcion saveOpcion(Opcion opcion);

    public void deleteOpcion(Opcion opcion);

    public Opcion getOpcion(Long id);

    public List<Opcion> getOpcionPadreList();


    //
    // Objeto
    //
    public List<Objeto> findObjetoList(Objeto objeto, Pagination pagination);

    public Objeto createObjeto();

    public Objeto saveObjeto(Objeto objeto);

    public void deleteObjeto(Objeto objeto);

    public Objeto getObjeto(Long id);
}
