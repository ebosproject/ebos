package ec.com.ebos.core.mse.model;

import java.util.Set;

import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface Monaguillo extends Entidad {

	public Auditoria getAuditoria();

	public String getCentroEstudio();

	public Entidad.Estado getEstado();

	public Long getId();

	public Set<MonaguilloGrupo> getMonaguilloGrupoList();

	public Persona getPersona();

	public String getRepresentantes();

	public void setAuditoria(Auditoria auditoria);

	public void setCentroEstudio(String centroEstudio);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setMonaguilloGrupoList(Set<MonaguilloGrupo> monaguilloGrupoList);

	public void setPersona(Persona persona);

	public void setRepresentantes(String representantes);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}