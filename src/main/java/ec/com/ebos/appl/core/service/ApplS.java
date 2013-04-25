package ec.com.ebos.appl.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import ec.com.ebos.appl.model.Bundle;
import ec.com.ebos.appl.model.Persona;
import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public interface ApplS extends Serializable {
    
	//
    // Bundle
    //

	public List<Bundle> obtenerBundleList(Bundle bundle, Pagination paginacion);
	
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
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo, Bundle.Localidad localidad);
	
	/**
	 * Obtiene el listado de codigos de los {@link Bundle} entities existentes por localidad
	 * 
	 * @param localidad
	 * @return
	 */
	@Cacheable(value="cacheBundle")
	public List<String> obtenerCodeMessageResourcePorLocale(Bundle.Localidad localidad);
	
	public Bundle obtenerBundleNuevo();
	
	/**
	 * Guarda un {@link Bundle} entity en la base de datos y descarga la cacheBundle
	 * para que la entidad guardada vuelva a ser cargada por las pantallas que lo utilicen 
	 * 
	 * @param bundle
	 * @return
	 */
	@CacheEvict(value="cacheBundle", allEntries=true) //TODO (epa): Probar si la descarga de las bundle cacheadas es total o solo por entity almacenada
	public Bundle guardarBundle(Bundle bundle);

	
	/**
	 * Elimina un {@link Bundle} y descarga la cacheBundle
	 * @param activeEntity
	 */
	@CacheEvict(value="cacheBundle", allEntries=true)
	public void eliminarBundle(Bundle bundle);

	
	//
    // Propiedad
    //

	public List<Propiedad> obtenerPropiedadList(Propiedad propiedad, Pagination paginacion);
	
	public Propiedad obtenerPropiedadNuevo();
	
	public Propiedad guardarPropiedad(Propiedad propiedad);
	
	public void eliminarPropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<Persona> obtenerPersonaList(Persona persona, Pagination paginacion);

	public Persona obtenerPersonaNuevo();

	public Persona guardarPersona(Persona persona);

	public void eliminarPersona(Persona persona);

}
