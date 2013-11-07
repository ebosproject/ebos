package ec.com.ebos.core.conta.model;

import java.math.BigDecimal;

import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface SaldoCuentaCentro extends Contabilidad {

	public Auditoria getAuditoria();

	public CentroCosto getCentroCosto();

	public CuentaContable getCuentaContable();

	public Entidad.Estado getEstado();

	public Long getId();

	public Periodo getPeriodo();

	public BigDecimal getSaldo();

	public BigDecimal getSaldoInicial();

	public CentroCosto getSubcentroCosto();

	public BigDecimal getValorDebe();

	public BigDecimal getValorHaber();

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCosto(CentroCosto centroCosto);

	public void setCuentaContable(CuentaContable cuentaContable);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPeriodo(Periodo periodo);

	public void setSaldo(BigDecimal saldo);

	public void setSaldoInicial(BigDecimal saldoInicial);

	public void setSubcentroCosto(CentroCosto subcentroCosto);

	public void setValorDebe(BigDecimal valorDebe);

	public void setValorHaber(BigDecimal valorHaber);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}