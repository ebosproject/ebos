package ec.com.ebos.admin.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;

import org.springframework.stereotype.Component;

import ec.com.ebos.admin.model.Objeto;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Component
@ManagedBean(name = ObjetoBean.BEAN_NAME)
//@SessionScoped
@ViewScoped
public class ObjetoBean extends AdministracionBean<Objeto> {
    
	private static final long serialVersionUID = 833763360386716739L;
	
	public static final String BEAN_NAME = "objetoBean";

	@Override
    public void getInit() {
        //entitySearch = new Objeto(); TODO (epa): instanciar
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
        entitySearch.setTipo(Objeto.TipoObjeto.BEAN);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar(); //TODO (epa):Implementar Seguridad
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/admin/objeto/index.jsf";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Objeto> loadDataTableCollection(Objeto objeto, Pagination pagination) {
        return administracionS.findObjetoList(objeto,pagination);
    }
        
    ////////////////////////// ACCIONES ////////////////////////
    
    @Override
    public void crear() {
        activeEntity = administracionS.createObjeto();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = administracionS.saveObjeto(activeEntity);        
    }

    @Override
    public void eliminar() {
        administracionS.deleteObjeto(activeEntity);        
    }
    
	///////////////////////////LISTS ///////////////////////////
	    
    @Getter
	private List<Objeto.TipoObjeto> tipoObjetoList = new ArrayList<Objeto.TipoObjeto>(Objeto.TipoObjeto.LIST);

}
