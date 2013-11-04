package ec.com.ebos.security.model;

import java.util.Date;
import java.util.Set;

import ec.com.ebos.admin.model.Bundle.Localidad;
import ec.com.ebos.master.model.EmpresaPersona;
import ec.com.ebos.root.model.Auditoria;

public interface Usuario extends Security {

	public Auditoria getAuditoria();

	public String getConfpassword();

	public EmpresaPersona getEmpresaPersona();

	public Estado getEstado();

	public Long getId();

	public Localidad getLocalidad();

	public int getMaxOptions();

	public String getNewpassword();

	public String getPassword();

	public String getTema();

	public String getUsername();

	public Set<UsuarioRol> getUsuarioRolList();

	public void setAuditoria(Auditoria auditoria);

	public void setConfpassword(String confpassword);

	public void setEmpresaPersona(EmpresaPersona empresaPersona);

	public void setEstado(Estado estado);

	public void setId(Long id);

	public void setLocalidad(Localidad localidad);

	public void setMaxOptions(int maxOptions);

	public void setNewpassword(String newpassword);

	public void setPassword(String password);

	public void setTema(String tema);

	public void setUsername(String username);

	public void setUsuarioRolList(Set<UsuarioRol> usuarioRolList);

	public Date getCreado();

}