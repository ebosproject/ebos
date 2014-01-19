package ec.com.ebos.web.master.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.context.BeanScopes;
import ec.com.ebos.core.master.session.SessionBean;

/**
 * Bean de administracion de sesiones de usuarios
 * 
 * @author Eduardo Plua Alay
 */
@Component(AppBean.BEAN_NAME)
//@Scope(BeanScopes.SINGLETON)
//@ManagedBean(name = AppBean.BEAN_NAME)
//@ApplicationScoped
public class AppBean implements Serializable{
    
	private static final long serialVersionUID = -3490701569635035883L;
	
	public static final String BEAN_NAME = "adminBean";
	public static final String EL_BEAN_NAME = "#{" + BEAN_NAME + "}";
	
	/**
     * Sesiones de usuarios
     */
    private transient Map<SessionBean, Object> sesionUsuarioMap = new WeakHashMap<SessionBean, Object>();

    public void addSession(SessionBean sesionUsuario) {
        this.sesionUsuarioMap.put(sesionUsuario, null);
    }

    public void removeSession(SessionBean sesionUsuario) {
        this.sesionUsuarioMap.remove(sesionUsuario);
    }
    
    public List<SessionBean> getSessionList(){
        return new ArrayList<SessionBean>(sesionUsuarioMap.keySet());
    }

    public void removeSessionInactives(){
        Collection<SessionBean> list = sesionUsuarioMap.keySet();
        for (Iterator<SessionBean> iter = list.iterator(); iter.hasNext();) {
            SessionBean sesionUsuario = iter.next();
            if (!sesionUsuario.isLogin()) {
                sesionUsuarioMap.remove(sesionUsuario);
            }
        }
    }
    
    @Getter
    private String url = "http://www.google.com";

}
