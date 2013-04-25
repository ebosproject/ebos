package ec.com.ebos.security.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Objeto;
import ec.com.ebos.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = ObjetoBean.BEAN_NAME)
//@SessionScoped
@ViewScoped
public class ObjetoBean extends SecurityBean<Objeto> {
    
	private static final long serialVersionUID = 833763360386716739L;
	
	public static final String BEAN_NAME = "objetoBean";

	@Override
    public void getInit() {
        entitySearch = new Objeto();
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
        entitySearch.setTipo(Objeto.TipoObjeto.BEAN);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar(); //TODO (epa):Implementar Seguridad
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "seguridad/objeto/index.jsf";
        TARGET_NEW_ID = "crearObjeto";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Objeto> loadDataTableCollection(Objeto objeto, Pagination paginacion) {
        return securityS.obtenerObjetoList(objeto,paginacion);
    }
        
    ////////////////////////// ACCIONES ////////////////////////
    
    @Override
    public void crear() {
        activeEntity = securityS.obtenerObjetoNuevo();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = securityS.guardarObjeto(activeEntity);        
    }

    @Override
    public void eliminar() {
        securityS.eliminarObjeto(activeEntity);        
    }
    
	///////////////////////////LISTS ///////////////////////////
	    
    @Getter
	private List<Objeto.TipoObjeto> tipoObjetoList = new ArrayList<Objeto.TipoObjeto>(Objeto.TipoObjeto.LIST);

}
