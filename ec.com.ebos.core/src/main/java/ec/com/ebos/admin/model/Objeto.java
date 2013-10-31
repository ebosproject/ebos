package ec.com.ebos.admin.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Objeto {

	public Auditoria getAuditoria();

	public String getCodigo();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public Long getId();

	public Set<HibernateOpcion> getOpcionList();

	public HibernateObjeto.TipoObjeto getTipo();

	public void setAuditoria(Auditoria auditoria);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setOpcionList(Set<HibernateOpcion> opcionList);

	public void setTipo(HibernateObjeto.TipoObjeto tipo);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}