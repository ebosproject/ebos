package ec.com.ebos.app.web.jsf.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
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
@ManagedBean(name = SessionMB.BEAN_NAME)
@SessionScoped
public class SessionMB implements Serializable{

	private static final long serialVersionUID = 502301922012194259L;
	
	public static final String BEAN_NAME = "sessionMB";
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
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
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        
        // Invalida la sesion web actual, crear una nueva, y asocia el bean de sesion del usuario
        HttpSession session = (HttpSession) extContext.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = (HttpSession) extContext.getSession(true);
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
    public static final int EDIT_ACTION_ID = 1;
    public static final int CREATE_ACTION_ID = 2;
    public static final int DELETE_ACTION_ID = 3;
    public static final int EXPORT_ACTION_ID = 4;
    
    
    public boolean verificaAcceso(String target, int accion){
        boolean flag = false;
        for(RolOpcion rolOpcion : rolOpcionList){            
            if(rolOpcion.getOpcion().getTarget().equals(target)){
                switch(accion){
                    case EDIT_ACTION_ID: flag = rolOpcion.isEditar(); break;
                    case CREATE_ACTION_ID: flag = rolOpcion.isCrear(); break;
                    case DELETE_ACTION_ID: flag = rolOpcion.isEliminar(); break;                       
                    case EXPORT_ACTION_ID: flag = rolOpcion.isExportar(); break;
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
    	ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
    	extContext.setSessionMaxInactiveInterval(login ? Constantes.SESSION_TIMEOUT_LOGON : Constantes.SESSION_TIMEOUT_LOGOUT);
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
			tema = usuario.getTema() != null ? usuario.getTema() : Constantes.THEME;
		}
		return tema;
	}
	
	public void guardarTema(String tema){
		this.tema = tema;
		usuario.setTema(tema);
		seguridadS.guardarPreferenciasUsuario(usuario);
	}
	
    public void putMessage(FacesMessage.Severity severity, String msg){
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void putSuccess(String msg) {        
        putMessage(FacesMessage.SEVERITY_INFO, msg);
    }

    public void putWarning(String msg) {
        putMessage(FacesMessage.SEVERITY_WARN, msg);        
    }

    public void putError(String msg) {
        putMessage(FacesMessage.SEVERITY_ERROR, msg);        
    }
    
    public void putFatal(String msg) {
        putMessage(FacesMessage.SEVERITY_FATAL, msg);        
    }
    
}