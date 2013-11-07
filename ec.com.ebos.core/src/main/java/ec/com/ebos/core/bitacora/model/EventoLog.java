package ec.com.ebos.core.bitacora.model;

import ec.com.ebos.core.root.model.Auditoria;

public interface EventoLog extends Bitacora {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public Evento getEvento();

	public Long getId();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEvento(Evento evento);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}