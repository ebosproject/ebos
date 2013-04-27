package ec.com.ebos.security.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Opcion;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.StringUtils;

/**
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = OpcionBean.BEAN_NAME)
//@SessionScoped
@ViewScoped
public class OpcionBean extends SecurityBean<Opcion> {
    
	private static final long serialVersionUID = 833763360386716739L;
	
	public static final String BEAN_NAME = "opcionBean";

	@Override
    public void getInit() {
        entitySearch = new Opcion();
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar(); //TODO (epa):Implementar Seguridad
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/security/opcion/index.jsf";
        TARGET_NEW_ID = "crearOpcion";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Opcion> loadDataTableCollection(Opcion opcion, Pagination pagination) {
        return securityS.findOpcionList(opcion,pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = securityS.createOpcion();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = securityS.saveOpcion(activeEntity);        
    }

    @Override
    public void eliminar() {
        securityS.deleteOpcion(activeEntity);        
    }            

   
    //////////// LISTS ////////////////////////////
    
    private List<Opcion> opcionList = new ArrayList<Opcion>();
    
    
    ///////////// CONVERTERS ////////////////////////
    public Converter getOpcionConverter(){
        return new Converter(){

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) throws ConverterException {
                if(submittedValue == null || submittedValue.trim().equals("")){
                    return null;
                }
                try{
                    return securityS.getOpcion(Long.parseLong(submittedValue));
                } catch(NumberFormatException exception) {
                	throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid objet"));
                }
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
                if(value == null || value.equals("")){
                    return StringUtils.empty;
                } else {
                    return String.valueOf(((Opcion) value).getId());
                }
            }          
        };
    }
    
    ///////////// GETTERS AND SETTERS //////////////////////////

    public List<Opcion> getOpcionList() {
    	if(opcionList.isEmpty()){
    		opcionList = securityS.getOpcionPadreList();
    	}
        return opcionList;
    }
    
}
