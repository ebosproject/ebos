package ec.com.ebos.conta.core.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ec.com.ebos.conta.exception.ContaException;
import ec.com.ebos.conta.model.Asiento;
import ec.com.ebos.conta.model.AsientoDetalle;
import ec.com.ebos.conta.model.CuentaContable;
import ec.com.ebos.conta.model.Periodo;
import ec.com.ebos.conta.model.SaldoCuentaContable;
import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.root.core.process.RootPImpl;
import ec.com.ebos.root.model.Entidad.Estado;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Repository("contaP")
public class ContaPImpl extends RootPImpl<Object, ContaException> implements ContaP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6576805861066271522L;

	public Periodo getPeriodo(Organizacion empresa, Date fecha){
		return null;
	}
	
	public CuentaContable getCuentaContable(Long id){
		return null;
	}
	
	public Boolean mayorizarAsiento(Asiento asiento, int signo){
		
//		actualizarSaldosCuentasContbables();
//		actualizarSaldosCentrosCostos();
//		actualizarSaldosCuentasCentros();
		
		List<CuentaContable> cuentasPadres = new ArrayList<CuentaContable>();
		Periodo periodo = getPeriodo(null,  asiento.getDocumento().getEmitido());
		GenericCriteria<AsientoDetalle> criteria = GenericCriteria.forClass(AsientoDetalle.class, "subCriteria");
		criteria.addAliasedJoins("cuentaContableEmpresa", "cuentaContableEmpresa.cuentaContable", "cuentaContable.padre");
		criteria.addEqualsProperty("criteria.id", "cuentaContableEmpresa.id");
		criteria.addEquals("asiento", asiento);
		//criteria.addLikeProperty("cuentaContableEmpresa.cuentaContable", "cuentaContable.padre");
		criteria.addSql("{cuentaContableEmpresa}.cuentaContable like {cuentaContable.padre || '%'");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("cuentaContableEmpresa.cuentaContable"), "cuentaContable")
				.add(Projections.sum("valorDebe"), "valorDebe")
				.add(Projections.sum("valorHaber"), "valorHaber"));
		
		List<SaldoCuentaContable> saldos = findByCriteriaWithAliasToBeanTransformation(criteria, SaldoCuentaContable.class);
		for (SaldoCuentaContable saldoCuentaContable : saldos) {
			saldoCuentaContable.setPeriodo(periodo);
			saldoCuentaContable.setEstado(Estado.ACTIVO);
			saldoCuentaContable.setSaldo(BigDecimal.ZERO);
			CuentaContable cp = saldoCuentaContable.getCuentaContable();
			if(!cuentasPadres.contains(cp.getPadre())){
				while(cp.getPadre()!=null){
					cuentasPadres.add(cp.getPadre());
					if(cp.getPadre()!=null){
						cp = getCuentaContable(cp.getPadre().getId());
					}
				}
			} else {
				//
			}
			saveOrMerge(saldoCuentaContable);
		}
		
		return true;
	}
	
	public List<AsientoDetalle> getListAsientoDetalle(Asiento asiento){
		return null;
	}
	
}
