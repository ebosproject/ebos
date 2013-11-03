package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.root.model.Entidad;

public interface ActivoCategoria {

	public Set<Activo> getActivoList();

	public Set<Categoria> getCategoriaList();

	public String getCodigo();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public ActivoCategoria getPadre();

	public void setActivoList(Set<Activo> activoList);

	public void setCategoriaList(Set<Categoria> categoriaList);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPadre(ActivoCategoria padre);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}