package ec.com.ebos.web.admin.jsf.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import ec.com.ebos.core.admin.model.Configuracion;
import ec.com.ebos.core.util.EntityUtils;


/**
 *
 * @author Eduardo Plua Alay
 */
@Component
@ManagedBean(name = ConfiguracionBean.BEAN_NAME)
@SessionScoped
public class ConfiguracionBean extends AdministracionBean<Configuracion>{

	private static final long serialVersionUID = -4050902280012061901L;
	
	public static final String BEAN_NAME = "configuracionBean";
	
	private Configuracion configuracionActual;
    
    @Override
    public void getInit() {
        entitySearch = administracionS.getInstanceConfiguracion();       
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear(false);
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar(false);        
        setHabilitaCerrar(false);
        
        if (EntityUtils.isPersistent(configuracionActual)) {
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/admin/configuracion/contenedor.jsf";
        TARGET_NEW_ID = "editarConfiguracion";        
    }
    
    //////////////////// ACCIONES ////////////////////
    
    public void actualizar() {
        configuracionActual = administracionS.getConfiguracion();
        habilitaControles();
    }

    public void guardar() {
        configuracionActual = administracionS.saveConfiguracion(configuracionActual); 
        habilitaControles();
    }

    
    //Setters and Getters

    public Configuracion getConfiguracionActual() {
        if(!EntityUtils.isPersistent(configuracionActual)){
            configuracionActual = administracionS.getConfiguracion();
        }            
        return configuracionActual;
    }

    public void setConfiguracionActual(Configuracion configuracionActual) {
        this.configuracionActual = configuracionActual;
    }
    
}
