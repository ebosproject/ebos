package ec.com.ebos.admin.model;

import java.util.Date;

import ec.com.ebos.root.model.Entidad;

public interface Parametros extends Administracion {

	public String getClave();

	public Entidad.Estado getEstado();

	public String getGrupo();

	public String getValor();

	public void setClave(String clave);

	public void setEstado(Entidad.Estado estado);

	public void setGrupo(String grupo);

	public void setValor(String valor);

	public void setModificado(Date fecha);

	public void setCreado(Date fecha);

}