package ec.com.ebos.admin.model;

import java.util.Date;
import java.util.Set;

import ec.com.ebos.conta.model.hibernate.HibernateAsiento;
import ec.com.ebos.conta.model.hibernate.HibernateAsientoDetalle;
import ec.com.ebos.conta.model.hibernate.HibernateDocumentoDistribucion;

public interface Documento {

	public Set<HibernateAsientoDetalle> getAsientoDetalleList();

	public Set<HibernateAsiento> getAsientoList();

	public Date getAutorizado();

	public Date getContabilizado();

	public Set<HibernateDocumentoDistribucion> getDocumentoDistribucionList();

	public Date getEmitido();

	public Date getEnviado();

	public Long getId();

	public Date getProcesado();

	public Date getVencimiento();

	public void setAsientoDetalleList(Set<HibernateAsientoDetalle> asientoDetalleList);

	public void setAsientoList(Set<HibernateAsiento> asientoList);

	public void setAutorizado(Date autorizado);

	public void setContabilizado(Date contabilizado);

	public void setDocumentoDistribucionList(
			Set<HibernateDocumentoDistribucion> documentoDistribucionList);

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