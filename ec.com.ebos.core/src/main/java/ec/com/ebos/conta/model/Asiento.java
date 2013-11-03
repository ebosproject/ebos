package ec.com.ebos.conta.model;

import java.util.Set;

import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.master.model.TipoDocumento;

public interface Asiento {

	public Set<AsientoDetalle> getAsientoDetalleList();

	public String getCodigo();

	public String getComentario();

	public String getDescripcion();

	public Documento getDocumento();

	public Organizacion getEmpresa();

	public Long getId();

	public Periodo getPeriodo();

	public Organizacion getPuntoVenta();

	public int getSecuencia();

	public TipoAsiento getTipoAsiento();

	public TipoDocumento getTipoDocumento();

	public boolean isCuadrado();

	public void setAsientoDetalleList(Set<AsientoDetalle> asientoDetalleList);

	public void setCodigo(String codigo);

	public void setComentario(String comentario);

	public void setCuadrado(boolean cuadrado);

	public void setDescripcion(String descripcion);

	public void setDocumento(Documento documento);

	public void setEmpresa(Organizacion empresa);

	public void setId(Long id);

	public void setPeriodo(Periodo periodo);

	public void setPuntoVenta(Organizacion puntoVenta);

	public void setSecuencia(int secuencia);

	public void setTipoAsiento(TipoAsiento tipoAsiento);

	public void setTipoDocumento(TipoDocumento tipoDocumento);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}