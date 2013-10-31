package ec.com.ebos.admin.core.process;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.admin.core.exception.AdministracionException;
import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.admin.model.hibernate.HibernateBundle;
import ec.com.ebos.admin.model.hibernate.HibernateConfiguracion;
import ec.com.ebos.admin.model.hibernate.HibernateObjeto;
import ec.com.ebos.admin.model.hibernate.HibernateOpcion;
import ec.com.ebos.admin.model.hibernate.HibernateParametros;
import ec.com.ebos.admin.model.hibernate.HibernateBundle.Localidad;
import ec.com.ebos.admin.model.hibernate.field.Bundle_;
import ec.com.ebos.admin.model.hibernate.field.Objeto_;
import ec.com.ebos.admin.model.hibernate.field.Opcion_;
import ec.com.ebos.admin.model.hibernate.field.Parametros_;
import ec.com.ebos.master.model.field.MessageResource_;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.root.model.hibernate.field.Auditoria_;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;
import ec.com.ebos.util.EntityUtils;

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
	public List<HibernateBundle> findBundleList(Bundle bundle, Pagination pagination) {
		GenericCriteria<HibernateBundle> criteria = GenericCriteria.forClass(HibernateBundle.class);

		criteria.addEqualsIfNotZero(Bundle_.id, bundle.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		
		criteria.addLikeIfNotNull(Bundle_.codigo, bundle.getCodigo());
        criteria.addEqualsIfNotNull(Bundle_.localidad, bundle.getLocalidad());

        //criteria.addOrderAsc(Bundle_.codigo); //TODO (epa): Ordenar por codigo y evitar grupo by por uso de funcion de agrupacion en la paginacion
        
        return findByCriteria(criteria, pagination);
	}
	
	@Override
	public Bundle getMessageResource(String codigo,
			HibernateBundle.Localidad localidad) {
		GenericCriteria<HibernateBundle> criteria = GenericCriteria.forClass(HibernateBundle.class);
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
        return load(id, HibernateBundle.class);
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
    public List<HibernateParametros> getParametrosList(Parametros param) {
        GenericCriteria<HibernateParametros> criteria = GenericCriteria.forClass(HibernateParametros.class);
        if(param != null){
            criteria.addLike(Parametros_.grupo, param.getGrupo());
            criteria.addLike(Parametros_.clave, param.getClave());
        }        
        return findByCriteria(criteria);
    }

    @Override
    public HibernateParametros saveParametros(HibernateParametros param) throws AdministracionException{
        Date fecha = new Date();
        if(EntityUtils.isPersistent(param)){
            param.setModificado(fecha);
        }else{
            param.setCreado(fecha);
            param.setModificado(fecha);
            param.setEstado(HibernateEntidad.Estado.ACTIVO);
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
    public HibernateConfiguracion getConfiguracion() {                
        HibernateConfiguracion conf = get(new Long(1), HibernateConfiguracion.class);       
        conf.setParametros(buildParametrosHash(getParametrosList(null)));
        return conf;
    }

    @Override
    public HibernateConfiguracion saveConfiguracion(HibernateConfiguracion configuracion) throws AdministracionException{                
                
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
	private HashMap buildParametrosHash(List<HibernateParametros> parametrosList){
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
    public List<HibernateOpcion> findOpcionList(Opcion opcion, Pagination pagination) {
        GenericCriteria<HibernateOpcion> criteria = GenericCriteria.forClass(HibernateOpcion.class);
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
    public HibernateOpcion createOpcion() {
        HibernateOpcion opcion = new HibernateOpcion();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public HibernateOpcion saveOpcion(HibernateOpcion opcion) {
        if (!EntityUtils.isPersistent(opcion)) {
            opcion.setEstado(HibernateEntidad.Estado.ACTIVO);
        }
        opcion = saveOrMerge(opcion);
        putSuccess("opcion.success.save",opcion.getId());
        return opcion;
    }

    @Override
    public void deleteOpcion(HibernateOpcion opcion) {
        Long id = opcion.getId();
        delete(opcion);
        putSuccess("opcion.success.delete",id);
    }

    @Override
    public Opcion getOpcion(Long id) {
    	GenericCriteria<HibernateOpcion> criteria = GenericCriteria.forClass(HibernateOpcion.class);
    	criteria.addEquals("estado", HibernateEntidad.Estado.ACTIVO);
        criteria.addAliasedJoins(Auditoria_.creador);
        criteria.addAliasedLeftJoins(Auditoria_.modificador);
        return findFirstByCriteria(criteria);
    }

    @Override
    public List<HibernateOpcion> getOpcionPadreList() {
        GenericCriteria<HibernateOpcion> criteria = GenericCriteria.forClass(HibernateOpcion.class);
        criteria.addEquals(Opcion_.estado, HibernateEntidad.Estado.ACTIVO);
        criteria.addIsNull(Opcion_.padre);
        criteria.addOrderAsc(Opcion_.padre);
        return findByCriteria(criteria);
    }
    
    //
    // Objeto
    //
    
    @Override
    public List<HibernateObjeto> findObjetoList(Objeto objeto, Pagination pagination) {
        GenericCriteria<HibernateObjeto> criteria = GenericCriteria.forClass(HibernateObjeto.class);
        criteria.addEquals("estado", HibernateEntidad.Estado.ACTIVO);
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
    public HibernateObjeto createObjeto() {
        HibernateObjeto opcion = new HibernateObjeto();
        opcion.setEstado(Usuario.Estado.INACTIVO);
        return opcion;
    }

    @Override
    public HibernateObjeto saveObjeto(HibernateObjeto objeto) {
        if (!EntityUtils.isPersistent(objeto)) {
            objeto.setEstado(HibernateEntidad.Estado.ACTIVO);
        }
        objeto = saveOrMerge(objeto);
        putSuccess("objeto.success.save",objeto.getId());
        return objeto;
    }

    @Override
    public void deleteObjeto(HibernateObjeto objeto) {
        Long id = objeto.getId();
    	delete(objeto);
    	putSuccess("objeto.success.delete",id);
    }

    @Override
    public Objeto getObjeto(Long id) {
        return get(id, HibernateObjeto.class);
    }
}
