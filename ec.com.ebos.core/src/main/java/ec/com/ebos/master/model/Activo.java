package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Activo extends Master {

	public Set<ActivoCustodio> getActivoCustodioList();

	public ActivoCategoria getCategoria();

	public String getDescripcion();

	public Organizacion getEmpresa();

	public Entidad.Estado getEstado();

	public Set<Propiedad> getPropiedadList();

	public void setActivoCustodioList(Set<ActivoCustodio> activoCustodioList);

	public void setDescripcion(String descripcion);

	public void setEmpresa(Organizacion empresa);

	public void setEstado(Entidad.Estado estado);

	public void setPropiedadList(Set<Propiedad> propiedadList);



}