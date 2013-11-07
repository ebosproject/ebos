package ec.com.ebos.conta.web.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;

import org.springframework.stereotype.Component;

import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-05-21
 */
@Component
@ManagedBean(name = TipoCuentaBean.BEAN_NAME)
@ViewScoped
public class TipoCuentaBean extends ContaBean<TipoCuenta> {
    	
	private static final long serialVersionUID = 4109617962842899097L;
	
	@Getter
	public static final String BEAN_NAME = "tipoCuentaBean";

	@Override
    public void getInit() {
        entitySearch = contaS.getInstanceTipoCuenta();
        entitySearch.setTipo(null);
        entitySearch.setNaturaleza(null);
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
        TARGET_ID = "/modules/conta/tipoCuenta/finder.xhtml";
    }
    
    ///////////////////////// DATA MODEL ////////////////////////

    @Override
    protected List<TipoCuenta> loadDataTableCollection(TipoCuenta tipoCuenta, Pagination pagination) {
        return contaS.findTipoCuentaList(tipoCuenta, pagination);
    }
        
    //////////////////// ACCIONES ////////////////////
    
    @Override
    public void crear() {
        activeEntity = contaS.createTipoCuenta();
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
        activeEntity = contaS.saveTipoCuenta(activeEntity);                
    }

    @Override
    public void eliminar() {
        contaS.deleteTipoCuenta(activeEntity);                
    }            
    
    //////////////////////// LISTS ///////////////////////////////
    
    @Getter
    protected final List<TipoCuenta.Tipo> tipoList = new ArrayList<TipoCuenta.Tipo>(TipoCuenta.Tipo.LIST);
    
    @Getter
    protected final List<CuentaContable.Naturaleza> naturalezaList = new ArrayList<CuentaContable.Naturaleza>(CuentaContable.Naturaleza.LIST);
    
}
