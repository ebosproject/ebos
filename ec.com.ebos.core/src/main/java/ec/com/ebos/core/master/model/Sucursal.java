package ec.com.ebos.core.master.model;

import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface Sucursal extends Master {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public Organizacion getEmpresa();

	public Entidad.Estado getEstado();

	public Long getId();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEmpresa(Organizacion empresa);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}