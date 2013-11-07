package ec.com.ebos.core.conta.process;

import java.util.List;

import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.orm.crud.Pagination;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface ContaP {

	//
	// TipoCuenta
	//
	public List<TipoCuenta> findTipoCuentaList(TipoCuenta tipoCuenta, Pagination pagination);
	
	public TipoCuenta createTipoCuenta();
	
	public TipoCuenta saveTipoCuenta(TipoCuenta tipoCuenta);
	
	public void deleteTipoCuenta(TipoCuenta tipoCuenta);
}
