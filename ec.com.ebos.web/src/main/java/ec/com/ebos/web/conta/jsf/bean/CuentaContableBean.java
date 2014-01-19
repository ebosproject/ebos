package ec.com.ebos.web.conta.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.context.BeanScopes;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-05-23
 */
@Component(CuentaContableBean.BEAN_NAME)
@Scope(BeanScopes.SESSION)
//@ManagedBean(name = CuentaContableBean.BEAN_NAME)
//@ViewScoped
public class CuentaContableBean extends ContaBean<CuentaContable> {
    	
	private static final long serialVersionUID = 4109617962842899097L;
	
	@Getter
	public static final String BEAN_NAME = "cuentaContableBean";

	@Override
    public void getInit() {
        entitySearch = contaS.getInstanceCuentaContable();
    }

    @Override
    protected void habilitaControles() {
        setHabilitaCrear();
        setHabilitaEditar();
        setHabilitaGuardar();
        setHabilitaEliminar();
        
        if(EntityUtils.isPersistent(activeEntity)){
            
        } else {
        	setHabilitaEliminar(false);
        	//TODO (epa): La plataforma no debe permitir para todos las pantallas mostrar el boton eliminar si la 
        	//entidad activa no esta persistida en la base de datos
        }
    }

    @Override
    protected void initTarget() {
        TARGET_ID = "/modules/conta/cuentaContable/transaction.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<CuentaContable> loadDataTableCollection(CuentaContable cuentaContable, Pagination pagination) {
        //return contaS.findTipoCuentaList(cuentaContable, pagination);
    	return new ArrayList<CuentaContable>();
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        //activeEntity = contaS.createTipoCuenta();
    }

    @Override
    public void editar() {        
    	//activeEntity = masterS.loadBundle(activeEntity.getId());
    }
    
    @Override
    public void actualizar(){                        
        editar();
    }

    @Override
    public void guardar() {
        //activeEntity = contaS.saveTipoCuenta(activeEntity);                
    }

    @Override
    public void eliminar() {
        //contaS.deleteTipoCuenta(activeEntity);                
    }            
    
    //////////////////////// LISTS ///////////////////////////////
    
}
