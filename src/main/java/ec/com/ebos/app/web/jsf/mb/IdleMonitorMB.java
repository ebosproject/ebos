package ec.com.ebos.app.web.jsf.mb;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.util.StringUtil;
import org.primefaces.context.RequestContext;

import lombok.Setter;

@ManagedBean(name = "idleMonitorMB")
@RequestScoped
public class IdleMonitorMB {

	@Setter
	@ManagedProperty(value = "#{sesionUsuario}")
	private SesionUsuarioMB sesionUsuario;

	public void idleListener() {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navHandler = context.getApplication()
				.getNavigationHandler();
		sesionUsuario.cerrarSesion();
//		RequestContext requestContext = RequestContext.getCurrentInstance();
		
//		requestContext.update("frgWelcome");
//		requestContext.update("frgHome");
		
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
				new FacesMessage(FacesMessage.SEVERITY_WARN, sesionUsuario
						.getUsuario().getEmpresaPersona().getPersona().getNombres(), "Bienvenido de nuevo"));
	}

}
