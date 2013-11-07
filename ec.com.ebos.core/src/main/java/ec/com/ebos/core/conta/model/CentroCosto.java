package ec.com.ebos.core.conta.model;

import java.util.Set;

import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;

public interface CentroCosto extends Contabilidad{

	public Set<AsientoDetalle> getAsientoDetalleList();

	public Set<AsientoDetalle> getAsientoDetalleList2();

	public Auditoria getAuditoria();

	public Set<CentroCostoEmpresa> getCentroCostoEmpresaList();

	public Set<CentroCosto> getCentroCostoList();

	public Set<CentroSubcentro> getCentroSubcentroList();

	public Set<CentroSubcentro> getCentroSubcentroList2();

	public String getCodigo();

	public String getCodigoFormato();

	public String getDescripcion();

	public Set<DocumentoDistribucion> getDocumentoDistribucionList();

	public Set<DocumentoDistribucion> getDocumentoDistribucionList2();

	public Entidad.Estado getEstado();

	public Long getId();

	public int getNivel();

	public CentroCosto getPadre();

	public Set<SaldoCentroCosto> getSaldoCentroCostoList();

	public Set<SaldoCuentaCentro> getSaldoCuentaCentroList();

	public Set<SaldoCuentaCentro> getSaldoCuentaCentroList2();

	public int getTerminante();

	public TipoCentroCosto getTipoCentroCosto();

	public boolean isPideSubcentroCosto();

	public void setAsientoDetalleList(Set<AsientoDetalle> asientoDetalleList);

	public void setAsientoDetalleList2(Set<AsientoDetalle> asientoDetalleList2);

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCostoEmpresaList(Set<CentroCostoEmpresa> centroCostoEmpresaList);

	public void setCentroCostoList(Set<CentroCosto> centroCostoList);

	public void setCentroSubcentroList(Set<CentroSubcentro> centroSubcentroList);

	public void setCentroSubcentroList2(Set<CentroSubcentro> centroSubcentroList2);

	public void setCodigo(String codigo);

	public void setCodigoFormato(String codigoFormato);

	public void setDescripcion(String descripcion);

	public void setDocumentoDistribucionList(Set<DocumentoDistribucion> documentoDistribucionList);

	public void setDocumentoDistribucionList2(Set<DocumentoDistribucion> documentoDistribucionList2);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setNivel(int nivel);

	public void setPadre(CentroCosto padre);

	public void setPideSubcentroCosto(boolean pideSubcentroCosto);

	public void setSaldoCentroCostoList(Set<SaldoCentroCosto> saldoCentroCostoList);

	public void setSaldoCuentaCentroList(Set<SaldoCuentaCentro> saldoCuentaCentroList);

	public void setSaldoCuentaCentroList2(Set<SaldoCuentaCentro> saldoCuentaCentroList2);

	public void setTerminante(int terminante);

	public void setTipoCentroCosto(TipoCentroCosto tipoCentroCosto);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}