package ec.com.ebos.core.conta.model;

import java.math.BigDecimal;

import ec.com.ebos.core.admin.model.Documento;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface DocumentoDistrubucion extends Contabilidad {

	public Auditoria getAuditoria();

	public CentroCosto getCentroCosto();

	public Documento getDocumento();

	public Entidad.Estado getEstado();

	public Long getId();

	public CentroCosto getSubcentroCosto();

	public BigDecimal getValor();

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCosto(CentroCosto centroCosto);

	public void setDocumento(Documento documento);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setSubcentroCosto(CentroCosto subcentroCosto);

	public void setValor(BigDecimal valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}