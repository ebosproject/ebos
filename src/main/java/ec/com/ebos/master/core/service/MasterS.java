package ec.com.ebos.master.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import ec.com.ebos.master.model.Bundle;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public interface MasterS extends Serializable {
    
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
    // Propiedad
    //

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination);
	
	public Propiedad createPropiedad();
	
	public Propiedad savePropiedad(Propiedad propiedad);
	
	public void deletePropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination);

	public Persona createPersona();

	public Persona savePersona(Persona persona);

	public void deletePersona(Persona persona);

}
