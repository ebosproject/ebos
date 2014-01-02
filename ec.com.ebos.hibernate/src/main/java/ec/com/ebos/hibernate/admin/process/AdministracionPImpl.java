package ec.com.ebos.hibernate.admin.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.core.admin.exception.AdministracionException;
import ec.com.ebos.core.admin.model.Bundle;
import ec.com.ebos.core.admin.model.Configuracion;
import ec.com.ebos.core.admin.model.Objeto;
import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.admin.model.Parametros;
import ec.com.ebos.core.admin.model.Bundle.Localidad;
import ec.com.ebos.core.admin.process.AdministracionP;
import ec.com.ebos.core.orm.crud.GenericCriteria;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.root.model.Entidad.Estado;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.hibernate.admin.model.HibernateBundle;
import ec.com.ebos.hibernate.admin.model.HibernateObjeto;
import ec.com.ebos.hibernate.admin.model.HibernateOpcion;
import ec.com.ebos.hibernate.admin.model.field.Bundle_;
import ec.com.ebos.hibernate.admin.model.field.Objeto_;
import ec.com.ebos.hibernate.admin.model.field.Opcion_;
import ec.com.ebos.hibernate.admin.model.field.Parametros_;
import ec.com.ebos.hibernate.master.model.field.MessageResource_;
import ec.com.ebos.hibernate.root.model.HibernateEntidad;
import ec.com.ebos.hibernate.root.model.field.Auditoria_;
import ec.com.ebos.hibernate.root.process.RootPImpl;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Repository("adminP")
public class AdministracionPImpl extends RootPImpl<Object, AdministracionException> implements AdministracionP{

	private static final long serialVersionUID = -5387275517395137673L;
	
	//
    // Bundle
    //
	
	@Override
	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);
		criteria.addEqualsIfNotZero(Bundle_.id, bundle.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		criteria.addLikeIfNotNull(Bundle_.codigo, bundle.getCodigo());
        criteria.addEqualsIfNotNull(Bundle_.localidad, bundle.getLocalidad());

        //criteria.addOrderAsc(Bundle_.codigo); //TODO (epa): Ordenar por codigo y evitar grupo by por uso de funcion de agrupacion en la paginacion
        
        //return findByCriteria(criteria, pagination);
        return new ArrayList<Bundle>();
	}
	
	@Override
	public Bundle getMessageResource(String codigo,
			HibernateBundle.Localidad localidad) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);
		criteria.addEquals(MessageResource_.codigo, codigo);
		criteria.addEquals(MessageResource_.localidad, localidad);
		return findFirstByCriteria(criteria);
	}

	@Override
	public List<String> getCodeMessageResourceList(Localidad localidad) {
		return findByQuery("select m.codigo from Bundle m where m.localidad = :localidad", String.class, localidad);
	}
	
	@Override
	public Bundle buildBundle() {
		Bundle bundle = new HibernateBundle();
		bundle.setLocalidad(getSessionBean().getLocalidad());
        return bundle;
	}
	
	@Override
	public Bundle loadBundle(Long id) {
        return load(id, Bundle.class);
	}

	@Override
	public Bundle saveBundle(Bundle bundle) {
		bundle = saveOrMerge(bundle);
        putSuccess("bundle.success.save", bundle.getId());
        return bundle;
	}

	@Override
	public void deleteBundle(Bundle bundle) {
		Long id = bundle.getId();
        delete(bundle);
        putSuccess("bundle.success.delete", id);
	}
	
	//
    //Parametros
    //
	
    @Override
    public List<Parametros> getParametrosList(Parametros param) {
        GenericCriteria<Parametros> criteria = GenericCriteria.forClass(Parametros.class);
        if(param != null){
            criteria.addLike(Parametros_.grupo, param.getGrupo());
            criteria.addLike(Parametros_.clave, param.getClave());
        }        
        return findByCriteria(criteria);
    }

    @Override
    public Parametros saveParametros(Parametros param) throws AdministracionException{
        Date fecha = new Date();
        if(EntityUtils.isPersistent(param)){
            param.setModificado(fecha);
        }else{
            param.setCreado(fecha);
            param.setModificado(fecha);
            param.setEstado(Entidad.Estado.ACTIVO);
        }
        param = saveOrMerge(param);
        
        //Actualiza lista de parametros en el bean de aplicacion
        //getApp().getConfiguracion().setParametros(buildParametrosHash(obtenerParametrosList(null)));
        //TODO (epa): refrescar parametros en Master Spring Bean
        putSuccess("param.success.save",+param.getId());
        return param;
    }

    
    //
    //Configuracion
    //
    
    @SuppressWarnings("unchecked")
	@Override
    public Configuracion getConfiguracion() {                
        Configuracion conf = get(new Long(1), Configuracion.class);       
        conf.setParametros(buildParametrosHash(getParametrosList(null)));
        return conf;
    }

    @Override
    public Configuracion saveConfiguracion(Configuracion configuracion) throws AdministracionException{                
                
        if(configuracion.isEnviarSmsPrx() && configuracion.isEnviarSmsGatewayPrx()){            
            configuracion.setEnviarSmsGatewayPrx(false);
            putWarning("Solo se puede habilitar una opcion de envio de mensajes, por default es Sms");            
        }        
        configuracion = update(configuracion);      
        
        //Actualiza las configuraciones del sistema en el bean de aplicacion
        //getApp().setConfiguracion(configuracion);
        //TODO (epa): refrescar parametros en Master Spring Bean
        putSuccess("Configuración actualizada correctamente");
        return configuracion;
    }
    
    /**
     * Construye una lista de propuedades en hashMap
     * @param parametrosList
     * @return 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap buildParametrosHash(List<Parametros> parametrosList){
        HashMap hashParam = new HashMap();
        for (Parametros parametros : parametrosList) {
            hashParam.put(parametros.getClave(), parametros.getValor());
        }
        return hashParam;
    }
    
    
    //
    // Opcion
    //
    @Override
    public List<Opcion> findOpcionList(Opcion opcion, Pagination pagination) {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals("estado", HibernateEntidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador, Opcion_.padre);
        
        criteria.addAliasedLeftJoins(Opcion_.padre);
        criteria.addLikeIfNotNull(Opcion_.nombre, opcion.getNombre());
        criteria.addLikeIfNotNull(Opcion_.descripcion, opcion.getDescripcion());
        criteria.addLikeIfNotNull(Opcion_.etiqueta, opcion.getEtiqueta());
        criteria.addLikeIfNotNull(Opcion_.target, opcion.getTarget());
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Opcion createOpcion() {
        Opcion opcion = new HibernateOpcion();
        opcion.setEstado(Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Opcion saveOpcion(Opcion opcion) {
        if (!EntityUtils.isPersistent(opcion)) {
            opcion.setEstado(HibernateEntidad.Estado.ACTIVO);
        }
        opcion = saveOrMerge(opcion);
        putSuccess("opcion.success.save",opcion.getId());
        return opcion;
    }

    @Override
    public void deleteOpcion(Opcion opcion) {
        Long id = opcion.getId();
        delete(opcion);
        putSuccess("opcion.success.delete",id);
    }

    @Override
    public Opcion getOpcion(Long id) {
    	GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
    	criteria.addEquals("estado", Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        return findFirstByCriteria(criteria);
    }

    @Override
    public List<Opcion> getOpcionPadreList() {
        GenericCriteria<Opcion> criteria = GenericCriteria.forClass(Opcion.class);
        criteria.addEquals(Opcion_.estado, Entidad.Estado.ACTIVO);
        criteria.addIsNull(Opcion_.padre);
        criteria.addOrderAsc(Opcion_.padre);
        return findByCriteria(criteria);
    }
    
    //
    // Objeto
    //
    
    @Override
    public List<Objeto> findObjetoList(Objeto objeto, Pagination pagination) {
        GenericCriteria<Objeto> criteria = GenericCriteria.forClass(Objeto.class);
        criteria.addEquals("estado", Entidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        if (objeto != null) {
	        criteria.addLike(Objeto_.descripcion, objeto.getDescripcion());
	        criteria.addLike(Objeto_.codigo, objeto.getCodigo());
	        criteria.addLike(Objeto_.estado, objeto.getEstado().getValue());
	        criteria.addLike(Objeto_.tipo, objeto.getTipo().getValue());
        }
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public Objeto createObjeto() {
        Objeto opcion = new HibernateObjeto();
        opcion.setEstado(Estado.INACTIVO);
        return opcion;
    }

    @Override
    public Objeto saveObjeto(Objeto objeto) {
        if (!EntityUtils.isPersistent(objeto)) {
            objeto.setEstado(Entidad.Estado.ACTIVO);
        }
        objeto = saveOrMerge(objeto);
        putSuccess("objeto.success.save",objeto.getId());
        return objeto;
    }

    @Override
    public void deleteObjeto(Objeto objeto) {
        Long id = objeto.getId();
    	delete(objeto);
    	putSuccess("objeto.success.delete",id);
    }

    @Override
    public Objeto getObjeto(Long id) {
        return get(id, HibernateObjeto.class);
    }
}
