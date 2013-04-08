package ec.com.ebos.seguridad.web.jsf.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ec.com.ebos.fwk.crud.Paginacion;
import ec.com.ebos.generic.model.Entidad;
import ec.com.ebos.seguridad.model.Opcion;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.StringUtils;

/**
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "opcionMB")
@SessionScoped
public class OpcionMB extends GenericSeguridadMB<Opcion> {
    
	private static final long serialVersionUID = 833763360386716739L;

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
        TARGET_ID = "seguridad/opcion/index.jsf";
        TARGET_NEW_ID = "crearOpcion";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Opcion> loadDataTableCollection(Opcion opcion, Paginacion paginacion) {
        return seguridadS.obtenerOpcionList(opcion,paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = seguridadS.obtenerOpcionNuevo();
    }

    @Override
    public void editar() {        
    }
        
    @Override
    public void guardar() {
        activeEntity = seguridadS.guardarOpcion(activeEntity);        
    }

    @Override
    public void eliminar() {
        seguridadS.eliminarOpcion(activeEntity);        
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
                    return seguridadS.obtenerOpcion(Long.parseLong(submittedValue));
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
    		opcionList = seguridadS.obtenerOpcionPadreList();
    	}
        return opcionList;
    }
    
}
