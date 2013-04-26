package ec.com.ebos.admin.web.jsf.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.com.ebos.admin.model.Configuracion;
import ec.com.ebos.util.GenericUtils;


/**
 *
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = ConfiguracionBean.BEAN_NAME)
@SessionScoped
public class ConfiguracionBean extends AdministracionBean<Configuracion>{

	private static final long serialVersionUID = -4050902280012061901L;
	
	public static final String BEAN_NAME = "configuracionBean";
	
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
        if(!GenericUtils.isPersistent(configuracionActual)){
            configuracionActual = administracionS.getConfiguracion();
        }            
        return configuracionActual;
    }

    public void setConfiguracionActual(Configuracion configuracionActual) {
        this.configuracionActual = configuracionActual;
    }
    
}
