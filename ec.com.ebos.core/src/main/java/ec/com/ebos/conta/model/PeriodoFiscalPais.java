package ec.com.ebos.conta.model;

import ec.com.ebos.master.model.DivisionGeografica;

public interface PeriodoFiscalPais extends Contabilidad {

	public String getCodigo();

	public String getDescripcion();

	public DivisionGeografica getDivisionGeografica();

	public Long getId();

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setDivisionGeografica(DivisionGeografica divisionGeografica);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}