package ec.com.ebos.appl.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.appl.core.process.ApplP;
import ec.com.ebos.appl.model.Bundle;
import ec.com.ebos.appl.model.Persona;
import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author Eduardo Plua Alay
 */
@Service("applS")
public class ApplSImpl implements ApplS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("applP")
    private ApplP applP;
	
	//
    // Bundle
    //
	
	@Override
	public List<Bundle> obtenerBundleList(Bundle bundle, Pagination paginacion) {		
		return applP.obtenerBundleList(bundle, paginacion);
	}
	
	@Override
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo, Bundle.Localidad localidad){
		return applP.obtenerMessageResourcePorCodeYLocale(codigo, localidad);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(Bundle.Localidad localidad) {
		return applP.obtenerCodeMessageResourcePorLocale(localidad);
	}
	
	@Override
	public Bundle obtenerBundleNuevo() {
		return applP.obtenerBundleNuevo();
	}
	
	@Override
	public Bundle guardarBundle(Bundle messageResource) {
        return applP.guardarBundle(messageResource);
	}

	@Override
	public void eliminarBundle(Bundle bundle) {
		applP.eliminarBundle(bundle);
	}
	
	
	//
    // Propiedad
    //

	public List<Propiedad> obtenerPropiedadList(Propiedad propiedad, Pagination paginacion){
		return applP.obtenerPropiedadList(propiedad, paginacion);
	}
	
	public Propiedad obtenerPropiedadNuevo(){
		return applP.obtenerPropiedadNuevo();
	}
	
	public Propiedad guardarPropiedad(Propiedad propiedad){
		return applP.guardarPropiedad(propiedad);
	}
	
	public void eliminarPropiedad(Propiedad propiedad){
		applP.eliminarPropiedad(propiedad);
	}
	
	//
	// Persona
	//
	
	public List<Persona> obtenerPersonaList(Persona persona, Pagination paginacion){
		return applP.obtenerPersonaList(persona, paginacion);
	}

	public Persona obtenerPersonaNuevo(){
		return applP.obtenerPersonaNuevo();
	}

	public Persona guardarPersona(Persona persona){
		return applP.guardarPersona(persona);
	}

	public void eliminarPersona(Persona persona){
		applP.eliminarPersona(persona);
	}
}
