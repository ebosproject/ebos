package ec.com.ebos.security.model;

import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface RolOpcion extends Security {

	public Auditoria getAuditoria();

	public Entidad.Estado getEstado();

	public Long getId();

	public Opcion getOpcion();

	public Rol getRol();

	public boolean isCrear();

	public boolean isEditar();

	public boolean isEliminar();

	public boolean isExportar();

	public void setAuditoria(Auditoria auditoria);

	public void setCrear(boolean crear);

	public void setEditar(boolean editar);

	public void setEliminar(boolean eliminar);

	public void setEstado(Entidad.Estado estado);

	public void setExportar(boolean exportar);

	public void setId(Long id);

	public void setOpcion(Opcion opcion);

	public void setRol(Rol rol);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}