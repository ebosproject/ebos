package ec.com.ebos.core.conta.model;

import java.util.Set;

import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface TipoAsiento extends Contabilidad {

	public Set<Asiento> getAsientoList();

	public Auditoria getAuditoria();

	public String getCodigo();

	public String getDescripcion();

	public Entidad.Estado getEstado();

	public String getFormatoReferencia();

	public Long getId();

	public boolean isDistribucion();

	public void setAsientoList(Set<Asiento> asientoList);

	public void setAuditoria(Auditoria auditoria);

	public void setCodigo(String codigo);

	public void setDescripcion(String descripcion);

	public void setDistribucion(boolean distribucion);

	public void setEstado(Entidad.Estado estado);

	public void setFormatoReferencia(String formatoReferencia);

	public void setId(Long id);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}