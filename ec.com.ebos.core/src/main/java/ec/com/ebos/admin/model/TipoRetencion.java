package ec.com.ebos.admin.model;

import java.util.Set;

import ec.com.ebos.conta.model.SaldoRetencion;

public interface TipoRetencion {

	public Long getId();

	public Set<SaldoRetencion> getSaldoRetencionList();

	public void setId(Long id);

	public void setSaldoRetencionList(Set<SaldoRetencion> saldoRetencionList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}