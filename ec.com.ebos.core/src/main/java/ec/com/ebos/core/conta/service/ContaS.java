package ec.com.ebos.core.conta.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-05-21
 */
public interface ContaS extends Serializable {
    
	/**
	 * Nombre del Spring Bean para {@link ContaS}
	 */
	public static final String BEAN_NAME = "contaS";
	
	//
    // TipoCuenta
    //
	public List<TipoCuenta> findTipoCuentaList(TipoCuenta tipoCuenta, Pagination pagination);
	
	public TipoCuenta createTipoCuenta();
	
	public TipoCuenta saveTipoCuenta(TipoCuenta tipoCuenta);
	
	public void deleteTipoCuenta(TipoCuenta tipoCuenta);
	
	public TipoCuenta getInstanceTipoCuenta();
	
	
	//
	// CuentaContable
	//
	public CuentaContable getInstanceCuentaContable();

}
