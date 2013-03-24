package ec.com.platform.seguridad.web.jsf.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.seguridad.model.Objeto;
import ec.com.platform.util.GenericUtils;

/**
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "objetoMB")
@SessionScoped
public class ObjetoMB extends GenericSeguridadMB<Objeto> {
    
	private static final long serialVersionUID = 833763360386716739L;

	@Override
    public void getInit() {
        entitySearch = new Objeto();
        entitySearch.setEstado(Generic.Estado.ACTIVO);
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
    protected List<Objeto> loadDataTableCollection(Objeto objeto, Paginacion paginacion) {
        return seguridadS.obtenerObjetoList(objeto,paginacion);
    }
        
    ////////////////////////// ACCIONES ////////////////////////
    
    @Override
    public void crear() {
        activeEntity = seguridadS.obtenerObjetoNuevo();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = seguridadS.guardarObjeto(activeEntity);        
    }

    @Override
    public void eliminar() {
        seguridadS.eliminarObjeto(activeEntity);        
    }
    
	///////////////////////////LISTS ///////////////////////////
	    
    @Getter
	private List<Objeto.TipoObjeto> tipoObjetoList = new ArrayList<Objeto.TipoObjeto>(Objeto.TipoObjeto.LIST);

}
