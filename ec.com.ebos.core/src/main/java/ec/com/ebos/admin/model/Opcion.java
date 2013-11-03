package ec.com.ebos.admin.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.RolOpcion;

public interface Opcion {

	public Auditoria getAuditoria();

	public String getBeanName();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public String getEtiqueta();

	public int getHeight();

	public String getIcono();

	public Long getId();

	public String getNombre();

	public Objeto getObjeto();

	public Set<Opcion> getOpcionList();

	public Opcion getPadre();

	public Set<RolOpcion> getRolOpcionList();

	public String getTarget();

	public int getWidth();

	public void setAuditoria(Auditoria auditoria);

	public void setBeanName(String beanName);

	public void setDescripcion(String descripcion);

	public void setEstado(Entidad.Estado estado);

	public void setEtiqueta(String etiqueta);

	public void setHeight(int height);

	public void setIcono(String icono);

	public void setId(Long id);

	public void setNombre(String nombre);

	public void setObjeto(Objeto objeto);

	public void setOpcionList(Set<Opcion> opcionList);

	public void setPadre(Opcion padre);

	public void setRolOpcionList(Set<RolOpcion> rolOpcionList);

	public void setTarget(String target);

	public void setWidth(int width);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}