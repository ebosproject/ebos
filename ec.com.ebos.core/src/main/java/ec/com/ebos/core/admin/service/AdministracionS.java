package ec.com.ebos.core.admin.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.core.admin.model.Bundle;
import ec.com.ebos.core.admin.model.Configuracion;
import ec.com.ebos.core.admin.model.Objeto;
import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.admin.model.Parametros;
import ec.com.ebos.core.admin.model.Bundle.Localidad;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.security.model.Rol;
//import org.springframework.test.annotation.NotTransactional;

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
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	//
    // Bundle
    //

	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination);
	
	//public Bundle getMessageResource(String codigo, Bundle.Localidad localidad);
	public Bundle getMessageResource(Bundle bundle);
	
	public List<String> getCodeMessageResourceList(Localidad localidad);
	
	public Bundle createBundle();
	
	public Bundle loadBundle(Long id);
	
	public Bundle saveBundle(Bundle bundle);
	
	public void deleteBundle(Bundle bundle);
	
	public Bundle getInstanceBundle();
	
    //
    //Parametros
    //
    public List<Parametros> getParametrosList(Parametros param);

    public Parametros saveParametros(Parametros param);
    
    public Parametros getInstanceParametros();

    //
    //Configuracion
    //
    public Configuracion getConfiguracion();

    public Configuracion saveConfiguracion(Configuracion configuracionActual);
    
    public Configuracion getInstanceConfiguracion();

    
    //
    // Opcion
    //
    
    public List<Opcion> findOpcionList(Opcion opcion, Pagination pagination);

    public Opcion createOpcion();

    public Opcion saveOpcion(Opcion opcion);

    public void deleteOpcion(Opcion opcion);
    
    public Opcion getOpcion(Long id);
    
    public List<Opcion> getOpcionPadreList();
    
    public Opcion getInstanceOpcion();
    
    //
    // Objeto
    //
    public List<Objeto> findObjetoList(Objeto objeto, Pagination pagination);

    public Objeto createObjeto();

    public Objeto saveObjeto(Objeto objeto);

    public void deleteObjeto(Objeto objeto);

    public Objeto getObjeto(Long id);

	public Objeto getInstanceObjeto();


}
