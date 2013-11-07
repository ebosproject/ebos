package ec.com.ebos.conta.core.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ec.com.ebos.conta.model.hibernate.HibernateAsientoDetalle;
import ec.com.ebos.conta.model.hibernate.HibernateCentroCosto;
import ec.com.ebos.conta.model.hibernate.HibernateCuentaContable;
import ec.com.ebos.conta.model.hibernate.HibernatePeriodo;
import ec.com.ebos.conta.model.hibernate.HibernateSaldoCentroCosto;
import ec.com.ebos.conta.model.hibernate.HibernateSaldoCuentaContable;
import ec.com.ebos.conta.model.hibernate.HibernateTipoCuenta;
import ec.com.ebos.conta.model.hibernate.field.CentroCosto_;
import ec.com.ebos.conta.model.hibernate.field.CuentaContable_;
import ec.com.ebos.conta.model.hibernate.field.Ejercicio_;
import ec.com.ebos.conta.model.hibernate.field.Periodo_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCentroCosto_;
import ec.com.ebos.conta.model.hibernate.field.SaldoCuentaContable_;
import ec.com.ebos.conta.model.hibernate.field.TipoCuenta_;
import ec.com.ebos.core.conta.exception.ContaException;
import ec.com.ebos.core.conta.model.Asiento;
import ec.com.ebos.core.conta.model.AsientoDetalle;
import ec.com.ebos.core.conta.model.CentroCosto;
import ec.com.ebos.core.conta.model.CuentaContable;
import ec.com.ebos.core.conta.model.Ejercicio;
import ec.com.ebos.core.conta.model.Periodo;
import ec.com.ebos.core.conta.model.SaldoCentroCosto;
import ec.com.ebos.core.conta.model.SaldoCuentaCentro;
import ec.com.ebos.core.conta.model.SaldoCuentaContable;
import ec.com.ebos.core.conta.model.TipoCuenta;
import ec.com.ebos.core.conta.process.ContaP;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.orm.crud.GenericCriteria;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.root.core.process.RootPImpl;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Repository("contaP")
public class ContaPImpl extends RootPImpl<Object, ContaException> implements ContaP {

	private static final long serialVersionUID = -6982586050138698390L;

	public Ejercicio getEjercicio(Organizacion empresa, Date fecha) {
		return null;
	}

	public Periodo getPeriodo(Ejercicio ejercicio, Date fecha) {
		GenericCriteria<HibernatePeriodo> criteria = GenericCriteria.forClass(HibernatePeriodo.class);
		criteria.addLE(Periodo_.fechaInicial, fecha);
		criteria.addGE(Periodo_.fechaFinal, fecha);
		criteria.addEquals(Periodo_.ejercicio, ejercicio);
		return findFirstByCriteria(criteria);
	}
	
	public Periodo getPeriodo(Date fecha) {
		GenericCriteria<HibernatePeriodo> criteria = GenericCriteria.forClass(HibernatePeriodo.class);
		criteria.addAliasedJoins(Periodo_.ejercicio);
		criteria.addLE(Periodo_.fechaInicial, fecha);
		criteria.addGE(Periodo_.fechaFinal, fecha);
		criteria.addEquals(Ejercicio_.empresa, getSessionBean().getEmpresa());
		return findFirstByCriteria(criteria);
	}

	public Periodo getPeriodo(Long id) {
		GenericCriteria<HibernatePeriodo> criteria = GenericCriteria.forClass(HibernatePeriodo.class);
		criteria.addAliasedJoins(Periodo_.ejercicio);
		criteria.addEquals(Periodo_.id, id);
		return findFirstByCriteria(criteria);
	}

	public List<HibernatePeriodo> listPeriodosPosteriores(Periodo periodo) {
		GenericCriteria<HibernatePeriodo> criteria = GenericCriteria.forClass(HibernatePeriodo.class);
		criteria.addAliasedJoins(Periodo_.ejercicio);
		criteria.addGE(Periodo_.fechaInicial, periodo.getFechaInicial());
		criteria.addEquals(Ejercicio_.empresa, getSessionBean().getEmpresa());
		criteria.addOrderAsc(Periodo_.fechaInicial);
		criteria.addNotEquals(Periodo_.estado, Periodo.Estado.PENDIENTE);
		return findByCriteria(criteria, true);
	}

	public HibernateCuentaContable getCuentaContable(Long id) {
		GenericCriteria<HibernateCuentaContable> criteria = GenericCriteria.forClass(HibernateCuentaContable.class);
		criteria.addAliasedJoins(CuentaContable_.padre);
		criteria.addEquals(CuentaContable_.id, id);
		return findFirstByCriteria(criteria);
	}
	
	public HibernateSaldoCuentaContable getSaldoCuenta(SaldoCuentaContable saldo) {
		GenericCriteria<HibernateSaldoCuentaContable> criteria = GenericCriteria.forClass(HibernateSaldoCuentaContable.class);
		criteria.addEquals(SaldoCuentaContable_.periodo, saldo.getPeriodo());
		criteria.addEquals(SaldoCuentaContable_.cuentaContable, saldo.getCuentaContable());
		return findFirstByCriteria(criteria);
	}
	
	public HibernateCentroCosto getCentroCosto(Long id) {
		GenericCriteria<HibernateCentroCosto> criteria = GenericCriteria.forClass(HibernateCentroCosto.class);
		criteria.addAliasedJoins(CentroCosto_.padre);
		criteria.addEquals(CentroCosto_.id, id);
		return findFirstByCriteria(criteria);
	}
	
	public HibernateSaldoCentroCosto getSaldoCentro(SaldoCentroCosto saldo) {
		GenericCriteria<HibernateSaldoCentroCosto> criteria = GenericCriteria.forClass(HibernateSaldoCentroCosto.class);
		criteria.addEquals(SaldoCentroCosto_.periodo, saldo.getPeriodo());
		criteria.addEquals(SaldoCentroCosto_.centroCosto, saldo.getCentroCosto());
		return findFirstByCriteria(criteria);
	}

	public List<HibernateAsientoDetalle> listAsientoDetalle(Asiento asiento) {
		GenericCriteria<HibernateAsientoDetalle> criteria = GenericCriteria.forClass(HibernateAsientoDetalle.class);
		criteria.addAliasedJoins("cuentaContableEmpresa", 
				"cuentaContableEmpresa.cuentaContable", "cuentaContable.padre");
		criteria.addAliasedLeftJoins("centroCosto", "subcentroCosto");
		criteria.addEquals("asiento", asiento);
		return findByCriteria(criteria, true);
	}

	public Boolean mayorizarAsiento(Asiento asiento, int signo) {

		//Ejercicio ejercicio = getEjercicio(asiento.getEmpresa(),  asiento.getDocumento().getEmitido());
		Periodo periodoAsiento = getPeriodo(asiento.getPeriodo().getId());
		//Validar estados de periodos
//		if(periodoAsiento.getEstado().isActivo()){ TODO (vvc): descomentar estas lineas y verificar la existencia de metodo isActivo()
//			
//		}
		
		
		List<HibernateAsientoDetalle> saldos = listAsientoDetalle(asiento);
		//Este mapa alamcenara temporalemten todas las cuentas, padres e hijas usadas en el asiento
		// de tal manera que no hagamos conultas a la base para obtenerlas cada vez que las necesitemos
		Map<Long, CuentaContable> cuentasDeAsiento = new HashMap<Long, CuentaContable>();
		Map<Long, CentroCosto> centrosDeAsiento = new HashMap<Long, CentroCosto>();
		Map<CuentaContable, SaldoCuentaContable> saldosCuentaMap= new HashMap<CuentaContable, SaldoCuentaContable>();
		Map<String, SaldoCuentaCentro> saldosCuentaCentroMap= new HashMap<String, SaldoCuentaCentro>();
		Map<CentroCosto, SaldoCentroCosto> saldosCentroMap= new HashMap<CentroCosto, SaldoCentroCosto>();

		List<HibernatePeriodo> periodoList = listPeriodosPosteriores(periodoAsiento);
		for (AsientoDetalle detalle : saldos) {
			for (Periodo periodo : periodoList) {
				if(periodo.getEstado().isCerrado()) {
					// error
				}
				actualizarSaldosCuentasContbables(cuentasDeAsiento, saldosCuentaMap, periodo, detalle, signo);
				//actualizarSaldosCentrosCostos();
				actualizarSaldosCentrosCosto(centrosDeAsiento, saldosCentroMap, periodo, detalle, signo);
			}
		}
		
		Collection<SaldoCuentaContable> saldosCuenta = saldosCuentaMap.values();
		for (SaldoCuentaContable saldoCuentaContable : saldosCuenta) {
			saveOrUpdate(saldoCuentaContable);
		}
		Collection<SaldoCuentaCentro> saldosCuentaCentro = saldosCuentaCentroMap.values();
		for (SaldoCuentaCentro saldoCuentaCentro : saldosCuentaCentro) {
			saveOrUpdate(saldoCuentaCentro);
		}
		Collection<SaldoCentroCosto> saldosCentro = saldosCentroMap.values();
		for (SaldoCentroCosto saldoCentroCosto : saldosCentro) {
			saveOrUpdate(saldoCentroCosto);
		}
		return true;
	}
	
	private void actualizarSaldosCuentasContbables(Map<Long, CuentaContable> cuentasDeAsiento, Map<CuentaContable, 
			SaldoCuentaContable> mapSaldos, Periodo periodo, AsientoDetalle detalle, int signo) {
		CuentaContable cuenta = detalle.getCuentaContable();
		boolean continuar= true;
		do {
			continuar = cuenta.getPadre()!=null;
			//Verificamos si ya obtuvimos el saldo de la cuenta a afectar
			SaldoCuentaContable saldoCuenta = mapSaldos.get(cuenta);
			if(saldoCuenta==null) {
				saldoCuenta = new HibernateSaldoCuentaContable();
				saldoCuenta.setPeriodo(periodo);
				saldoCuenta.setCuentaContable(detalle.getCuentaContable());
				// sino lo tenemos lo leemos de la base por cuenta y periodo
				saldoCuenta = getSaldoCuenta(saldoCuenta);
				if(saldoCuenta==null) {
					// si no lo encontramos en la base , entonces debemos agregarlo
					saldoCuenta = new HibernateSaldoCuentaContable();
					saldoCuenta.setPeriodo(periodo);
					saldoCuenta.setCuentaContable(detalle.getCuentaContable());
				}
			}
			BigDecimal debe = detalle.getValorDebe().multiply(BigDecimal.valueOf(signo));
			BigDecimal haber = detalle.getValorHaber().multiply(BigDecimal.valueOf(signo));
			// sumamos los valore debe y haber de la cuenta
			saldoCuenta.setValorDebe(debe.add(saldoCuenta.getValorDebe()));
			saldoCuenta.setValorHaber(haber.add(saldoCuenta.getValorHaber()));
			saldoCuenta.setSaldo(saldoCuenta.getSaldoInicial().add(debe).subtract(haber));
			// agregamos al mapa el saldo de la cuenta
			mapSaldos.put(cuenta, saldoCuenta);
			//
			if(continuar) {
				CuentaContable cuentaPadre = cuentasDeAsiento.get(cuenta.getPadre().getId());
				if(cuentaPadre==null) {
					cuentaPadre = getCuentaContable(cuenta.getPadre().getId());
					cuentasDeAsiento.put(cuenta.getId(), cuenta);
				}
				cuenta = cuentaPadre;
			}
		} while (continuar); 
	}
	
	private void actualizarSaldosCentrosCosto(Map<Long, CentroCosto> centrosDeAsiento, Map<CentroCosto, 
			SaldoCentroCosto> mapSaldos, Periodo periodo, AsientoDetalle detalle, int signo) {
		CentroCosto centro = detalle.getCentroCosto();
		boolean continuar= true;
		do {
			continuar = centro.getPadre()!=null;
			//Verificamos si ya obtuvimos el saldo de la cuenta a afectar
			SaldoCentroCosto saldoCentro = mapSaldos.get(centro);
			if(saldoCentro==null) {
				saldoCentro = new HibernateSaldoCentroCosto();
				saldoCentro.setPeriodo(periodo);
				saldoCentro.setCentroCosto(detalle.getCentroCosto());
				// sino lo tenemos lo leemos de la base por cuenta y periodo
				saldoCentro = getSaldoCentro(saldoCentro);
				if(saldoCentro==null) {
					// si no lo encontramos en la base , entonces debemos agregarlo
					saldoCentro = new HibernateSaldoCentroCosto();
					saldoCentro.setPeriodo(periodo);
					saldoCentro.setCentroCosto(detalle.getCentroCosto());
				}
			}
			BigDecimal debe = detalle.getValorDebe().multiply(BigDecimal.valueOf(signo));
			BigDecimal haber = detalle.getValorHaber().multiply(BigDecimal.valueOf(signo));
			// sumamos los valore debe y haber de la cuenta
			saldoCentro.setValorDebe(debe.add(saldoCentro.getValorDebe()));
			saldoCentro.setValorHaber(haber.add(saldoCentro.getValorHaber()));
			//saldoCentro.setSaldo(saldoCentro.getSaldoInicial().add(debe).subtract(haber));
			// agregamos al mapa el saldo de la cuenta
			mapSaldos.put(centro, saldoCentro);
			//
			if(continuar) {
				CentroCosto cuentaPadre = centrosDeAsiento.get(centro.getPadre().getId());
				if(cuentaPadre==null) {
					cuentaPadre = getCentroCosto(centro.getPadre().getId());
					centrosDeAsiento.put(centro.getId(), centro);
				}
				centro = cuentaPadre;
			}
		} while (continuar); 
	}

	//
    // TipoCuenta
    //
    @Override
    public List<TipoCuenta> findTipoCuentaList(TipoCuenta tipoCuenta, Pagination pagination) {
        GenericCriteria<TipoCuenta> criteria = GenericCriteria.forClass(TipoCuenta.class);
        
        criteria.addEqualsIfNotZero(TipoCuenta_.id, tipoCuenta.getId());
        if(criteria.isChanged()){
        	return findByCriteria(criteria, pagination);
        }
        
        criteria.addEqualsIfNotNull(TipoCuenta_.tipo, tipoCuenta.getTipo());
        criteria.addEqualsIfNotNull(TipoCuenta_.naturaleza, tipoCuenta.getNaturaleza());
        criteria.addLikeIfNotNull(TipoCuenta_.codigo, tipoCuenta.getCodigo());
        criteria.addLikeIfNotNull(TipoCuenta_.descripcion, tipoCuenta.getDescripcion());        
        
        return findByCriteria(criteria, pagination);
    }

    @Override
    public TipoCuenta createTipoCuenta() {
        TipoCuenta tipoCuenta = new HibernateTipoCuenta();
        return tipoCuenta;
    }

    @Override
    public TipoCuenta saveTipoCuenta(TipoCuenta tipoCuenta) {
        tipoCuenta = saveOrMerge(tipoCuenta);
        putSuccess("tipoCuenta.success.save", tipoCuenta.getId());
        return tipoCuenta;
    }

    @Override
    public void deleteTipoCuenta(TipoCuenta tipoCuenta) {
        Long id = tipoCuenta.getId();
        delete(tipoCuenta);
        putSuccess("tipoCuenta.success.delete",id);
    }
}
