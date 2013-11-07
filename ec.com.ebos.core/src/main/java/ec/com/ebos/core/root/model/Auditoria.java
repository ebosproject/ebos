package ec.com.ebos.core.root.model;

import java.util.Date;

import ec.com.ebos.core.security.model.Usuario;

public interface Auditoria extends Root{
	
	public Auditoria getAuditoria();

	public void setAuditoria(Auditoria auditoria);

	public Usuario getCreador();

	public void setCreador(Usuario creador);

	public Usuario getModificador();

	public void setModificador(Usuario modificador);

	public Date getCreado();

	public void setCreado(Date creado);

	public Date getModificado();

	public void setModificado(Date modificado);

}