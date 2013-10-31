package ec.com.ebos.master.model;

import ec.com.ebos.master.model.hibernate.HibernateTipoImpuesto.ClaseImpuesto;
import ec.com.ebos.root.model.Auditoria;

public interface TipoImpuesto {

	public Auditoria getAuditoria();

	public ClaseImpuesto getClaseImpuesto();

	public String getCodigo();

	public String getDescripcion();

	public Long getId();

	public void setAuditoria(Auditoria auditoria);

	public void setClaseImpuesto(ClaseImpuesto claseImpuesto);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}