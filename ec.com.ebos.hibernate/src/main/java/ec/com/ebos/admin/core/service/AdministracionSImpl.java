package ec.com.ebos.admin.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ec.com.ebos.admin.core.process.AdministracionP;
import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.admin.model.hibernate.HibernateBundle;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author Eduardo Plua Alay
 */
@Service("administracionS")
public class AdministracionSImpl implements AdministracionS{

	private static final long serialVersionUID = 1967015954113371599L;

	@Getter @Setter
	@Autowired
    @Qualifier("adminP")
    private AdministracionP administracionP = null;


	//
    // Bundle
    //
	
	@Override
	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination) {		
		return administracionP.findBundleList(bundle, pagination);
	}
	
	
	/**
	 * Obtiene un unique {@link HibernateBundle} entity por su codigo y localidad.
	 * 
	 * La entidad obtenida es almacenada en cache para uso de todas las pantallas que lo requieran
	 * 
	 * @param codigo
	 * @param localidad
	 * @return
	 */
	@Override
	@Cacheable(value="cacheBundle", key="#bundle.keyCache")
	public Bundle getMessageResource(Bundle bundle){
		return administracionP.getMessageResource(bundle.getCodigo(), bundle.getLocalidad());
	}

	/**
	 * Obtiene el listado de codigos de los {@link HibernateBundle} entities existentes por localidad
	 * 
	 * @param localidad
	 * @return
	 */
	@Override
	public List<String> getCodeMessageResourceList(HibernateBundle.Localidad localidad) {
		return administracionP.getCodeMessageResourceList(localidad);
	}
	
	@Override
	public Bundle createBundle() {
		return administracionP.buildBundle();
	}
	
	public Bundle loadBundle(Long id){
		return administracionP.loadBundle(id);
	}
	
	/**
	 * Guarda un {@link HibernateBundle} entity en la base de datos y descarga la cacheBundle
	 * para que la entidad guardada vuelva a ser cargada por las pantallas que lo utilicen 
	 * 
	 * @param bundle
	 * @return
	 */
	@Override
	@CacheEvict(value="cacheBundle", key="#bundle.keyCache", beforeInvocation = true)
	@Cacheable(value="cacheBundle", key="#bundle.keyCache")
	public Bundle saveBundle(Bundle bundle) {
        return administracionP.saveBundle(bundle);
	}
	
	/**
	 * Elimina un {@link HibernateBundle} y descarga la cacheBundle
	 * @param activeEntity
	 */
	@Override
	@CacheEvict(value="cacheBundle", key="#{bundle.keyCache}")
	public void deleteBundle(Bundle bundle) {
		administracionP.deleteBundle(bundle);
	}
	
    //
    //Parametros
    //
    
    @Override
    public List<Parametros> getParametrosList(Parametros param) {
        return administracionP.getParametrosList(param);
    }

    @Override
    public Parametros saveParametros(Parametros param) {
        return administracionP.saveParametros(param);
    }
    
    //
    //Configuracion
    //

    @Override
    public Configuracion getConfiguracion() {
        return administracionP.getConfiguracion();
    }
    
    @Override
    public Configuracion saveConfiguracion(Configuracion configuracion) {
        return administracionP.saveConfiguracion(configuracion);
    }
    
    //
    // Opcion
    //
    
    @Override
    public List<Opcion> findOpcionList(Opcion opcion, Pagination pagination) {
        return administracionP.findOpcionList(opcion, pagination);
    }

    @Override
    public Opcion createOpcion() {
        return administracionP.createOpcion();
    }

    @Override
    public Opcion saveOpcion(Opcion opcion) {
        return administracionP.saveOpcion(opcion);
    }

    @Override
    public void deleteOpcion(Opcion opcion) {
        administracionP.deleteOpcion(opcion);
    }
    
    @Override
    public Opcion getOpcion(Long id) {
    	Opcion opc = administracionP.getOpcion(id); 
        return opc;
    }

    @Override
    public List<Opcion> getOpcionPadreList() {
        return administracionP.getOpcionPadreList();
    }
    
    //
    // Objeto
    //
    public List<Objeto> findObjetoList(Objeto objeto, Pagination pagination){
    	return administracionP.findObjetoList(objeto, pagination);
    }

    public Objeto createObjeto(){
    	return administracionP.createObjeto();
    }

    public Objeto saveObjeto(Objeto objeto){
    	return administracionP.saveObjeto(objeto);
    }

    public void deleteObjeto(Objeto objeto){
    	administracionP.deleteObjeto(objeto);
    }

    public Objeto getObjeto(Long id){
    	return administracionP.getObjeto(id);
    }


}
