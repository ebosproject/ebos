package ec.com.ebos.web.master.jsf.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.BeanScopes;
import ec.com.ebos.core.context.EbosContext;
import ec.com.ebos.core.master.session.SessionBean;

@Component(IdleMonitorBean.BEAN_NAME)
@Scope(BeanScopes.REQUEST)
//@ManagedBean(name = IdleMonitorBean.BEAN_NAME)
//@RequestScoped
public class IdleMonitorBean implements Serializable{

	private static final long serialVersionUID = 1843880331963115097L;

	public static final String BEAN_NAME = "idleMonitorBean";
	
	@Setter
	@ManagedProperty(value = SessionBean.EL_BEAN_NAME)
	private SessionBean sessionBean;

	public void idleListener() {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navHandler = context.getApplication()
				.getNavigationHandler();
		sessionBean.cerrarSesion();

		EbosContext.updateComponent(":pngWelcome", "pnHome");
		
//		context.addMessage(
//				null,
//				new FacesMessage(FacesMessage.SEVERITY_WARN, sesionUsuario
//						.getUsuario().getNombres(), "Bienvenido de nuevo"));
		navHandler.handleNavigation(context, null,
				"welcome?faces-redirect=true&timedout=true");
	}

	public void activeListener() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, sessionBean
						.getUsuario().getEmpresaPersona().getPersona().getNombres(), "Bienvenido de nuevo"));
	}

}
