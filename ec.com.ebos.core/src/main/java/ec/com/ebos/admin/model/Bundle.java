package ec.com.ebos.admin.model;

public interface Bundle {

	public String getCodigo();

	public Long getId();

	public HibernateBundle.Localidad getLocalidad();

	public String getValor();

	public void setCodigo(String codigo);

	public void setId(Long id);

	public void setLocalidad(HibernateBundle.Localidad localidad);

	public void setValor(String valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public String getKeyCache();

}