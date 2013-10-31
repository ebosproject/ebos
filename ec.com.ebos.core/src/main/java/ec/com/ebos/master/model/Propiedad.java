package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.master.model.hibernate.HibernatePropiedad;
import ec.com.ebos.master.model.hibernate.HibernatePropiedadValor;
import ec.com.ebos.root.model.Entidad;

public interface Propiedad {

	public Activo getActivo();

	public ActivoCategoria getCategoria();

	public Entidad.Estado getEstado();

	public Long getId();

	public int getLongitud();

	public Set<HibernatePropiedadValor> getPropiedadValorList();

	public HibernatePropiedad.TipoDato getTipoDato();

	public String getValor();

	public String getValorDefecto();

	public boolean isLista();

	public boolean isRequerido();

	public void setActivo(Activo activo);

	public void setCategoria(ActivoCategoria categoria);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setLista(boolean lista);

	public void setLongitud(int longitud);

	public void setPropiedadValorList(
			Set<HibernatePropiedadValor> propiedadValorList);

	public void setRequerido(boolean requerido);

	public void setTipoDato(HibernatePropiedad.TipoDato tipoDato);

	public void setValor(String valor);

	public void setValorDefecto(String valorDefecto);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}