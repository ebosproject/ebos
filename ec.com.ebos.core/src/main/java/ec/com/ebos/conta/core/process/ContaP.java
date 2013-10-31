package ec.com.ebos.conta.core.process;

import java.util.List;

import ec.com.ebos.conta.model.hibernate.HibernateTipoCuenta;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface ContaP {

	//
	// TipoCuenta
	//
	public List<HibernateTipoCuenta> findTipoCuentaList(HibernateTipoCuenta tipoCuenta, Pagination pagination);
	
	public HibernateTipoCuenta createTipoCuenta();
	
	public HibernateTipoCuenta saveTipoCuenta(HibernateTipoCuenta tipoCuenta);
	
	public void deleteTipoCuenta(HibernateTipoCuenta tipoCuenta);
}
