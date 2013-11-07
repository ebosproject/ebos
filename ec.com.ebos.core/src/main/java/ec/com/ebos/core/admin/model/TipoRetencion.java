package ec.com.ebos.core.admin.model;

import java.util.Set;

import ec.com.ebos.core.conta.model.SaldoRetencion;

public interface TipoRetencion extends Administracion{

	public Long getId();

	public Set<SaldoRetencion> getSaldoRetencionList();

	public void setId(Long id);

	public void setSaldoRetencionList(Set<SaldoRetencion> saldoRetencionList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}