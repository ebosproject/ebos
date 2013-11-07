package ec.com.ebos.core.master.model;

public interface PropiedadValor extends Master {

	public Long getId();

	public Propiedad getPropiedad();

	public String getValor();

	public void setId(Long id);

	public void setPropiedad(Propiedad propiedad);

	public void setValor(String valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}