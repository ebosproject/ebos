package ec.com.ebos.core.conta.model;

import java.math.BigDecimal;

import ec.com.ebos.core.admin.model.Documento;
import ec.com.ebos.core.root.model.Auditoria;

public interface AsientoDetalle extends Contabilidad{

	public Asiento getAsiento();

	public Auditoria getAuditoria();

	public CentroCosto getCentroCosto();

	public CuentaContable getCuentaContable();

	public String getDescripcion();

	public Documento getDocumento();

	public Long getId();

	public CentroCosto getSubcentroCosto();

	public BigDecimal getValorDebe();

	public BigDecimal getValorHaber();

	public void setAsiento(Asiento asiento);

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCosto(CentroCosto centroCosto);

	public void setCuentaContable(CuentaContable cuentaContable);

	public void setDescripcion(String descripcion);

	public void setDocumento(Documento documento);

	public void setId(Long id);

	public void setSubcentroCosto(CentroCosto subcentroCosto);

	public void setValorDebe(BigDecimal valorDebe);

	public void setValorHaber(BigDecimal valorHaber);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}