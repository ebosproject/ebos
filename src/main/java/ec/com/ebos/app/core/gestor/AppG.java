package ec.com.ebos.app.core.gestor;

import java.util.List;

import ec.com.ebos.app.model.Bundle;
import ec.com.ebos.app.model.Persona;
import ec.com.ebos.app.model.Propiedad;
import ec.com.ebos.app.model.Bundle.Localidad;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface AppG {

    //
    // MessageResource
    //
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo, Localidad localidad);
	
	public List<String> obtenerCodeMessageResourcePorLocale(Localidad localidad);
	
	public Bundle guardarBundle(Bundle messageResource);

	public List<Bundle> obtenerBundleList(Bundle bundle, Pagination paginacion);

	public Bundle obtenerBundleNuevo();

	public void eliminarBundle(Bundle activeEntity);
	
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
