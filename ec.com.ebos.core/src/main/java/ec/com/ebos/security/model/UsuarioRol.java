package ec.com.ebos.security.model;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface UsuarioRol {

	public Auditoria getAuditoria();

	public Entidad.Estado getEstado();

	public Long getId();

	public Rol getRol();

	public Usuario getUsuario();

	public void setAuditoria(Auditoria auditoria);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setRol(Rol rol);

	public void setUsuario(Usuario usuario);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}