package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.master.model.hibernate.HibernateActivoCustodio;
import ec.com.ebos.master.model.hibernate.HibernatePropiedad;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Activo {

	public Set<HibernateActivoCustodio> getActivoCustodioList();

	public Auditoria getAuditoria();

	public ActivoCategoria getCategoria();

	public String getDescripcion();

	public Organizacion getEmpresa();

	public Entidad.Estado getEstado();

	public Long getId();

	public Set<HibernatePropiedad> getPropiedadList();

	public void setActivoCustodioList(
			Set<HibernateActivoCustodio> activoCustodioList);

	public void setAuditoria(Auditoria auditoria);

	public void setCategoria(ActivoCategoria categoria);

	public void setDescripcion(String descripcion);

	public void setEmpresa(Organizacion empresa);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPropiedadList(Set<HibernatePropiedad> propiedadList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}