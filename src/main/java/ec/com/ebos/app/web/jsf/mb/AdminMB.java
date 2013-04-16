package ec.com.ebos.app.web.jsf.mb;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Bean de administracion de sesiones de usuarios
 * 
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = AdminMB.BEAN_NAME)
@ApplicationScoped
public class AdminMB implements Serializable{
    
	private static final long serialVersionUID = -3490701569635035883L;
	
	public static final String BEAN_NAME = "adminMB";
	
	/**
     * Sesiones de usuarios
     */
    private transient Map<SessionMB, Object> sesionUsuarioMap = new WeakHashMap<SessionMB, Object>();

    public void addSession(SessionMB sesionUsuario) {
        this.sesionUsuarioMap.put(sesionUsuario, null);
    }

    public void removeSession(SessionMB sesionUsuario) {
        this.sesionUsuarioMap.remove(sesionUsuario);
    }
    
    public List<SessionMB> getSessionList(){
        return new ArrayList<SessionMB>(sesionUsuarioMap.keySet());
    }

    public void removeSessionInactives(){
        Collection<SessionMB> list = sesionUsuarioMap.keySet();
        for (Iterator<SessionMB> iter = list.iterator(); iter.hasNext();) {
            SessionMB sesionUsuario = iter.next();
            if (!sesionUsuario.isLogin()) {
                sesionUsuarioMap.remove(sesionUsuario);
            }
        }
    }

}
