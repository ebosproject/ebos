package ec.com.ebos.master.web.jsf.bean;

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
import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.root.resources.RootMensajes;
import ec.com.ebos.security.core.service.SecurityS;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.util.Constantes;

/**
 * Bean para datos de sesion del usuario
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@ManagedBean(name = SessionBean.BEAN_NAME)
@SessionScoped
public class SessionBean implements Serializable{

	private static final long serialVersionUID = 502301922012194259L;
	
	/**
	 * Nombre del bean para {@link SessionBean}
	 */
	public static final String BEAN_NAME = "sessionBean";
	
	/**
	 * Nombre del Bean como EL a ser referenciado para la inyeccion de una instancia de {@link SessionBean} en otro ManagedBean
	 */
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
    @Getter @Setter
    @ManagedProperty(value = SecurityS.EL_BEAN_NAME)
    protected SecurityS securityS;

    @Getter @Setter
    @ManagedProperty(value = "#{adminMB}")
    protected AdminBean admin;
    
	private String tema = null;
    
    @Getter @Setter
    private Usuario usuario;
           
    @Getter @Setter
    private Organizacion empresa;

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
        login = securityS.authLogin(this);
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
        securityS.changePassword(usuario);
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
		securityS.saveUserPreferences(usuario);
	}
	
    private void putMessage(FacesMessage.Severity severity, String keySummary, String detail, Object... args){
    	String summary = RootMensajes.getString(keySummary, args);
		putMessage(severity, summary, (detail != null && !detail.isEmpty()) ? detail : "");
    }
    
    private void putMessage(FacesMessage.Severity severity, String summary, String detail){
    	FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void putSuccess(String keySummary, String detail, Object... args) {        
        putMessage(FacesMessage.SEVERITY_INFO, keySummary, detail, args);
    }
    
    public void putSuccess(String summary) {        
        putMessage(FacesMessage.SEVERITY_INFO, summary, "");
    }

    public void putWarn(String keySummary, String detail, Object... args) {
        putMessage(FacesMessage.SEVERITY_WARN, keySummary, detail, args);        
    }
    
    public void putWarning(String summary) {
        putMessage(FacesMessage.SEVERITY_WARN, summary, "");        
    }

    public void putError(String keySummary, String detail, Object... args) {
        putMessage(FacesMessage.SEVERITY_ERROR, keySummary, detail, args);        
    }
    
    public void putError(String summary) {
        putMessage(FacesMessage.SEVERITY_ERROR, summary, "");        
    }
    
    public void putFatal(String keySummary, String detail, Object... args) {
        putMessage(FacesMessage.SEVERITY_FATAL, keySummary, detail, args);        
    }
    
    public void putFatal(String summary) {
        putMessage(FacesMessage.SEVERITY_FATAL, summary, "");        
    }
}