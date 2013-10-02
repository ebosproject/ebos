package ec.com.ebos.master.web.jsf.bean;

import java.util.List;


public interface AppBean {

	public static final String BEAN_NAME = "adminBean";
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{" + BEAN_NAME + "}";

	public void addSession(SessionBean sesionUsuario);

	public void removeSession(SessionBean sesionUsuario);

	public List<SessionBean> getSessionList();

	public void removeSessionInactives();

	public String getUrl();

}