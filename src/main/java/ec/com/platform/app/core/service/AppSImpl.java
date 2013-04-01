package ec.com.platform.app.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.platform.app.core.gestor.AppG;
import ec.com.platform.app.model.Bundle;
import ec.com.platform.app.model.Propiedad;
import ec.com.platform.fwk.crud.Paginacion;

/**
 * @author Eduardo Plua Alay
 */
@Service("appS")
public class AppSImpl implements AppS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("appG")
    private AppG appG;
	
	//
    // Bundle
    //
	
	@Override
	public List<Bundle> obtenerBundleList(Bundle bundle, Paginacion paginacion) {		
		return appG.obtenerBundleList(bundle, paginacion);
	}
	
	@Override
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo, String localidad){
		return appG.obtenerMessageResourcePorCodeYLocale(codigo, localidad);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad) {
		return appG.obtenerCodeMessageResourcePorLocale(localidad);
	}
	
	@Override
	public Bundle obtenerBundleNuevo() {
		return appG.obtenerBundleNuevo();
	}
	
	@Override
	public Bundle guardarBundle(Bundle messageResource) {
        return appG.guardarBundle(messageResource);
	}

	@Override
	public void eliminarBundle(Bundle bundle) {
		appG.eliminarBundle(bundle);
	}
	
	
	//
    // Propiedad
    //

	public List<Propiedad> obtenerPropiedadList(Propiedad propiedad, Paginacion paginacion){
		return appG.obtenerPropiedadList(propiedad, paginacion);
	}
	
	public Propiedad obtenerPropiedadNuevo(){
		return appG.obtenerPropiedadNuevo();
	}
	
	public Propiedad guardarPropiedad(Propiedad propiedad){
		return appG.guardarPropiedad(propiedad);
	}
	
	public void eliminarPropiedad(Propiedad propiedad){
		appG.eliminarPropiedad(propiedad);
	}
}
