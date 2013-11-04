package ec.com.ebos.conta.model;

import java.util.Set;

import ec.com.ebos.root.model.Entidad;

public interface TipoCentroCosto extends Contabilidad {

	public Set<CentroCosto> getCentroCostoList();

	public Set<CentroSubcentro> getCentroSubcentroList();

	public String getCodigo();

	public Set<CuentaCentro> getCuentaCentroList();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public boolean isDistribucion();

	public void setCentroCostoList(Set<CentroCosto> centroCostoList);

	public void setCentroSubcentroList(Set<CentroSubcentro> centroSubcentroList);

	public void setCodigo(String codigo);

	public void setCuentaCentroList(Set<CuentaCentro> cuentaCentroList);

	public void setDescripcion(String descripcion);

	public void setDistribucion(boolean distribucion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}