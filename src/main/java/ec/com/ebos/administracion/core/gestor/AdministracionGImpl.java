package ec.com.ebos.administracion.core.gestor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.administracion.core.exception.AdministracionException;
import ec.com.ebos.administracion.model.Configuracion;
import ec.com.ebos.administracion.model.Parametros;
import ec.com.ebos.administracion.model.Parametros_;
import ec.com.ebos.generic.core.gestor.GenericGImpl;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Repository("administracionG")
public class AdministracionGImpl extends GenericGImpl<Object, AdministracionException> implements AdministracionG{

	private static final long serialVersionUID = -5387275517395137673L;
	
	//
    //Parametros
    //
	
    @Override
    public List<Parametros> obtenerParametrosList(Parametros param) {
        GenericCriteria<Parametros> criteria = GenericCriteria.forClass(Parametros.class);
        if(param != null){
            criteria.addLike(Parametros_.grupo, param.getGrupo());
            criteria.addLike(Parametros_.clave, param.getClave());
        }        
        return findByCriteria(criteria);
    }

    @Override
    public Parametros guardarParametros(Parametros param) throws AdministracionException{
        Date fecha = new Date();
        if(GenericUtils.isPersistent(param)){
            param.setFechaModificacion(fecha);
        }else{
            param.setFechaCreacion(fecha);
            param.setFechaModificacion(fecha);
            param.setEstado(Entidad.Estado.ACTIVO);
        }
        param = saveOrUpdate(param);
        
        //Actualiza lista de parametros en el bean de aplicacion
        //getApp().getConfiguracion().setParametros(buildParametrosHash(obtenerParametrosList(null)));
        //TODO (epa): refrescar parametros en Application Spring Bean
        putSuccess("Parametro "+param.getId()+" guardado correctamente");
        return param;
    }

    
    //
    //Configuracion
    //
    
    @SuppressWarnings("unchecked")
	@Override
    public Configuracion obtenerConfiguracion() {                
        Configuracion conf = findById(new Long(1), Configuracion.class);       
        conf.setParametros(buildParametrosHash(obtenerParametrosList(null)));
        return conf;
    }

    @Override
    public Configuracion guardarConfiguracion(Configuracion configuracion) throws AdministracionException{                
                
        if(configuracion.isEnviarSmsPrx() && configuracion.isEnviarSmsGatewayPrx()){            
            configuracion.setEnviarSmsGatewayPrx(false);
            putWarning("Sólo se puede habilitar una opción de envío de mensajes, por default es Sms");            
        }        
        configuracion = update(configuracion);      
        
        //Actualiza las configuraciones del sistema en el bean de aplicacion
        //getApp().setConfiguracion(configuracion);
        //TODO (epa): refrescar parametros en Application Spring Bean
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
