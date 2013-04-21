package ec.com.ebos.app.web.jsf.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lombok.Setter;

import org.primefaces.context.RequestContext;

@ManagedBean(name = IdleMonitorMB.BEAN_NAME)
@RequestScoped
public class IdleMonitorMB implements Serializable{

	private static final long serialVersionUID = 1843880331963115097L;

	public static final String BEAN_NAME = "idleMonitorMB";
	
	@Setter
	@ManagedProperty(value = SessionMB.EL_BEAN_NAME)
	private SessionMB sessionMB;

	public void idleListener() {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navHandler = context.getApplication()
				.getNavigationHandler();
		sessionMB.cerrarSesion();

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update(":pngWelcome");
		requestContext.update(":pngHome");
		
//		context.addMessage(
//				null,
//				new FacesMessage(FacesMessage.SEVERITY_WARN, sesionUsuario
//						.getUsuario().getNombres(), "Bienvenido de nuevo"));
		navHandler.handleNavigation(context, null,
				"home?faces-redirect=true&timedout=true");
	}

	public void activeListener() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, sessionMB
						.getUsuario().getEmpresaPersona().getPersona().getNombres(), "Bienvenido de nuevo"));
	}

}
