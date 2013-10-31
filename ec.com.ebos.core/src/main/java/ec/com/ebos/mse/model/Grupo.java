package ec.com.ebos.mse.model;

import java.util.Set;

import ec.com.ebos.mse.model.hibernate.HibernateMonaguilloGrupo;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Grupo {

	public Set<HibernateAsientoDetalle> getAsientoDetalleList();

	public Auditoria getAuditoria();

	public Entidad.Estado getEstado();

	public Long getId();

	public Set<HibernateMonaguilloGrupo> getMonaguilloGrupoList();

	public String getNombre();

	public void setAsientoDetalleList(
			Set<HibernateAsientoDetalle> asientoDetalleList);

	public void setAuditoria(Auditoria auditoria);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setMonaguilloGrupoList(
			Set<HibernateMonaguilloGrupo> monaguilloGrupoList);

	public void setNombre(String nombre);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}