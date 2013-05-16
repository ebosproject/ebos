package ec.com.ebos.admin.core.process;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.admin.core.exception.AdministracionException;
import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.admin.model.Parametros;
import ec.com.ebos.admin.model.field.Parametros_;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Repository("adminP")
public class AdministracionPImpl extends RootPImpl<Object, AdministracionException> implements AdministracionP{

	private static final long serialVersionUID = -5387275517395137673L;
	
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
        putSuccess("Parametro "+param.getId()+" guardado correctamente");
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
            putWarning("Sólo se puede habilitar una opción de envío de mensajes, por default es Sms");            
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
    
}
