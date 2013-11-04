package ec.com.ebos.conta.model;

import java.math.BigDecimal;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface SaldoCuentaContable extends Contabilidad {

	public Auditoria getAuditoria();

	public CuentaContable getCuentaContable();

	public Entidad.Estado getEstado();

	public Long getId();

	public Periodo getPeriodo();

	public BigDecimal getSaldo();

	public BigDecimal getSaldoInicial();

	public BigDecimal getValorDebe();

	public BigDecimal getValorHaber();

	public void setAuditoria(Auditoria auditoria);

	public void setCuentaContable(CuentaContable cuentaContable);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPeriodo(Periodo periodo);

	public void setSaldo(BigDecimal saldo);

	public void setSaldoInicial(BigDecimal saldoInicial);

	public void setValorDebe(BigDecimal valorDebe);

	public void setValorHaber(BigDecimal valorHaber);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}