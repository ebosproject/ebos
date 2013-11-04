package ec.com.ebos.bitacora.model;

import ec.com.ebos.root.model.Auditoria;

public interface Turno extends Bitacora {

	public Auditoria getAuditoria();

	public Long getId();

	public void setAuditoria(Auditoria auditoria);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}