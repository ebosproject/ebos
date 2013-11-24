package ec.com.ebos.web.admin.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;

import ec.com.ebos.core.admin.model.Opcion;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.StringUtils;

/**
 * @author Eduardo Plua Alay
 */
@Component
@ManagedBean(name = OpcionBean.BEAN_NAME)
@SessionScoped
public class OpcionBean extends AdministracionBean<Opcion> {
    
	private static final long serialVersionUID = 833763360386716739L;
	
	public static final String BEAN_NAME = "opcionBean";

	@Override
    public void getInit() {
        entitySearch = administracionS.getInstanceOpcion();
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar(); //TODO (epa):Implementar Seguridad
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/admin/opcion/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Opcion> loadDataTableCollection(Opcion opcion, Pagination pagination) {
        return administracionS.findOpcionList(opcion,pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = administracionS.createOpcion();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = administracionS.saveOpcion(activeEntity);        
    }

    @Override
    public void eliminar() {
        administracionS.deleteOpcion(activeEntity);        
    }            

   
    //////////// LISTS ////////////////////////////
    
    private List<Opcion> opcionList = new ArrayList<Opcion>();
    
    
    ///////////// CONVERTERS ////////////////////////
    public Converter getOpcionConverter(){
        return new Converter(){

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) throws ConverterException {
                if(submittedValue.trim().equals("")){
                    return null;
                } else {
                	try{
                        //return administracionS.getOpcion(Long.parseLong(submittedValue));
                		for(Opcion opc : getOpcionList()){
                			if(opc.getId().equals(Long.parseLong(submittedValue))){
                				return opc;
                			}
                		}
                    } catch(NumberFormatException exception) {
                    	throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid object"));
                    }
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
                if(value == null || value.equals("")){
                    return StringUtils.EMPTY;
                } else {
                    return String.valueOf(((Opcion) value).getId());
                }
            }          
        };
    }
    
    ///////////// GETTERS AND SETTERS //////////////////////////

    public List<Opcion> getOpcionList() { //TODO (epa): Cambiar por suggestions AutoCompleteItemTip
    	if(opcionList.isEmpty()){
    		opcionList = administracionS.getOpcionPadreList();
    	}
        return opcionList;
    }
    
}
