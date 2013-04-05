package ec.com.platform.app.core.gestor;

import java.util.List;

import ec.com.platform.app.model.Bundle;
import ec.com.platform.app.model.Bundle.Localidad;
import ec.com.platform.app.model.Persona;
import ec.com.platform.app.model.Propiedad;
import ec.com.platform.fwk.crud.Paginacion;

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

	public List<Bundle> obtenerBundleList(Bundle bundle, Paginacion paginacion);

	public Bundle obtenerBundleNuevo();

	public void eliminarBundle(Bundle activeEntity);
	
	//
	// Propiedad
	//

	public List<Propiedad> obtenerPropiedadList(Propiedad propiedad, Paginacion paginacion);

	public Propiedad obtenerPropiedadNuevo();

	public Propiedad guardarPropiedad(Propiedad propiedad);

	public void eliminarPropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<Persona> obtenerPersonaList(Persona persona, Paginacion paginacion);

	public Persona obtenerPersonaNuevo();

	public Persona guardarPersona(Persona persona);

	public void eliminarPersona(Persona persona);
	
}
