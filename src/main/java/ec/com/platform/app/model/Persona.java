package ec.com.platform.app.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ec.com.platform.app.resources.AppMensajes;
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.GenericUtils;
import ec.com.platform.util.type.StringValuedEnum;
import ec.com.platform.util.type.StringValuedEnumReflect;
import ec.com.platform.util.type.StringValuedEnumType;
import ec.com.platform.util.type.Type;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = "APPTPERSONA", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class Persona extends GenericApp<Persona>{

	private static final long serialVersionUID = -2896367216397132540L;

	@Id
	@SequenceGenerator(name = "APPTPERSONA_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSPERSONA")
	@GeneratedValue(generator = "APPTPERSONA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "nombres", nullable = false, length = 50)
	private String nombres;

	@Column(name = "apellidos", nullable = false, length = 50)
	private String apellidos;

	@Column(name = "identificacion", unique = true, nullable = false, length = 20)
	private String identificacion;
	
	@Column(name = "mail", length = 100)
	private String mail;
	
	@Column(name = "usuario", nullable = false)
	private boolean usuario;
	
	@Column(name = "cliente", nullable = false)
	private boolean cliente;
	
	@Column(name = "proveedor", nullable = false)
	private boolean proveedor;
	
	@Column(name = "empleado", nullable = false)
	private boolean empleado;
	
	@Column(name = "tipoIdentificacion", nullable = false)
    @Type(type = TipoPersona.TYPE)
    private TipoPersona tipoIdentificacion;
	
	@Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    private Set<Empresa> empresaList = new HashSet<Empresa>(0);
	
	@OneToMany(mappedBy = "persona", fetch= FetchType.LAZY)
    private Set<EmpresaPersona> empresaPersonaList = new HashSet<EmpresaPersona>(0);
	
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
        RUC("R");

        public static class Type extends StringValuedEnumType<Estado> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".app.model.Persona$TipoIdentificacion$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoIdentificacion(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoIdentificacion> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoIdentificacion.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Estado> LIST = Arrays.asList(Estado.values());

        @Override
        public String getLabel() {
            return AppMensajes.getString(labelKey);
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

        public static class Type extends StringValuedEnumType<Estado> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".app.model.Persona$TipoPersona$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoPersona(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoPersona> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoPersona.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoPersona> LIST = Arrays.asList(TipoPersona.values());

        @Override
        public String getLabel() {
            return AppMensajes.getString(labelKey);
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

}
