package ec.com.ebos.root.core.web.jsf.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.LazyDataModel;

import ec.com.ebos.master.web.jsf.bean.SessionBean;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.root.model.Entidad.Estado;
import ec.com.ebos.security.core.service.SecurityS;

public interface RootBean<T extends Entidad<T>> {

	public SessionBean getSessionBean();

	public void setSessionBean(SessionBean sessionBean);

	public SecurityS getSecurityS();

	public void setSecurityS(SecurityS securityS);

	public boolean isHabilitaGuardar();

	public void setHabilitaGuardar();

	public boolean isHabilitaEditar();

	public void setHabilitaEditar();

	public boolean isHabilitaEliminar();

	public void setHabilitaEliminar();

	public boolean isHabilitaCrear();

	public void setHabilitaCrear();

	public boolean isHabilitaExportar();

	public void setHabilitaExportar();

	public boolean isHabilitaActualizar();

	public void setHabilitaActualizar(boolean habilitaActualizar);

	public boolean isHabilitaCerrar();

	public void setHabilitaCerrar(boolean habilitaCerrar);

	public T getEntitySearch();

	public T getActiveEntity();

	public void setActiveEntity(T activeEntity);

	public Long getParamId();

	public void setParamId(Long paramId);

	public void _buscar();

	public void _crear();

	public String _editar();

	public void _actualizar();

	public void _guardar();

	public String _eliminar();

	public void getInit();

	public String TARGET_ID();

	public String TARGET_NEW_ID();

	@PostConstruct
	public void postConstruct();

	public void destroy(String beanName);

	public LazyDataModel<T> getDataTable();

	public void putSuccess(String key, Object... args);

	public void putWarning(String key, Object... args);

	public void putError(String key, Object... args);

	public void putFatal(String key, Object... args);

	public List<Entidad.Estado> getEstadoList();

	public String getRandomId();

	public void putError(Throwable e);

}