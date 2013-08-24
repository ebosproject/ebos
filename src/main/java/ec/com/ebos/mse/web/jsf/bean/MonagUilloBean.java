package ec.com.ebos.mse.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-08-23
 */
@ManagedBean(name = MonagUilloBean.BEAN_NAME)
@ViewScoped
public class MonagUilloBean extends MseBean<Monaguillo> {
    	
	private static final long serialVersionUID = 1936050047220453830L;
	
	@Getter
	public static final String BEAN_NAME = "monagilloBean";

	@Override
    public void getInit() {
        // Para busquedas
        entitySearch = new Monaguillo();
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
        TARGET_ID = "/modules/mse/monagillo/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Monaguillo> loadDataTableCollection(Monaguillo cuentaContable, Pagination pagination) {
        //return contaS.findTipoCuentaList(cuentaContable, pagination);
    	return new ArrayList<Monaguillo>();
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = mseS.createMonagillo();
    }

    @Override
    public void editar() {        
    	//activeEntity = mseS.loadMonagillo(activeEntity.getId());
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = mseS.saveMonagillo(activeEntity);                
    }

    @Override
    public void eliminar() {
    	mseS.deleteMonagillo(activeEntity);                
    }            
    
    //////////////////////// LISTS ///////////////////////////////
    
}
