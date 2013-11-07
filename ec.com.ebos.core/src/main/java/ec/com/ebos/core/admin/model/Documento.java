package ec.com.ebos.core.admin.model;

import java.util.Date;
import java.util.Set;

import ec.com.ebos.core.conta.model.Asiento;
import ec.com.ebos.core.conta.model.AsientoDetalle;
import ec.com.ebos.core.conta.model.DocumentoDistribucion;

public interface Documento extends Administracion{

	public Set<AsientoDetalle> getAsientoDetalleList();

	public Set<Asiento> getAsientoList();

	public Date getAutorizado();

	public Date getContabilizado();

	public Set<DocumentoDistribucion> getDocumentoDistribucionList();

	public Date getEmitido();

	public Date getEnviado();

	public Long getId();

	public Date getProcesado();

	public Date getVencimiento();

	public void setAsientoDetalleList(Set<AsientoDetalle> asientoDetalleList);

	public void setAsientoList(Set<Asiento> asientoList);

	public void setAutorizado(Date autorizado);

	public void setContabilizado(Date contabilizado);

	public void setDocumentoDistribucionList(
			Set<DocumentoDistribucion> documentoDistribucionList);

	public void setEmitido(Date emitido);

	public void setEnviado(Date enviado);

	public void setId(Long id);

	public void setProcesado(Date procesado);

	public void setVencimiento(Date vencimiento);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}