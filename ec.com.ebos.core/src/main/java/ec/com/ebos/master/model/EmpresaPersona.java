package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.security.model.Usuario;

public interface EmpresaPersona extends Master {

	public Set<ActivoCustodio> getActivoCustodioList();

	public Auditoria getAuditoria();

	public Organizacion getEmpresa();

	public Entidad.Estado getEstado();

	public Long getId();

	public Persona getPersona();

	public Set<Usuario> getUsuarioList();

	public void setActivoCustodioList(Set<ActivoCustodio> activoCustodioList);

	public void setAuditoria(Auditoria auditoria);

	public void setEmpresa(Organizacion empresa);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPersona(Persona persona);

	public void setUsuarioList(Set<Usuario> usuarioList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}