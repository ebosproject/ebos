package ec.com.platform.app.web.jsf.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import ec.com.platform.app.model.Persona;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.util.GenericUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-04-02
 */
@ManagedBean(name = "personaMB")
@SessionScoped
public class PersonaMB extends GenericAppMB<Persona> {
    
	private static final long serialVersionUID = 783070179851922363L;

	@Override
    public void getInit() {
        entitySearch = new Persona();
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(GenericUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "app/persona/index.jsf";
        TARGET_NEW_ID = "crearPersona";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<Persona> loadDataTableCollection(Persona persona, Paginacion paginacion) {
        return appS.obtenerPersonaList(persona, paginacion);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = appS.obtenerPersonaNuevo();
    }

    @Override
    public void editar() {        
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        activeEntity = appS.guardarPersona(activeEntity);                
    }

    @Override
    public void eliminar() {
        appS.eliminarPersona(activeEntity);                
    }
    
	///////////////////////////LISTS ///////////////////////////
    
    @Getter
	private List<Persona.TipoIdentificacion> tipoIdentificacionList = new ArrayList<Persona.TipoIdentificacion>(Persona.TipoIdentificacion.LIST);
    
    @Getter
	private List<Persona.TipoPersona> tipoPersonaList = new ArrayList<Persona.TipoPersona>(Persona.TipoPersona.LIST);
}
