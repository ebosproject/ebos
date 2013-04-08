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
@ManagedBean(name = "adminMB")
@ApplicationScoped
public class AdminMB implements Serializable{
    
	private static final long serialVersionUID = -3490701569635035883L;
	
	/**
     * Sesiones de usuarios
     */
    private transient Map<SesionUsuarioMB, Object> sesionUsuarioMap = new WeakHashMap<SesionUsuarioMB, Object>();

    public void addSession(SesionUsuarioMB sesionUsuario) {
        this.sesionUsuarioMap.put(sesionUsuario, null);
    }

    public void removeSession(SesionUsuarioMB sesionUsuario) {
        this.sesionUsuarioMap.remove(sesionUsuario);
    }
    
    public List<SesionUsuarioMB> getSessionList(){
        return new ArrayList<SesionUsuarioMB>(sesionUsuarioMap.keySet());
    }

    public void removeSessionInactives(){
        Collection<SesionUsuarioMB> list = sesionUsuarioMap.keySet();
        for (Iterator<SesionUsuarioMB> iter = list.iterator(); iter.hasNext();) {
            SesionUsuarioMB sesionUsuario = iter.next();
            if (!sesionUsuario.isLogin()) {
                sesionUsuarioMap.remove(sesionUsuario);
            }
        }
    }

}
