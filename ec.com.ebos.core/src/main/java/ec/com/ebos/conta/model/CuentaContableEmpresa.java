package ec.com.ebos.conta.model;

import ec.com.ebos.master.model.Organizacion;

public interface CuentaContableEmpresa {

	public CuentaContable getCuentaContable();

	public Organizacion getEmpresa();

	public Long getId();

	public void setCuentaContable(CuentaContable cuentaContable);

	public void setEmpresa(Organizacion empresa);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}