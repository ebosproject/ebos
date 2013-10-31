package ec.com.ebos.conta.core.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.conta.model.hibernate.HibernateTipoCuenta;
import ec.com.ebos.orm.crud.Pagination;

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
	public List<HibernateTipoCuenta> findTipoCuentaList(HibernateTipoCuenta tipoCuenta, Pagination pagination);
	
	public HibernateTipoCuenta createTipoCuenta();
	
	public HibernateTipoCuenta saveTipoCuenta(HibernateTipoCuenta tipoCuenta);
	
	public void deleteTipoCuenta(HibernateTipoCuenta tipoCuenta);
	
}
