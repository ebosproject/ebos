package ec.com.ebos.master.web.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.Bundle.Localidad;
import ec.com.ebos.context.EbosContext;
import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.master.model.Tema;
import ec.com.ebos.master.session.SessionBean;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.security.core.service.SecurityS;
import ec.com.ebos.security.model.RolOpcion;
import ec.com.ebos.security.model.Usuario;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;

/**
 * Bean para datos de sesion del usuario
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Component(SessionBeanImpl.BEAN_NAME)
@ManagedBean(name = SessionBeanImpl.BEAN_NAME)
@SessionScoped
public class SessionBeanImpl implements Serializable, SessionBean{

	private static final long serialVersionUID = 502301922012194259L;
	
	@Getter @Setter
    @Autowired
    @Qualifier(SecurityS.BEAN_NAME)
    protected SecurityS securityS;

    @Getter @Setter
    @Autowired
    protected AppBean admin;
    
	private String tema = null;
	
	@Getter @Setter
	private Localidad localidad;
    
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
        //usuario = new HibernateUsuario();
        login = false;
        rolOpcionList = new ArrayList<RolOpcion>();
//        defineSessionTimeout();
    }
    
    @PreDestroy
    public void preDestroy() {
        admin.removeSession(this);
    }
    
    public void iniciarSesion(){                
        if(login = securityS.authLogin(this)){
        	localidad = usuario.getLocalidad();
        	changeLocale();
        }
        usuario.setPassword(null);
        defineSessionTimeout();
    }
    
    public void cerrarSesion() {                
        this.rolOpcionList.clear();        
        this.login = false;
        ExternalContext extContext = EbosContext.getExternalContext();
        
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
    
    /**
     * Metodo que verifica los permisos que el usuario actual tiene sobre
     * las opciones asignadas
     *  
     * @param target
     * @param accion
     * @return
     */
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
    	EbosContext.getExternalContext().setSessionMaxInactiveInterval(login ? Constantes.SESSION_TIMEOUT_LOGON : Constantes.SESSION_TIMEOUT_LOGOUT);
    }
    
	public String getTema() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (params.containsKey(Constantes.THEME_KEY)) {
			tema = params.get(Constantes.THEME_KEY);
		} else {
			tema = usuario.getTema() != null ? usuario.getTema() : Constantes.THEME;
		}
		return tema;
	}
		
	/**
	 * Guarda el tema seleccionado por el usuario actual
	 * @param theme
	 */
	public void saveTheme(String theme){
		this.tema = theme;
		usuario.setTema(theme);
		securityS.saveUserPreferences(usuario);
	}
	
	/**
	 * Realiza el cambio de localidad(languaje  y pais) del usuario actual,
	 * default = 'es_EC' //TODO (epa): Parametrizar localidad por defecto
	 */
	private void changeLocale(){
		String language = "es", country = "EC";
		if(localidad != null){
			String newLocaleValue = localidad.getValue();
			language = newLocaleValue.substring(0, 2);
			country = newLocaleValue.substring(3, 5);
		}
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language, country));
	}
	
	/**
	 * Guarda la nueva localidad del usuario actual
	 */
	public void saveLocale(){
		changeLocale();
		usuario.setLocalidad(localidad);
		securityS.saveUserPreferences(usuario);
	}
	
    //////////////////////// LISTS ///////////////////////////////
	
	@Getter
    protected final List<Bundle.Localidad> localidadList = new ArrayList<Bundle.Localidad>(Bundle.Localidad.LIST);
	
	
    //////////////////////// MESSAGES ///////////////////////////////
	
	@Getter @Setter
	private boolean success;
	
    private void putMessage(FacesMessage.Severity severity, String keySummary, String detail, Object... args){
    	String summary = MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args);
		putMessage(severity, summary, (detail != null && !detail.isEmpty()) ? detail : "");
    }
    
    private void putMessage(FacesMessage.Severity severity, String summary, String detail){
    	FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void putInfo(String keySummary, String detail, Object... args) {
    	success = false;
        putMessage(FacesMessage.SEVERITY_INFO, keySummary, detail, args);
    }
    
    public void putInfo(String summary) {
    	success = false;
        putMessage(FacesMessage.SEVERITY_INFO, summary, "");
    }
    
    public void putSuccess(String keySummary, String detail, Object... args) {
    	success = true;
        putMessage(FacesMessage.SEVERITY_INFO, keySummary, detail, args);
    }
    
    public void putSuccess(String summary) {
    	success = true;
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

	@Override
	public Auditoria getAuditoria() {
		return securityS.getAuditoria();
	}
	
	public Tema getInstanceTema(String nombre, String imagen){
		return securityS.getInstanceTema(nombre, imagen);
	}

}
