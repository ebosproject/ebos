package ec.com.ebos.security.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.hibernate.HibernateRolOpcion;
import ec.com.ebos.security.model.hibernate.HibernateUsuarioRol;

public interface Rol {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public String getNombre();

	public Set<HibernateRolOpcion> getRolOpcionList();

	public Set<HibernateUsuarioRol> getUsuarioRolList();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setNombre(String nombre);

	public void setRolOpcionList(Set<HibernateRolOpcion> rolOpcionList);

	public void setUsuarioRolList(Set<HibernateUsuarioRol> usuarioRolList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}