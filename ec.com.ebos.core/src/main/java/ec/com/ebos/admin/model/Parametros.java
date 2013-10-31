package ec.com.ebos.admin.model;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Parametros {

	public Auditoria getAuditoria();

	public String getClave();

	public Entidad.Estado getEstado();

	public String getGrupo();

	public Long getId();

	public String getValor();

	public void setAuditoria(Auditoria auditoria);

	public void setClave(String clave);

	public void setEstado(Entidad.Estado estado);

	public void setGrupo(String grupo);

	public void setId(Long id);

	public void setValor(String valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}