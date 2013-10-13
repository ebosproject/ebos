package ec.com.ebos.master.web.jsf.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-31
 */
@Component
@ManagedBean(name = PropiedadBean.BEAN_NAME)
@SessionScoped
public class PropiedadBean extends MasterBean<Propiedad> {
    
	private static final long serialVersionUID = -8387498705417931654L;
	
	public static final String BEAN_NAME = "propiedadBean";

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
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/master/propiedad/index.jsf";
        TARGET_NEW_ID = "crearPropiedad";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Propiedad> loadDataTableCollection(Propiedad propiedad, Pagination pagination) {
        return masterS.findPropiedadList(propiedad, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = masterS.createPropiedad();
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
        activeEntity = masterS.savePropiedad(activeEntity);                
    }

    @Override
    public void eliminar() {
        masterS.deletePropiedad(activeEntity);                
    }            
    
}
