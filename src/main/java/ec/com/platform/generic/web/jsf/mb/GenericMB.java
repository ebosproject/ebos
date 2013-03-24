package ec.com.platform.generic.web.jsf.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ec.com.platform.app.web.jsf.mb.SesionUsuarioMB;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.NumberUtils;
import ec.com.platform.util.type.JsfMessage;

/**
 *
 * @author Eduardo Plua Alay
 */
    public abstract class GenericMB<T extends Generic<T>> extends AbstractServiceMB implements JsfMessage{

	private static final long serialVersionUID = 7260345983546581957L;
	
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

		private Paginacion paginacion = new Paginacion();
		
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
    
    protected List<T> loadDataTableCollection(T entity, Paginacion paginacion) {
        return new ArrayList<T>();
    }
    
    @SuppressWarnings("unchecked")
	protected T loadRowData(Long rowKey){
        return (T) new Object();
    }

    public final String getRandomId() {
        return "id_" + ("" + Math.random()).substring(2);
    }   

    protected void setHabilitaCrear() {
        setHabilitaCrear(sesionUsuario.verificaAcceso(TARGET_ID, SesionUsuarioMB.CREAR_ACTION_ID));
    }

    protected void setHabilitaGuardar() {
        setHabilitaGuardar(sesionUsuario.verificaAcceso(TARGET_ID, SesionUsuarioMB.CREAR_ACTION_ID));
    }

    protected void setHabilitaEditar() {
        setHabilitaEditar(sesionUsuario.verificaAcceso(TARGET_ID, SesionUsuarioMB.EDITAR_ACTION_ID));
    }

    protected void setHabilitaEliminar() {
        setHabilitaEliminar(sesionUsuario.verificaAcceso(TARGET_ID, SesionUsuarioMB.ELIMINAR_ACTION_ID));
    }

    protected void setHabilitaExportar() {
        setHabilitaExportar(sesionUsuario.verificaAcceso(TARGET_ID, SesionUsuarioMB.EXPORTAR_ACTION_ID));
    }
    
    @Override
    public void wrapMessage(FacesMessage.Severity severity, String summary, String... args){
        FacesMessage message = new FacesMessage(severity, summary, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @Override
    public void wrapSuccessMessage(String summary) {        
        wrapMessage(FacesMessage.SEVERITY_INFO, summary, "");
    }

    @Override
    public void wrapWarningMessage(String summary) {
        wrapMessage(FacesMessage.SEVERITY_WARN, summary, "");        
    }

    @Override
    public void wrapErrorMessage(String summary) {
        wrapMessage(FacesMessage.SEVERITY_ERROR, summary, "");        
    }
    
    @Override
    public void wrapFatalMessage(String summary) {
        wrapMessage(FacesMessage.SEVERITY_FATAL, summary, "");        
    }

}
