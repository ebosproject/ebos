package ec.com.ebos.master.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import ec.com.ebos.master.model.Bundle;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-24
 */
@ManagedBean(name = BundleBean.BEAN_NAME)
@ViewScoped
public class BundleBean extends MasterBean<Bundle> {
    
	private static final long serialVersionUID = -8387498705417931654L;
	
	@Getter
	public static final String BEAN_NAME = "bundleBean";

	@Override
    public void getInit() {
        // Para busquedas
        entitySearch = new Bundle();
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
        TARGET_ID = "/modules/master/bundle/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Bundle> loadDataTableCollection(Bundle bundle, Pagination pagination) {
        return masterS.findBundleList(bundle, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = masterS.createBundle();
    }

    @Override
    public void editar() {        
    	//activeEntity = masterS.loadBundle(activeEntity.getId());
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = masterS.saveBundle(activeEntity);                
    }

    @Override
    public void eliminar() {
        masterS.deleteBundle(activeEntity);                
    }            
    
    //////////////////////// DATALIST ///////////////////////////////
    
    @Getter
    protected final List<Bundle.Localidad> localidadList = new ArrayList<Bundle.Localidad>(Bundle.Localidad.LIST);
    
}
