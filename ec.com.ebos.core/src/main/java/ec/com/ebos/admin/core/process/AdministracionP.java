package ec.com.ebos.admin.core.process;

import java.util.List;

import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.HibernateConfiguracion;
import ec.com.ebos.admin.model.HibernateObjeto;
import ec.com.ebos.admin.model.HibernateOpcion;
import ec.com.ebos.admin.model.HibernateParametros;
import ec.com.ebos.admin.model.Bundle.Localidad;
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.orm.crud.Pagination;

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
    public List<HibernateParametros> getParametrosList(Parametros param);

    public HibernateParametros saveParametros(HibernateParametros param);

    //
    //Configuracion
    //
    public HibernateConfiguracion getConfiguracion();

    public HibernateConfiguracion saveConfiguracion(HibernateConfiguracion configuracion);

    //
    // Opcion
    //
    public List<HibernateOpcion> findOpcionList(Opcion opcion, Pagination pagination);

    public HibernateOpcion createOpcion();

    public HibernateOpcion saveOpcion(HibernateOpcion opcion);

    public void deleteOpcion(HibernateOpcion opcion);

    public Opcion getOpcion(Long id);

    public List<HibernateOpcion> getOpcionPadreList();


    //
    // Objeto
    //
    public List<HibernateObjeto> findObjetoList(Objeto objeto, Pagination pagination);

    public HibernateObjeto createObjeto();

    public HibernateObjeto saveObjeto(HibernateObjeto objeto);

    public void deleteObjeto(HibernateObjeto objeto);

    public Objeto getObjeto(Long id);
}
