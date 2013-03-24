package ec.com.platform.seguridad.web.jsf.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.app.model.EmpresaPersona;
import ec.com.platform.app.model.Persona;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.seguridad.model.Rol;
import ec.com.platform.seguridad.model.Usuario;
import ec.com.platform.seguridad.model.UsuarioRol;
import ec.com.platform.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB extends GenericSeguridadMB<Usuario> {
    
	private static final long serialVersionUID = 3205546315013216597L;

    @Override
    public void getInit() {
        // Para busquedas
        entitySearch = new Usuario();
        EmpresaPersona empresaPersona = new EmpresaPersona();
        empresaPersona.setPersona(new Persona());
        entitySearch.setEmpresaPersona(empresaPersona);
        entitySearch.setEstado(Generic.Estado.ACTIVO);    
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "seguridad/usuario/index.jsf";
        TARGET_NEW_ID = "crearUsuario";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Usuario> loadDataTableCollection(Usuario usuario, Paginacion paginacion) {
        return seguridadS.obtenerUsuarioList(usuario, paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = seguridadS.obtenerUsuarioNuevo();
        usuarioRolList.clear();
        rolList.clear();        
    }

    @Override
    public void editar() {        
        usuarioRolList.clear();
        rolList.clear();
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = seguridadS.guardarUsuario(activeEntity);                
    }

    @Override
    public void eliminar() {
        seguridadS.eliminarUsuario(activeEntity);                
    }            
    
    ///////////////////////// DATALIST /////////////////////////
    
    @Setter
    private List<UsuarioRol> usuarioRolList = new ArrayList<UsuarioRol>();
    
    @Setter
    private List<Rol> rolList = new ArrayList<Rol>();
    
    @Getter @Setter
    private UsuarioRol[] selectedUsuarioRolList;
    
    @Getter @Setter
    private Rol selectedRol;    

    public void agregarUsuarioRol(){        
        seguridadS.generarUsuarioRol(activeEntity, selectedRol);
        usuarioRolList.clear();
    }
    
    public void guardarUsuarioRolList(){                
        seguridadS.guardarUsuarioRolList(Arrays.asList(selectedUsuarioRolList));                
        usuarioRolList.clear();
    }
    
    public void eliminarUsuarioRolList(){
        List<UsuarioRol> list = Arrays.asList(selectedUsuarioRolList);        
        seguridadS.eliminarUsuarioRolList(list);        
        usuarioRolList.removeAll(list);
        usuarioRolList.clear();
    }

    //
    // Getters
    //
    
    public List<Rol> getRolList() {
        if(rolList.isEmpty()){
            rolList = seguridadS.obtenerRolList(null, null);
        }
        return rolList;
    }

    public List<UsuarioRol> getUsuarioRolList() {
        if(usuarioRolList.isEmpty()){
            usuarioRolList = seguridadS.obtenerUsuarioRolList(activeEntity);
        }
        return usuarioRolList;
    }
    
}
