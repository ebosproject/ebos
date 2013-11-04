package ec.com.ebos.conta.model;

import ec.com.ebos.master.model.Organizacion;

public interface CentroCostoEmpresa extends Contabilidad{

	public CentroCosto getCentroCosto();

	public Organizacion getEmpresa();

	public Long getId();

	public void setCentroCosto(CentroCosto centroCosto);

	public void setEmpresa(Organizacion empresa);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}