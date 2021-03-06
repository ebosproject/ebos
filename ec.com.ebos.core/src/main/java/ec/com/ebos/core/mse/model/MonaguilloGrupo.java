package ec.com.ebos.core.mse.model;

import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface MonaguilloGrupo extends Entidad {

	public Auditoria getAuditoria();

	public Entidad.Estado getEstado();

	public Grupo getGrupo();

	public Long getId();

	public Monaguillo getMonaguillo();

	public void setAuditoria(Auditoria auditoria);

	public void setEstado(Entidad.Estado estado);

	public void setGrupo(Grupo grupo);

	public void setId(Long id);

	public void setMonaguillo(Monaguillo monaguillo);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}