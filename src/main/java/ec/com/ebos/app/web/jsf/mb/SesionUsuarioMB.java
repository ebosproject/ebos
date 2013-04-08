package ec.com.ebos.app.web.jsf.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.seguridad.core.servicio.SeguridadS;
import ec.com.ebos.seguridad.model.RolOpcion;
import ec.com.ebos.seguridad.model.Usuario;
import ec.com.ebos.util.Constantes;

/**
 * Bean para datos de sesion del usuario
 *
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "sesionUsuario")
@SessionScoped
public class SesionUsuarioMB implements Serializable{

	private static final long serialVersionUID = -4715010869007043390L;

	public static final String BEAN_NAME = "sesionUsuario";
	
    @Getter @Setter
    @ManagedProperty(value = "#{seguridadS}")
    protected SeguridadS seguridadS;

    @Getter @Setter
    @ManagedProperty(value = "#{adminMB}")
    protected AdminMB admin;
    
	private String tema = null;
    
    @Getter @Setter
    private Usuario usuario;
           
    @Getter @Setter
    private boolean login;
    
    @Getter @Setter
    private List<RolOpcion> rolOpcionList;
    
    @PostConstruct
    public void postConstruct() {
        usuario = new Usuario();
        login = false;
        rolOpcionList = new ArrayList<RolOpcion>();
        defineSessionTimeout();
    }
    
    @PreDestroy
    public void preDestroy() {
        admin.removeSession(this);
    }
    
    public void iniciarSesion(){                
        login = seguridadS.iniciarSesion(this);
        usuario.setPassword(null);
        defineSessionTimeout();
    }
    
    public void cerrarSesion() {                
        this.rolOpcionList.clear();        
        this.login = false;
        // Invalida la sesion web actual, crear una nueva, y asocia el bean de sesion del usuario
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // invalida la sesion web actual, crear una nueva, y asocia el bean de sesion del usuario
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute(BEAN_NAME, this);
        defineSessionTimeout();
        
    }
    
    public void cambiarPassword(){
        seguridadS.cambiarPassword(usuario);
        usuario.setPassword(null);
        usuario.setNewpassword(null);
        usuario.setConfpassword(null);
    }
    
    // Permisos de Seguridad
    public static final int EDITAR_ACTION_ID = 1;
    public static final int CREAR_ACTION_ID = 2;
    public static final int ELIMINAR_ACTION_ID = 3;
    public static final int EXPORTAR_ACTION_ID = 4;
    
    
    public boolean verificaAcceso(String target, int accion){
        boolean flag = false;
        for(RolOpcion rolOpcion : rolOpcionList){            
            if(rolOpcion.getOpcion().getTarget().equals(target)){
                switch(accion){
                    case EDITAR_ACTION_ID: flag = rolOpcion.isEditar(); break;
                    case CREAR_ACTION_ID: flag = rolOpcion.isCrear(); break;
                    case ELIMINAR_ACTION_ID: flag = rolOpcion.isEliminar(); break;                       
                    case EXPORTAR_ACTION_ID: flag = rolOpcion.isExportar(); break;
                }
                break;
            }
        }
        return flag;
    }
    
    /**
    * Define el timeout de la sesion actual
    */
    private void defineSessionTimeout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(login ? Constantes.SESSION_TIMEOUT : Constantes.SESSION_TIMEOUT_LOGOUT);
        }
    }
    
//
//    public List<RolOpcion> getRolOpcionList() {
//        return rolOpcionList == null ? new ArrayList<RolOpcion>() : rolOpcionList;
//    }
    
	public String getTema() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (params.containsKey("tema")) {
			tema = params.get("tema");
		} else {
			tema = usuario.getTema() != null ? usuario.getTema() : Constantes.tema;
		}
		return tema;
	}
	
	public void guardarTema(String tema){
		this.tema = tema;
		usuario.setTema(tema);
		seguridadS.guardarPreferenciasUsuario(usuario);
	}
    
}