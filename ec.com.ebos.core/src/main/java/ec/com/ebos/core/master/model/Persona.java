package ec.com.ebos.core.master.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.core.mse.model.Monaguillo;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;

public interface Persona extends Master {

	public String getApellidos();

	public Auditoria getAuditoria();

	public String getCelular();

	public DivisionGeografica getCiudad();

	public String getContentType();

	public String getDireccion();

	public Set<Organizacion> getEmpresaList();

	public Set<EmpresaPersona> getEmpresaPersonaList();

	public Entidad.Estado getEstado();

	public String getFacebook();

	public Genero getGenero();

	public Long getId();

	public String getIdentificacion();

	public byte[] getImagen();

	public String getMail();

	public Set<Monaguillo> getMonagilloList();

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

	public void setEmpresaList(Set<Organizacion> empresaList);

	public void setEmpresaPersonaList(Set<EmpresaPersona> empresaPersonaList);

	public void setEstado(Entidad.Estado estado);

	public void setFacebook(String facebook);

	public void setGenero(Genero genero);

	public void setId(Long id);

	public void setIdentificacion(String identificacion);

	public void setImagen(byte[] imagen);

	public void setMail(String mail);

	public void setMonagilloList(Set<Monaguillo> monagilloList);

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
	
	/**
     * <strong>Tipo Identificacion para una Persona</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> C: Cedula<br> R: Ruc<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoIdentificacion implements StringValuedEnum<TipoIdentificacion> {
        CEDULA("C"),
        RUC("R"),
        CODIGO("O");

        public static class Type extends StringValuedEnumType<TipoIdentificacion> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".master.model.Persona$TipoIdentificacion$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoIdentificacion(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoIdentificacion> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoIdentificacion.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoIdentificacion> LIST = Arrays.asList(TipoIdentificacion.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isCedula() {
            return this.equals(CEDULA);
        }

        public boolean isRuc() {
            return this.equals(RUC);
        }
        
        public boolean isCodigo(){
        	return this.equals(CODIGO);
        }
    }

    
	/**
     * <strong>Tipo Persona</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> N: Natural<br> J: Juridica<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoPersona implements StringValuedEnum<TipoPersona> {

        NATURAL("N"),
        JURIDICA("J");

        public static class Type extends StringValuedEnumType<TipoPersona> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".master.model.Persona$TipoPersona$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoPersona(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoPersona> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoPersona.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoPersona> LIST = Arrays.asList(TipoPersona.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isNatural() {
            return this.equals(NATURAL);
        }

        public boolean isJuridica() {
            return this.equals(JURIDICA);
        }
    }
    
    /**
     * <strong>Genero de una Persona</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Generos </th>
     * <tr><td valign="top"> M: Masculino<br> F: Femenino<br> O: Otro<br></td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum Genero implements StringValuedEnum<Genero> {
        MASCULINO("M"),
        FEMENINO("F"),
        OTRO("O");

        public static class Type extends StringValuedEnumType<Genero> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".master.model.Persona$Genero$Type";

        @Getter
        private String value;
        private String labelKey;

        private Genero(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, Genero> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Genero.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Genero> LIST = Arrays.asList(Genero.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isMasculino() {
            return this.equals(MASCULINO);
        }

        public boolean isFemenino() {
            return this.equals(FEMENINO);
        }
        
        public boolean isOtro(){
        	return this.equals(OTRO);
        }
    }

}