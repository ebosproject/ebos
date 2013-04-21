package ec.com.ebos.generic.web.jsf.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ec.com.ebos.administracion.resources.AdministracionMensajes;
import ec.com.ebos.administracion.web.jsf.mb.GenericAdministracionMB;
import ec.com.ebos.app.resources.AppMensajes;
import ec.com.ebos.app.web.jsf.mb.GenericAppMB;
import ec.com.ebos.app.web.jsf.mb.SessionMB;
import ec.com.ebos.bitacora.resources.BitacoraMensajes;
import ec.com.ebos.bitacora.web.jsf.mb.GenericBitacoraMB;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.seguridad.core.servicio.SeguridadS;
import ec.com.ebos.seguridad.resources.SeguridadMensajes;
import ec.com.ebos.seguridad.web.jsf.mb.GenericSeguridadMB;
import ec.com.ebos.util.NumberUtils;
import ec.com.ebos.util.type.JsfMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
    public abstract class GenericMB<T extends Entidad<T>> implements Serializable, JsfMessage{

	private static final long serialVersionUID = 6416663507886628619L;

	@Getter @Setter
    @ManagedProperty(value = SessionMB.EL_BEAN_NAME)
    protected SessionMB sessionMB;
	
    @Getter @Setter
    @ManagedProperty(value = "#{seguridadS}")
    protected SeguridadS seguridadS;
	
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
                
    public GenericMB() {
        initTarget();
    }
    
    public void _buscar(){
    	//TODO (epa): Limpiar numero de registros en el datalist para que funcione la busqueda del queryTemplate
    }
    
    public String _crear(){
        crear();
        habilitaControles();
        habilitaEliminar = false;
        return TARGET_NEW_ID;
    }
    
    protected void crear(){        
    }
    
    public String _editar(){
        editar();
        habilitaControles();
        return TARGET_NEW_ID;
    }
    
    protected void editar(){     
    }
    
    public void _actualizar(){        
        actualizar();
        habilitaControles();
    }
    
    protected void actualizar(){        
    }
    
    public void _guardar(){
        guardar();
        habilitaControles();
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
    
    @Getter //TODO (epa): Optimizar paginacion en LazyDataModel
    protected final LazyDataModel<T> dataTable = new LazyDataModel<T>() {                   
                        
		private static final long serialVersionUID = -5130944051776981116L;

		private Pagination paginacion = new Pagination();
		
		private List<T> data = new ArrayList<T>();

		@Override
        public List<T> load(int first, int pageSize, String sortField,
                SortOrder sortOrder, Map<String, String> filters) {
			paginacion.setFirst(first);
			paginacion.setPageSize(pageSize);
			paginacion.setSortField(sortField);
			paginacion.setSortOrder(sortOrder);
			paginacion.setFilters(filters);
			
            data = loadDataTableCollection(entitySearch, paginacion);
            this.setRowCount(paginacion.getRowCount());
            if(data != null && !data.isEmpty()){
            	activeEntity = data.get(0);
            }            
            return data;
            //rowCount  
//            int dataSize = data.size();
//            this.setRowCount(dataSize);
              

            //paginate  
//            if(dataSize > pageSize) {  
//                try {  
//                    return data.subList(first, first + pageSize);  
//                }  
//                catch(IndexOutOfBoundsException e) {  
//                    return data.subList(first, first + (dataSize % pageSize));  
//                }  
//            }  
//            else {  
//                return data;  
//            }                          
        }
        
        @Override
        public T getRowData(String rowKey) {            
            return loadRowData(NumberUtils.parseLong(rowKey));
        }

        @Override
        public Object getRowKey(T entity) {
            return entity.getId();
        }
    };
    
    protected List<T> loadDataTableCollection(T entity, Pagination paginacion) {
        return new ArrayList<T>();
    }
    
    @SuppressWarnings("unchecked")
	protected T loadRowData(Long rowKey){
        return (T) new Object();
    }

    protected void setHabilitaCrear() {
        setHabilitaCrear(sessionMB.verificaAcceso(TARGET_ID, SessionMB.CREATE_ACTION_ID));
    }

    protected void setHabilitaGuardar() {
        setHabilitaGuardar(sessionMB.verificaAcceso(TARGET_ID, SessionMB.CREATE_ACTION_ID));
    }

    protected void setHabilitaEditar() {
        setHabilitaEditar(sessionMB.verificaAcceso(TARGET_ID, SessionMB.EDIT_ACTION_ID));
    }

    protected void setHabilitaEliminar() {
        setHabilitaEliminar(sessionMB.verificaAcceso(TARGET_ID, SessionMB.DELETE_ACTION_ID));
    }

    protected void setHabilitaExportar() {
        setHabilitaExportar(sessionMB.verificaAcceso(TARGET_ID, SessionMB.EXPORT_ACTION_ID));
    }
    
	/**
	 * Construye un mensaje utilizando el archivo de propiedades
	 * del proyecto correspondiente al modulo
	 * @param keySummary
	 * @param params
	 * @return
	 */
	private String buildMessage(String keySummary, Object... params) {
		String message = "";
		if (this instanceof GenericAdministracionMB) {
			message = AdministracionMensajes.getString(keySummary, params);
		} else if (this instanceof GenericSeguridadMB) {
			message = SeguridadMensajes.getString(keySummary, params);
		} else if (this instanceof GenericBitacoraMB){
			message = BitacoraMensajes.getString(keySummary, params);
		} else if (this instanceof GenericAppMB){
			message = AppMensajes.getString(keySummary, params);
		}
		return message;
	}
	
    public void putSuccess(String key, Object... args) {        
    	sessionMB.putSuccess(buildMessage(key, args));
     }

     public void putWarning(String key, Object... args) {
    	 sessionMB.putWarning(buildMessage(key, args));        
     }

     public void putError(String key, Object... args) {
    	 sessionMB.putError(buildMessage(key, args));        
     }
     
     public void putFatal(String key, Object... args) {
    	 sessionMB.putFatal(buildMessage(key, args));        
     }
        
	///////////////////////////LISTS ///////////////////////////
    
    @Getter
	private List<Entidad.Estado> estadoList = new ArrayList<Entidad.Estado>(Entidad.Estado.LIST);

}
