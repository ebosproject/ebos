package ec.com.ebos.root.model;

import java.util.Date;

import ec.com.ebos.security.model.hibernate.HibernateUsuario;

public interface Auditoria {

	public HibernateUsuario getCreador();

	public void setCreador(HibernateUsuario creador);

	public HibernateUsuario getModificador();

	public void setModificador(HibernateUsuario modificador);

	public Date getCreado();

	public void setCreado(Date creado);

	public Date getModificado();

	public void setModificado(Date modificado);

}