package ec.com.platform.seguridad.web.jsf.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.seguridad.model.Opcion;
import ec.com.platform.seguridad.model.Rol;
import ec.com.platform.seguridad.model.RolOpcion;
import ec.com.platform.util.GenericUtils;

/**
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "rolMB")
@SessionScoped
public class RolMB extends GenericSeguridadMB<Rol> {
    
	private static final long serialVersionUID = 5900425430487867980L;

    @Override
    public void getInit() {
        // para busquedas
        entitySearch = new Rol();
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
        TARGET_ID = "seguridad/rol/index.jsf";
        TARGET_NEW_ID = "crearRol";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Rol> loadDataTableCollection(Rol rol, Paginacion paginacion) {
        return seguridadS.obtenerRolList(rol, paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = seguridadS.obtenerRolNuevo();
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
        activeEntity = seguridadS.guardarRol(activeEntity);
    }

    @Override
    public void eliminar() {
        seguridadS.eliminarRol(activeEntity);    
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
        seguridadS.generarRolOpcion(activeEntity, selectedOpcion);
        rolOpcionList.clear();
    }
    
    public void guardarRolOpcionList(){                
        seguridadS.guardarRolOpcionList(Arrays.asList(selectedRolOpcionList));
        selectedRolOpcionList = null;
        rolOpcionList.clear();
    }
    
    public void eliminarRolOpcionList(){ 
        List<RolOpcion> list = Arrays.asList(selectedRolOpcionList);
        seguridadS.eliminarRolOpcionList(list);        
        rolOpcionList.clear();
    }
    
    //GETTERS AND SETTERS

    public List<RolOpcion> getRolOpcionList() {
        if(rolOpcionList.isEmpty()){
            rolOpcionList = seguridadS.obtenerRolOpcionList(activeEntity);
        }
        return rolOpcionList;
    }

    public List<Opcion> getOpcionList() {
        if(opcionList.isEmpty()){
            opcionList = seguridadS.obtenerOpcionList(null, new Paginacion()); // TODO (epa): Crear metodo sin paginacion
        }        
        return opcionList;
    }

}
