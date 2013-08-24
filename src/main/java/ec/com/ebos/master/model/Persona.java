package ec.com.ebos.master.model;

import java.util.Arrays;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Type;

import ec.com.ebos.master.model.field.EmpresaPersona_;
import ec.com.ebos.master.model.field.Organizacion_;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.mse.model.field.Monagillo_;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = Persona.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Persona extends Master<Persona>{

	private static final long serialVersionUID = -2896367216397132540L;

	protected static final String TABLE_NAME = "PERSONA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "nombres", nullable = false, length = 50)
	private String nombres;

	@Column(name = "apellidos", nullable = false, length = 50)
	private String apellidos;
	
	@Getter @Setter
	@Column(name = "nacimiento", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date nacimiento;

	@Column(name = "identificacion", unique = true, nullable = false, length = 20)
	private String identificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ciudad")
    private DivisionGeografica ciudad;
	
	/**
	 *  Posibles configuraciones
	 *	@Type(type="org.hibernate.type.PrimitiveByteArrayBlobType")
	 *  @Column(name = "imagen", columnDefinition="bytea")
	 *	@Column(name = "imagen")
	 *  Postgresql types: oid y bytea
	 *  @see http://stackoverflow.com/questions/3677380/proper-hibernate-annotation-for-byte
	 *  @see http://www.zephid.dk/2008/08/09/oid-vs-bytea-in-postgresql/
	 *  @see http://amilasilva88.blogspot.com/2010/08/postgresql-bytea-and-oid.html
	 *  @see https://forum.hibernate.org/viewtopic.php?p=2432614 
	 *  Conclusion: oid mas eficiente que bytea
	 *  
	*/
	@Lob //Postgres default oid type
	@Column(name = "imagen")
	private byte[] imagen;
	
	@Column(name = "direccion", length = 100)
	private String direccion;
	
	@Column(name = "mail", length = 100)
	private String mail;
	
	@Column(name = "facebook", length = 30)
	private String facebook;

	@Column(name = "telefono", length = 25)
	private String telefono;
	
	@Column(name = "celular", length = 25)
	private String celular;
	
	@Column(name = "usuario", nullable = false)
	private boolean usuario;
	
	@Column(name = "cliente", nullable = false)
	private boolean cliente;
	
	@Column(name = "proveedor", nullable = false)
	private boolean proveedor;
	
	@Column(name = "empleado", nullable = false)
	private boolean empleado;
	
	@Column(name = "tipoIdentificacion", nullable = false, length = 1)
    @Type(type = TipoIdentificacion.TYPE)
    private TipoIdentificacion tipoIdentificacion;
	
	/**
	 * Numero de cedula, ruc o codigo de indentificacion
	 */
	@Column(name = "tipoPersona", nullable = false, length = 1)
	@Type(type = TipoPersona.TYPE)
    private TipoPersona tipoPersona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = Organizacion_.persona, fetch = FetchType.LAZY)
    private Set<Organizacion> empresaList = new HashSet<Organizacion>(0);
	
	@OneToMany(mappedBy = EmpresaPersona_.persona, fetch= FetchType.LAZY)
    private Set<EmpresaPersona> empresaPersonaList = new HashSet<EmpresaPersona>(0);
	
	@OneToMany(mappedBy = Monagillo_.persona, fetch= FetchType.LAZY)
    private Set<Monaguillo> monagilloList = new HashSet<Monaguillo>(0);
	
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

        public static class Type extends StringValuedEnumType<Estado> {
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

        public static class Type extends StringValuedEnumType<Estado> {
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

}
