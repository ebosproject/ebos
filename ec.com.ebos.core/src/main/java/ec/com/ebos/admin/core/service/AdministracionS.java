package ec.com.ebos.admin.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.test.annotation.NotTransactional;

import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.HibernateConfiguracion;
import ec.com.ebos.admin.model.HibernateObjeto;
import ec.com.ebos.admin.model.HibernateOpcion;
import ec.com.ebos.admin.model.HibernateParametros;
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AdministracionS extends Serializable {

	/**
	 * Nombre del Spring Bean para {@link AdministracionS}
	 */
	public static final String BEAN_NAME = "administracionS";
	
	/**
	 * Nombre del Bean como EL a ser referenciado para la inyeccion de una instancia de {@link AdministracionSImpl} 
	 * en otro ManagedBean
	 */
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	//
    // Bundle
    //

	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination);
	
	//public Bundle getMessageResource(String codigo, Bundle.Localidad localidad);
	public Bundle getMessageResource(Bundle bundle);
	
	public List<String> getCodeMessageResourceList(Bundle.Localidad localidad);
	
	public Bundle createBundle();
	
	public Bundle loadBundle(Long id);
	
	public Bundle saveBundle(Bundle bundle);
	
	public void deleteBundle(Bundle bundle);
	
    //
    //Parametros
    //
    public List<HibernateParametros> getParametrosList(Parametros param);

    public HibernateParametros saveParametros(HibernateParametros param);

    //
    //Configuracion
    //
    public HibernateConfiguracion getConfiguracion();

    public HibernateConfiguracion saveConfiguracion(HibernateConfiguracion configuracionActual);

    
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
