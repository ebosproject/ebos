package ec.com.ebos.web.master.jsf.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.BeanScopes;
import ec.com.ebos.core.master.model.Propiedad;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-31
 */
@Component(PropiedadBean.BEAN_NAME)
@Scope(BeanScopes.SESSION)
//@ManagedBean(name = PropiedadBean.BEAN_NAME)
//@SessionScoped
public class PropiedadBean extends MasterBean<Propiedad> {
    
	private static final long serialVersionUID = -8387498705417931654L;
	
	public static final String BEAN_NAME = "propiedadBean";

	@Override
    public void getInit() {
        entitySearch = masterS.getInstancePropiedad();
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
