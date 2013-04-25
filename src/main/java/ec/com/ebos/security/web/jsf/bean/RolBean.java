package ec.com.ebos.security.web.jsf.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.util.GenericUtils;

/**
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = RolBean.BEAN_NAME)
//@SessionScoped
@ViewScoped
public class RolBean extends SecurityBean<Rol> {
    
	private static final long serialVersionUID = 5900425430487867980L;
	
	public static final String BEAN_NAME = "rolBean";

    @Override
    public void getInit() {
        // para busquedas
        entitySearch = new Rol();
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
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
        TARGET_ID = "seguridad/rol/index.jsf";
        TARGET_NEW_ID = "crearRol";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Rol> loadDataTableCollection(Rol rol, Pagination paginacion) {
        return securityS.obtenerRolList(rol, paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = securityS.obtenerRolNuevo();
        opcionList.clear();
        rolOpcionList.clear();
    }

    @Override
    public void editar() {        
        opcionList.clear();
        rolOpcionList.clear();
    }
    
    @Override
    public void actualizar(){                        
        rolOpcionList.clear();    
    }

    @Override
    public void guardar() {
        activeEntity = securityS.guardarRol(activeEntity);
    }

    @Override
    public void eliminar() {
        securityS.eliminarRol(activeEntity);    
    }            
    
    ///////////////////////// DATALIST /////////////////////////
    
    @Setter
    private List<RolOpcion> rolOpcionList = new ArrayList<RolOpcion>();
    
    @Setter
    private List<Opcion> opcionList = new ArrayList<Opcion>();
    
    @Getter @Setter
    private RolOpcion[] selectedRolOpcionList;
    
    @Getter @Setter
    private Opcion selectedOpcion;    
    
    public void agregarRolOpcion(){        
        securityS.generarRolOpcion(activeEntity, selectedOpcion);
        rolOpcionList.clear();
    }
    
    public void guardarRolOpcionList(){                
        securityS.guardarRolOpcionList(Arrays.asList(selectedRolOpcionList));
        selectedRolOpcionList = null;
        rolOpcionList.clear();
    }
    
    public void eliminarRolOpcionList(){ 
        List<RolOpcion> list = Arrays.asList(selectedRolOpcionList);
        securityS.eliminarRolOpcionList(list);        
        rolOpcionList.clear();
    }
    
    //GETTERS AND SETTERS

    public List<RolOpcion> getRolOpcionList() {
        if(rolOpcionList.isEmpty()){
            rolOpcionList = securityS.obtenerRolOpcionList(activeEntity);
        }
        return rolOpcionList;
    }

    public List<Opcion> getOpcionList() {
        if(opcionList.isEmpty()){
            opcionList = securityS.obtenerOpcionList(null, new Pagination()); // TODO (epa): Crear metodo sin paginacion
        }        
        return opcionList;
    }

}
