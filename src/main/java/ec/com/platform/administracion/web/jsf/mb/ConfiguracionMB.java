package ec.com.platform.administracion.web.jsf.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.com.platform.administracion.model.Configuracion;
import ec.com.platform.util.GenericUtils;


/**
 *
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "configuracionMB")
@SessionScoped
public class ConfiguracionMB extends GenericAdministracionMB<Configuracion>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4050902280012061901L;
	
	private Configuracion configuracionActual;
    
    @Override
    public void getInit() {
        entitySearch = new Configuracion();        
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear(false);
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar(false);        
        setHabilitaCerrar(false);
        
        if (GenericUtils.isPersistent(configuracionActual)) {
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "administracion/configuracion/contenedor.jsf";
        TARGET_NEW_ID = "editarConfiguracion";        
    }
    
    //////////////////// ACCIONES ////////////////////
    
    public void actualizar() {
        configuracionActual = administracionS.obtenerConfiguracion();
        habilitaControles();
    }

    public void guardar() {
        configuracionActual = administracionS.guardarConfiguracion(configuracionActual); 
        habilitaControles();
    }

    
    //Setters and Getters

    public Configuracion getConfiguracionActual() {
        if(!GenericUtils.isPersistent(configuracionActual)){
            configuracionActual = administracionS.obtenerConfiguracion();
        }            
        return configuracionActual;
    }

    public void setConfiguracionActual(Configuracion configuracionActual) {
        this.configuracionActual = configuracionActual;
    }
    
}
