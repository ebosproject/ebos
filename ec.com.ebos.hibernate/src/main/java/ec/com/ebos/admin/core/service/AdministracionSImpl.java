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
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.admin.model.hibernate.HibernateBundle;
import ec.com.ebos.admin.model.hibernate.HibernateConfiguracion;
import ec.com.ebos.admin.model.hibernate.HibernateObjeto;
import ec.com.ebos.admin.model.hibernate.HibernateOpcion;
import ec.com.ebos.admin.model.hibernate.HibernateParametros;
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
	public List<HibernateBundle> findBundleList(Bundle bundle, Pagination pagination) {		
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
    public List<HibernateParametros> getParametrosList(Parametros param) {
        return administracionP.getParametrosList(param);
    }

    @Override
    public HibernateParametros saveParametros(HibernateParametros param) {
        return administracionP.saveParametros(param);
    }
    
    //
    //Configuracion
    //

    @Override
    public HibernateConfiguracion getConfiguracion() {
        return administracionP.getConfiguracion();
    }
    
    @Override
    public HibernateConfiguracion saveConfiguracion(HibernateConfiguracion configuracion) {
        return administracionP.saveConfiguracion(configuracion);
    }
    
    //
    // Opcion
    //
    
    @Override
    public List<HibernateOpcion> findOpcionList(Opcion opcion, Pagination pagination) {
        return administracionP.findOpcionList(opcion, pagination);
    }

    @Override
    public HibernateOpcion createOpcion() {
        return administracionP.createOpcion();
    }

    @Override
    public HibernateOpcion saveOpcion(HibernateOpcion opcion) {
        return administracionP.saveOpcion(opcion);
    }

    @Override
    public void deleteOpcion(HibernateOpcion opcion) {
        administracionP.deleteOpcion(opcion);
    }
    
    @Override
    public Opcion getOpcion(Long id) {
    	Opcion opc = administracionP.getOpcion(id); 
        return opc;
    }

    @Override
    public List<HibernateOpcion> getOpcionPadreList() {
        return administracionP.getOpcionPadreList();
    }
    
    //
    // Objeto
    //
    public List<HibernateObjeto> findObjetoList(Objeto objeto, Pagination pagination){
    	return administracionP.findObjetoList(objeto, pagination);
    }

    public HibernateObjeto createObjeto(){
    	return administracionP.createObjeto();
    }

    public HibernateObjeto saveObjeto(HibernateObjeto objeto){
    	return administracionP.saveObjeto(objeto);
    }

    public void deleteObjeto(HibernateObjeto objeto){
    	administracionP.deleteObjeto(objeto);
    }

    public Objeto getObjeto(Long id){
    	return administracionP.getObjeto(id);
    }


}
