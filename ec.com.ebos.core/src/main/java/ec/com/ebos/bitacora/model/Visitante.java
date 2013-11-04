package ec.com.ebos.bitacora.model;

import ec.com.ebos.root.model.Auditoria;

public interface Visitante extends Bitacora {

	public String getApellidos();

	public Auditoria getAuditoria();

	public String getCedula();

	public Long getId();

	public String getNombre();

	public void setApellidos(String apellidos);

	public void setAuditoria(Auditoria auditoria);

	public void setCedula(String cedula);

	public void setId(Long id);

	public void setNombre(String nombre);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}