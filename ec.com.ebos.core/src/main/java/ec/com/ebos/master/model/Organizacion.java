package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.conta.model.hibernate.HibernateCentroCostoEmpresa;
import ec.com.ebos.conta.model.hibernate.HibernateCuentaCentro;
import ec.com.ebos.conta.model.hibernate.HibernateCuentaContableEmpresa;
import ec.com.ebos.conta.model.hibernate.HibernateEjercicio;
import ec.com.ebos.master.model.hibernate.HibernateActivo;
import ec.com.ebos.master.model.hibernate.HibernateEmpresaPersona;
import ec.com.ebos.master.model.hibernate.HibernateSucursal;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Organizacion {

	public Set<HibernateActivo> getActivoList();

	public Auditoria getAuditoria();

	public Set<HibernateCentroCostoEmpresa> getCentroCostoEmpresaList();

	public Set<HibernateCuentaCentro> getCuentaCentroList();

	public Set<HibernateCuentaContableEmpresa> getCuentaContableEmpresaList();

	public String getDescripcion();

	public Set<HibernateEjercicio> getEjercicioList();

	public Set<HibernateEmpresaPersona> getEmpresaPersonaList();

	public Entidad.Estado getEstado();

	public Long getId();

	public String getImagen();

	public Persona getPersona();

	public Set<HibernateSucursal> getSucursalList();

	public void setActivoList(Set<HibernateActivo> activoList);

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCostoEmpresaList(
			Set<HibernateCentroCostoEmpresa> centroCostoEmpresaList);

	public void setCuentaCentroList(Set<HibernateCuentaCentro> cuentaCentroList);

	public void setCuentaContableEmpresaList(
			Set<HibernateCuentaContableEmpresa> cuentaContableEmpresaList);

	public void setDescripcion(String descripcion);

	public void setEjercicioList(Set<HibernateEjercicio> ejercicioList);

	public void setEmpresaPersonaList(
			Set<HibernateEmpresaPersona> empresaPersonaList);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setImagen(String imagen);

	public void setPersona(Persona persona);

	public void setSucursalList(Set<HibernateSucursal> sucursalList);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}