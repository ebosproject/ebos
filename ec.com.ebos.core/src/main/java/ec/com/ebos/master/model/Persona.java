package ec.com.ebos.master.model;

import java.util.Date;
import java.util.Set;

import ec.com.ebos.master.model.hibernate.HibernateEmpresaPersona;
import ec.com.ebos.master.model.hibernate.HibernateOrganizacion;
import ec.com.ebos.master.model.hibernate.HibernatePersona.Genero;
import ec.com.ebos.master.model.hibernate.HibernatePersona.TipoIdentificacion;
import ec.com.ebos.master.model.hibernate.HibernatePersona.TipoPersona;
import ec.com.ebos.mse.model.hibernate.HibernateMonaguillo;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

public interface Persona {

	public String getApellidos();

	public Auditoria getAuditoria();

	public String getCelular();

	public DivisionGeografica getCiudad();

	public String getContentType();

	public String getDireccion();

	public Set<HibernateOrganizacion> getEmpresaList();

	public Set<HibernateEmpresaPersona> getEmpresaPersonaList();

	public Entidad.Estado getEstado();

	public String getFacebook();

	public Genero getGenero();

	public Long getId();

	public String getIdentificacion();

	public byte[] getImagen();

	public String getMail();

	public Set<HibernateMonaguillo> getMonagilloList();

	public String getNombres();

	public String getTelefono();

	public TipoIdentificacion getTipoIdentificacion();

	public TipoPersona getTipoPersona();

	public boolean isCliente();

	public boolean isEmpleado();

	public boolean isProveedor();

	public boolean isUsuario();

	public void setApellidos(String apellidos);

	public void setAuditoria(Auditoria auditoria);

	public void setCelular(String celular);

	public void setCiudad(DivisionGeografica ciudad);

	public void setCliente(boolean cliente);

	public void setContentType(String contentType);

	public void setDireccion(String direccion);

	public void setEmpleado(boolean empleado);

	public void setEmpresaList(Set<HibernateOrganizacion> empresaList);

	public void setEmpresaPersonaList(
			Set<HibernateEmpresaPersona> empresaPersonaList);

	public void setEstado(Entidad.Estado estado);

	public void setFacebook(String facebook);

	public void setGenero(Genero genero);

	public void setId(Long id);

	public void setIdentificacion(String identificacion);

	public void setImagen(byte[] imagen);

	public void setMail(String mail);

	public void setMonagilloList(Set<HibernateMonaguillo> monagilloList);

	public void setNombres(String nombres);

	public void setProveedor(boolean proveedor);

	public void setTelefono(String telefono);

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion);

	public void setTipoPersona(TipoPersona tipoPersona);

	public void setUsuario(boolean usuario);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public Date getNacimiento();

	public void setNacimiento(Date nacimiento);

	/**
	 * Obtiene la edad de la persona basada en su fecha de {@link #nacimiento} 
	 * @return edad persona
	 */
	public int getEdad();

	public String getFullName();

}