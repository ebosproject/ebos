package ec.com.ebos.root.web.jsf.bean.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ec.com.ebos.master.web.jsf.bean.SessionBean;
import ec.com.ebos.master.web.jsf.bean.impl.SessionBeanImpl;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.exception.RootException;
import ec.com.ebos.root.core.web.jsf.bean.RootBean;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.core.service.SecurityS;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.FacesUtils;
import ec.com.ebos.util.MessageUtils;
import ec.com.ebos.util.NumberUtils;
import ec.com.ebos.util.type.JsfMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
public abstract class RootBeanImpl<T extends Entidad<T>> implements Serializable, JsfMessage, RootBean<T>{

	private static final long serialVersionUID = 6416663507886628619L;

	@Getter @Setter
    @ManagedProperty(value = SessionBean.EL_BEAN_NAME)
    protected SessionBean sessionBean;
	
    @Getter @Setter
    @ManagedProperty(value = SecurityS.EL_BEAN_NAME)
    protected SecurityS securityS;
	
	protected String TARGET_ID;
    protected String TARGET_NEW_ID;
    
    @Getter @Setter
    private boolean habilitaGuardar = false;
    
    @Getter @Setter
    private boolean habilitaEditar = false;
    
    @Getter @Setter
    private boolean habilitaEliminar = false;
    
    @Getter @Setter
    private boolean habilitaCrear = false;
    
    @Getter @Setter
    private boolean habilitaExportar = false;
    
    @Getter @Setter
    private boolean habilitaActualizar = true;
    
    @Getter @Setter
    private boolean habilitaCerrar = true;
    
    @Getter
    protected T entitySearch;
    
    @Getter @Setter
    protected T activeEntity;
    
    @Getter @Setter
    protected Long paramId;
    
    private boolean loaded = true;
    
    public RootBeanImpl() {
        initTarget();
    }
    
    public void _buscar(){
    	loaded = false;
    }
    
    public void _crear(){
        crear();
        habilitaControles();
        habilitaEliminar = false; 
        habilitaActualizar = false;
    }
    
    protected void crear(){        
    }
    
    public String _editar(){
        editar();
        habilitaControles();
        habilitaActualizar = EntityUtils.isPersistent(activeEntity);
        return TARGET_NEW_ID;
    }
    
    protected void editar(){     
    }
    
    public void _actualizar(){
    	if(EntityUtils.isPersistent(activeEntity)){
    		editar();
    	}
        actualizar();
        habilitaControles();
    }
    
    protected void actualizar(){        
    }
    
    public void _guardar(){
        guardar();
        habilitaControles();
        habilitaActualizar = EntityUtils.isPersistent(activeEntity);
    }

    protected void guardar(){        
    }
    
    public String _eliminar(){
        eliminar();
        habilitaControles();
        return TARGET_ID;
    }
    
    protected void eliminar(){        
    }
    
    public abstract void getInit();

    protected abstract void habilitaControles();

    protected abstract void initTarget();

    public final String TARGET_ID() {
        return TARGET_ID;
    }

    public final String TARGET_NEW_ID() {
        return TARGET_NEW_ID;
    }

    @PostConstruct
    public void postConstruct() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaExportar();
        getInit();
        habilitaControles();
    }
	
    public void destroy(String beanName){
    	FacesUtils.removeViewScopedBean(beanName);
    }
     
    
    @Getter
    protected final LazyDataModel<T> dataTable = new LazyDataModel<T>() {                   
                        
		private static final long serialVersionUID = -5130944051776981116L;

		private Pagination pagination = new Pagination();
		private List<T> data = new ArrayList<T>();
			
		@Override
        public List<T> load(int first, int pageSize, String sortField,
                SortOrder sortOrder, Map<String, String> filters) {
			
			pagination.setFirst(first);
			pagination.setPageSize(pageSize);
			pagination.setSortField(sortField);
			pagination.setSortOrder(sortOrder);
			pagination.setFilters(filters);
			
			if(!loaded){
				data = loadDataTableCollection(entitySearch, pagination);
			}
            
            this.setRowCount(pagination.getRowCount());
            if(data != null && !data.isEmpty()){
            	activeEntity = data.get(0);
            }           
            return data;                        
        }
        
        @Override
        public T getRowData(String rowKey) {            
            return loadRowData(NumberUtils.parseLong(rowKey));
        }

        @Override
        public Object getRowKey(T entity) {
            return entity.getId();
        }
        
        @Override
        public void setRowIndex(int rowIndex) {
        	if ( rowIndex == -1 || getPageSize() == 0 ) {
        	    super.setRowIndex( -1 );
        	   } else
    	    super.setRowIndex( rowIndex % getPageSize() );
        };
    };
    
    protected List<T> loadDataTableCollection(T entity, Pagination pagination) {
        return new ArrayList<T>();
    }
    
    @SuppressWarnings("unchecked")
	protected T loadRowData(Long rowKey){
        return (T) new Object();
    }

    protected void setHabilitaCrear() {
        setHabilitaCrear(sessionBean.verificaAcceso(TARGET_ID, SessionBeanImpl.CREATE_ACTION_ID));
    }

    protected void setHabilitaGuardar() {
        setHabilitaGuardar(sessionBean.verificaAcceso(TARGET_ID, SessionBeanImpl.CREATE_ACTION_ID));
    }

    protected void setHabilitaEditar() {
        setHabilitaEditar(sessionBean.verificaAcceso(TARGET_ID, SessionBeanImpl.EDIT_ACTION_ID));
    }

    protected void setHabilitaEliminar() {
        setHabilitaEliminar(sessionBean.verificaAcceso(TARGET_ID, SessionBeanImpl.DELETE_ACTION_ID));
    }

    protected void setHabilitaExportar() {
        setHabilitaExportar(sessionBean.verificaAcceso(TARGET_ID, SessionBeanImpl.EXPORT_ACTION_ID));
    }
    
	/**
	 * Construye un mensaje utilizando el archivo de propiedades
	 * del proyecto correspondiente al modulo
	 * @param keySummary
	 * @param params
	 * @return
	 */
	private String buildMessage(String keySummary, Object... args) {
		return MessageUtils.getFormattedMessage(FacesUtils.getLabel(keySummary), args);
	}
	
    public void putSuccess(String key, Object... args) {        
    	sessionBean.putSuccess(buildMessage(key, args));
     }

     public void putWarning(String key, Object... args) {
    	 sessionBean.putWarning(buildMessage(key, args));        
     }

     public void putError(String key, Object... args) {
    	 sessionBean.putError(buildMessage(key, args));        
     }
     
     public void putFatal(String key, Object... args) {
    	 sessionBean.putFatal(buildMessage(key, args));        
     }
        
	///////////////////////////LISTS ///////////////////////////
    
    @Getter
	private List<Entidad.Estado> estadoList = new ArrayList<Entidad.Estado>(Entidad.Estado.LIST);
    
    ////////////////////////// UTILS //////////////////////////
    
    public String getRandomId(){
    	return FacesUtils.getRandomId();
    }

	public void putError(Throwable e) {
		if (sessionBean != null) {
			sessionBean.setSuccess(false);
		}
		Severity severity = FacesMessage.SEVERITY_FATAL;
		e.printStackTrace();
		if (e instanceof RootException && !((RootException) e).isFatal()) {
			severity = FacesMessage.SEVERITY_ERROR;
		}
		MessageUtils.clearFacesMessages(FacesMessage.SEVERITY_WARN);
		MessageUtils.addSimpleFacesMessage(e.getMessage(), severity);
	}

}
