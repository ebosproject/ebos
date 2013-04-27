package ec.com.ebos.appl.web.jsf.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-31
 */
@ManagedBean(name = PropiedadBean.BEAN_NAME)
@SessionScoped
public class PropiedadBean extends ApplicationBean<Propiedad> {
    
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
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/appl/propiedad/index.jsf";
        TARGET_NEW_ID = "crearPropiedad";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Propiedad> loadDataTableCollection(Propiedad propiedad, Pagination pagination) {
        return applS.findPropiedadList(propiedad, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = applS.createPropiedad();
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
        activeEntity = applS.savePropiedad(activeEntity);                
    }

    @Override
    public void eliminar() {
        applS.deletePropiedad(activeEntity);                
    }            
    
}
