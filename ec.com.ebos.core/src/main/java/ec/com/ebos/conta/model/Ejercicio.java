package ec.com.ebos.conta.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import ec.com.ebos.master.model.Organizacion;
import ec.com.ebos.root.model.Entidad;

public interface Ejercicio extends Contabilidad {

	public String getCodigo();

	public Date getCreacion();

	public CuentaContable getCuentaUtilidad();

	public String getDescripcion();

	public Organizacion getEmpresa();

	public Entidad.Estado getEstado();

	public Date getFechaInicial();

	public Long getId();

	public Set<Periodo> getPeriodoList();

	public Set<Periodo> getPeriodoList2();

	public BigDecimal getValor();

	public void setCodigo(String codigo);

	public void setCreacion(Date creacion);

	public void setCuentaUtilidad(CuentaContable cuentaUtilidad);

	public void setDescripcion(String descripcion);

	public void setEmpresa(Organizacion empresa);

	public void setEstado(Entidad.Estado estado);

	public void setFechaInicial(Date fechaInicial);

	public void setId(Long id);

	public void setPeriodoList(Set<Periodo> periodoList);

	public void setPeriodoList2(Set<Periodo> periodoList2);

	public void setValor(BigDecimal valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}