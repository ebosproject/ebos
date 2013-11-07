package ec.com.ebos.core.conta.model;

import ec.com.ebos.core.root.model.Entidad;

public interface CentroSubcentro extends Contabilidad{

	public CentroCosto getCentroCosto();

	public Entidad.Estado getEstado();

	public Long getId();

	public CentroCosto getSubcentroCosto();

	public TipoCentroCosto getTipoCentroCosto();

	public void setCentroCosto(CentroCosto centroCosto);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setSubcentroCosto(CentroCosto subcentroCosto);

	public void setTipoCentroCosto(TipoCentroCosto tipoCentroCosto);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}