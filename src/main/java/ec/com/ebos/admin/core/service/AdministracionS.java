package ec.com.ebos.admin.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.Configuracion;
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
	
	/**
	 * Obtiene un unique {@link Bundle} entity por su codigo y localidad.
	 * 
	 * La entidad obtenida es almacenada en cache para uso de todas las pantallas que lo requieran
	 * 
	 * @param codigo
	 * @param localidad
	 * @return
	 */
	@Cacheable(value="cacheBundle")
	public Bundle getMessageResource(String codigo, Bundle.Localidad localidad);
	
	/**
	 * Obtiene el listado de codigos de los {@link Bundle} entities existentes por localidad
	 * 
	 * @param localidad
	 * @return
	 */
	@Cacheable(value="cacheBundle")
	public List<String> getCodeMessageResourceList(Bundle.Localidad localidad);
	
	public Bundle createBundle();
	
	public Bundle loadBundle(Long id);
	
	/**
	 * Guarda un {@link Bundle} entity en la base de datos y descarga la cacheBundle
	 * para que la entidad guardada vuelva a ser cargada por las pantallas que lo utilicen 
	 * 
	 * @param bundle
	 * @return
	 */
	@CacheEvict(value="cacheBundle", allEntries=true) //TODO (epa): Probar si la descarga de las bundle cacheadas es total o solo por entity almacenada
	public Bundle saveBundle(Bundle bundle);

	
	/**
	 * Elimina un {@link Bundle} y descarga la cacheBundle
	 * @param activeEntity
	 */
	@CacheEvict(value="cacheBundle", allEntries=true)
	public void deleteBundle(Bundle bundle);
	
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
