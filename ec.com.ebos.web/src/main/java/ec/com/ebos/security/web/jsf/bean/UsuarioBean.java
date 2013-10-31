package ec.com.ebos.security.web.jsf.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.master.model.EmpresaPersona;
import ec.com.ebos.master.model.hibernate.HibernateEmpresaPersona;
import ec.com.ebos.master.model.hibernate.HibernatePersona;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Rol;
import ec.com.ebos.security.model.hibernate.HibernateRol;
import ec.com.ebos.security.model.hibernate.HibernateUsuario;
import ec.com.ebos.security.model.hibernate.HibernateUsuarioRol;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Component
@ManagedBean(name = UsuarioBean.BEAN_NAME)
@ViewScoped
public class UsuarioBean extends SecurityBean<HibernateUsuario> {
    
	private static final long serialVersionUID = 3205546315013216597L;
	
	public static final String BEAN_NAME = "usuarioBean";

    @Override
    public void getInit() {
        // Para busquedas
        entitySearch = new HibernateUsuario();
        EmpresaPersona empresaPersona = new HibernateEmpresaPersona();
        empresaPersona.setPersona(new HibernatePersona());
        entitySearch.setEmpresaPersona(empresaPersona);
        entitySearch.setEstado(Entidad.Estado.ACTIVO);    
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/security/usuario/finder.xhtml";
        TARGET_NEW_ID = "crearUsuario";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<HibernateUsuario> loadDataTableCollection(HibernateUsuario usuario, Pagination pagination) {
        return securityS.findUsuarioList(usuario, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = securityS.createUsuario();
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
        activeEntity = securityS.saveUsuario(activeEntity);                
    }

    @Override
    public void eliminar() {
        securityS.saveUsuario(activeEntity);                
    }            
    
    ///////////////////////// DATALIST /////////////////////////
    
    @Setter
    private List<HibernateUsuarioRol> usuarioRolList = new ArrayList<HibernateUsuarioRol>();
    
    @Setter
    private List<HibernateRol> rolList = new ArrayList<HibernateRol>();
    
    @Getter @Setter
    private HibernateUsuarioRol[] selectedUsuarioRolList;
    
    @Getter @Setter
    private Rol selectedRol;    

    public void addUsuarioRol(){        
        securityS.generateUsuarioRol(activeEntity, selectedRol);
        usuarioRolList.clear();
    }
    
    public void saveUsuarioRolList(){                
        securityS.saveUsuarioRolList(Arrays.asList(selectedUsuarioRolList));                
        usuarioRolList.clear();
    }
    
    public void deleteUsuarioRolList(){
        List<HibernateUsuarioRol> list = Arrays.asList(selectedUsuarioRolList);        
        securityS.deleteUsuarioRolList(list);        
        usuarioRolList.removeAll(list);
        usuarioRolList.clear();
    }

    //
    // Getters
    //
    
    public List<HibernateRol> getRolList() {
        if(rolList.isEmpty()){
            rolList = securityS.findRolList(null, null);
        }
        return rolList;
    }

    public List<HibernateUsuarioRol> getUsuarioRolList() {
        if(usuarioRolList.isEmpty()){
            usuarioRolList = securityS.getUsuarioRolList(activeEntity);
        }
        return usuarioRolList;
    }
    
}
