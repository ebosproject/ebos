package ec.com.ebos.conta.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.conta.core.process.ContaP;
import ec.com.ebos.conta.model.TipoCuenta;
import ec.com.ebos.orm.crud.Pagination;

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
	
}
