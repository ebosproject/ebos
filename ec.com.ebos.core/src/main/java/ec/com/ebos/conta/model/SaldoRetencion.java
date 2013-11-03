package ec.com.ebos.conta.model;

import java.math.BigDecimal;

import ec.com.ebos.admin.model.TipoRetencion;
import ec.com.ebos.root.model.Auditoria;

public interface SaldoRetencion {

	public Auditoria getAuditoria();

	public BigDecimal getBaseImponible();

	public Long getId();

	public Periodo getPeriodo();

	public TipoRetencion getTipoRetencion();

	public BigDecimal getValorRetenido();

	public void setAuditoria(Auditoria auditoria);

	public void setBaseImponible(BigDecimal baseImponible);

	public void setId(Long id);

	public void setPeriodo(Periodo periodo);

	public void setTipoRetencion(TipoRetencion tipoRetencion);

	public void setValorRetenido(BigDecimal valorRetenido);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}