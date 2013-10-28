package ec.com.ebos.root.model;

import java.util.Date;

import ec.com.ebos.security.model.Usuario;

public interface Auditoria {

	public Usuario getCreador();

	public void setCreador(Usuario creador);

	public Usuario getModificador();

	public void setModificador(Usuario modificador);

	public Date getCreado();

	public void setCreado(Date creado);

	public Date getModificado();

	public void setModificado(Date modificado);

}