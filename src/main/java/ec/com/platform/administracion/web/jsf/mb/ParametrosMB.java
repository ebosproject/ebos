package ec.com.platform.administracion.web.jsf.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ec.com.platform.administracion.model.Parametros;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.GenericUtils;


/**
 *
 * @author Eduardo Plua Alay
 */
@ManagedBean(name = "parametrosMB")
@SessionScoped
public class ParametrosMB extends GenericAdministracionMB<Parametros>{

	private static final long serialVersionUID = -8810452745934111969L;
	
    @Override
    public void getInit() {
        entitySearch = new Parametros();
        entitySearch.setEstado(Generic.Estado.ACTIVO);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear(false);
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar(false);

        if (GenericUtils.isPersistent(activeEntity)) {
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "administracion/parametros/index.jsf";
        TARGET_NEW_ID = "crearParametros";        
    }

    ///////////////////////// DATA MODEL ////////////////////////
    @Override
    protected List<Parametros> loadDataTableCollection(Parametros parametros, Paginacion paginacion) {
        return administracionS.obtenerParametrosList(parametros);
    }

    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void editar() {
    }

    public void guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        String resp = validaDatos();
        
        if(resp.equals("OK")){
        activeEntity = administracionS.guardarParametros(activeEntity);
            FacesMessage mensaje = new FacesMessage("Datos guardados, debe reiniciar el Motor commsms");
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, mensaje);
        }else{
            FacesMessage mensaje = new FacesMessage(resp);
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensaje);
        }
        
        habilitaControles();
    }

    //Setters and Getters

    public Parametros getParametrosActual() {
        return activeEntity;
    }

    public void setParametrosActual(Parametros activeEntity) {
        this.activeEntity = activeEntity;
    }
    
    private String validaDatos(){
        String v = "OK";
        if(activeEntity.getGrupo().equals("LIMITAR") && activeEntity.getClave().equals("ESTADO")){
            if(activeEntity.getValor().equals("S") || activeEntity.getValor().equals("N")){
                v = "OK";
            }else{
                v = "Valor debe ser S o N";
            }
        }else if(!isNumeric(activeEntity.getValor())){
            v = "Valor debe ser un digito entero";
        }
        
        return v;
    }
    
    private boolean isNumeric(String valor){
        try{
            Integer.parseInt(valor);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
