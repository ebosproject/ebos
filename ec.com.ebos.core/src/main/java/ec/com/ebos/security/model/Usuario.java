package ec.com.ebos.security.model;

import java.util.Set;

import ec.com.ebos.master.model.EmpresaPersona;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.util.type.StringValuedEnum;

public interface Usuario {

	public Auditoria getAuditoria();

	public String getConfpassword();

	public EmpresaPersona getEmpresaPersona();

	public StringValuedEnum<?> getEstado();

	public Long getId();

	public StringValuedEnum<?> getLocalidad();

	public int getMaxOptions();

	public String getNewpassword();

	public String getPassword();

	public String getTema();

	public String getUsername();

	public Set<UsuarioRol> getUsuarioRolList();

	public void setAuditoria(Auditoria auditoria);

	public void setConfpassword(String confpassword);

	public void setEmpresaPersona(EmpresaPersona empresaPersona);

	public void setEstado(StringValuedEnum<?> estado);

	public void setId(Long id);

	public void setLocalidad(StringValuedEnum<?> localidad);

	public void setMaxOptions(int maxOptions);

	public void setNewpassword(String newpassword);

	public void setPassword(String password);

	public void setTema(String tema);

	public void setUsername(String username);

	public void setUsuarioRolList(Set<UsuarioRol> usuarioRolList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}