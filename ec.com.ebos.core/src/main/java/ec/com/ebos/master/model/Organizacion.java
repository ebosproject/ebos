package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.conta.model.CentroCostoEmpresa;
import ec.com.ebos.conta.model.CuentaCentro;
import ec.com.ebos.conta.model.CuentaContableEmpresa;
import ec.com.ebos.conta.model.Ejercicio;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Organizacion {

	public Set<Activo> getActivoList();

	public Auditoria getAuditoria();

	public Set<CentroCostoEmpresa> getCentroCostoEmpresaList();

	public Set<CuentaCentro> getCuentaCentroList();

	public Set<CuentaContableEmpresa> getCuentaContableEmpresaList();

	public String getDescripcion();

	public Set<Ejercicio> getEjercicioList();

	public Set<EmpresaPersona> getEmpresaPersonaList();

	public Entidad.Estado getEstado();

	public Long getId();

	public String getImagen();

	public Persona getPersona();

	public Set<Sucursal> getSucursalList();

	public void setActivoList(Set<Activo> activoList);

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCostoEmpresaList(Set<CentroCostoEmpresa> centroCostoEmpresaList);

	public void setCuentaCentroList(Set<CuentaCentro> cuentaCentroList);

	public void setCuentaContableEmpresaList(Set<CuentaContableEmpresa> cuentaContableEmpresaList);

	public void setDescripcion(String descripcion);

	public void setEjercicioList(Set<Ejercicio> ejercicioList);

	public void setEmpresaPersonaList(Set<EmpresaPersona> empresaPersonaList);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setImagen(String imagen);

	public void setPersona(Persona persona);

	public void setSucursalList(Set<Sucursal> sucursalList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}