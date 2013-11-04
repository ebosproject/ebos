package ec.com.ebos.security.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Rol extends Security {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public String getNombre();

	public Set<RolOpcion> getRolOpcionList();

	public Set<UsuarioRol> getUsuarioRolList();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setNombre(String nombre);

	public void setRolOpcionList(Set<RolOpcion> rolOpcionList);

	public void setUsuarioRolList(Set<UsuarioRol> usuarioRolList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}