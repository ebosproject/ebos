package ec.com.platform.app.web.jsf.mb;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import lombok.Setter;

/**
 * Las preferencias del usuario se guardan en {@link SesionUsuarioMB} 
 * 
 * @author Eduardo Plua Alay
 * @since 2013-02-27
 */
@ManagedBean(name = "guestPreferencesMB")
@SessionScoped
@Deprecated
public class GuestPreferencesMB implements Serializable {

	private static final long serialVersionUID = -5473698572112383419L;

	@Setter
	private String theme = "aristo"; //default

	public String getTheme() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.containsKey("theme")) {
			theme = params.get("theme");
		}
		
		return theme;
	}

}

