package ec.com.ebos.web.admin.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

import ec.com.ebos.core.admin.model.Parametros;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.util.EntityUtils;


/**
 *
 * @author Eduardo Plua Alay
 */
@Component
@ManagedBean(name = ParametrosBean.BEAN_NAME)
@SessionScoped
public class ParametrosBean extends AdministracionBean<Parametros>{

	private static final long serialVersionUID = -8810452745934111969L;
	
	public static final String BEAN_NAME = "parametrosBean";
	
    @Override
    public void getInit() {
        entitySearch = administracionS.getInstanceParametros();
        entitySearch.setEstado(Entidad.Estado.ACTIVO);
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear(false);
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar(false);

        if (EntityUtils.isPersistent(activeEntity)) {
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/admin/parametros/index.jsf";
        TARGET_NEW_ID = "crearParametros";        
    }

    ///////////////////////// DATA MODEL ////////////////////////
    @Override
    protected List<Parametros> loadDataTableCollection(Parametros parametros, Pagination pagination) {
        return administracionS.getParametrosList(parametros);
    }

    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void editar() {
    }

    public void guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        String resp = validaDatos();
        
        if(resp.equals("OK")){
        activeEntity = administracionS.saveParametros(activeEntity);
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
