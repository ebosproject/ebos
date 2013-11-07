package ec.com.ebos.hibernate.conta.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.conta.process.ContaP;
import ec.com.ebos.core.conta.service.ContaS;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.hibernate.conta.model.HibernateCuentaContable;
import ec.com.ebos.hibernate.conta.model.HibernateTipoCuenta;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-05-21
 */
@Service("contaS")
public class ContaSImpl implements ContaS{
    
	private static final long serialVersionUID = -9166399635499511530L;
	
	@Getter @Setter
    @Autowired
    @Qualifier("contaP")
    private ContaP contaP;

	//
	// TipoCuenta
	//
	
	@Override
	public List<TipoCuenta> findTipoCuentaList(TipoCuenta tipoCuenta,
			Pagination pagination) {		
		return contaP.findTipoCuentaList(tipoCuenta, pagination);
	}

	@Override
	public TipoCuenta createTipoCuenta() {
		return contaP.createTipoCuenta();
	}

	@Override
	public TipoCuenta saveTipoCuenta(TipoCuenta tipoCuenta) {
		return contaP.saveTipoCuenta(tipoCuenta);
	}

	@Override
	public void deleteTipoCuenta(TipoCuenta tipoCuenta) {
		contaP.deleteTipoCuenta(tipoCuenta);
	}
	
	public TipoCuenta getInstanceTipoCuenta(){
		return new HibernateTipoCuenta();
	}
	
	
	//
	// CuentaContable
	//
	
	public CuentaContable getInstanceCuentaContable(){
		return new HibernateCuentaContable();
	}
}
