package ec.com.ebos.app.web.jsf.mb;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.com.ebos.app.model.Propiedad;
import ec.com.ebos.fwk.crud.Paginacion;
import ec.com.ebos.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-31
 */
@ManagedBean(name = "propiedadMB")
@SessionScoped
public class PropiedadMB extends GenericAppMB<Propiedad> {
    
	private static final long serialVersionUID = -8387498705417931654L;

	@Override
    public void getInit() {
        // Para busquedas
        entitySearch = new Propiedad();
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "app/propiedad/index.jsf";
        TARGET_NEW_ID = "crearPropiedad";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Propiedad> loadDataTableCollection(Propiedad propiedad, Paginacion paginacion) {
        return appS.obtenerPropiedadList(propiedad, paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = appS.obtenerPropiedadNuevo();
    }

    @Override
    public void editar() {        
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = appS.guardarPropiedad(activeEntity);                
    }

    @Override
    public void eliminar() {
        appS.eliminarPropiedad(activeEntity);                
    }            
    
}
