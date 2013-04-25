package ec.com.ebos.appl.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.appl.resources.ApplMensajes;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = "APPTPROPIEDAD", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false)
public class Propiedad extends Application<Propiedad> {
    
	private static final long serialVersionUID = -5344259206776358470L;

	@Id
	@SequenceGenerator(name = "APPTPROPIEDAD_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSPROPIEDAD")
	@GeneratedValue(generator = "APPTPROPIEDAD_ID_GENERATOR")
	private Long id;	

	@ManyToOne
	@JoinColumn(name = "activo_id", nullable = false)
	private Activo activo;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
	
	@Column(name = "tipoDato", nullable = false, length = 1)
    @Type(type = Propiedad.TipoDato.TYPE)
    private Propiedad.TipoDato tipoDato;
	
	@Column(name = "valor", length = 50)
	private String valor;
	
	@Column(name = "valorDefecto", length = 50)
	private String valorDefecto;
	
    @Column(name = "longitud")
	private int longitud;
    
    @Column(name="requerido")
    private boolean requerido;
    
    @Column(name="lista")
    private boolean lista;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    @OneToMany(mappedBy = "propiedad", fetch= FetchType.LAZY)
    private Set<PropiedadValor> propiedadValorList = new HashSet<PropiedadValor>(0);
    
    /**
     * <strong>Tipo de dato</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Estados </th>
     * <tr><td valign="top"> 
     * T: TEXT <br> 
     * N: NUMBER <br>
     * B: BOOLEAN <br>
     * </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoDato implements StringValuedEnum<TipoDato> { //TODO (epa): agregar mas tipos de datos

        TEXT("T"),
        NUMBER("N"),
        BOOLEAN("B");

        public static class Type extends StringValuedEnumType<TipoDato> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".appl.model.Propiedad$TipoDato$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoDato(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoDato> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoDato.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoDato> LIST = Arrays.asList(TipoDato.values());

        @Override
        public String getLabel() {
            return ApplMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isTexto() {
            return this.equals(TEXT);
        }
        
        public boolean isNumber() {
            return this.equals(NUMBER);
        }
        
        public boolean isBoolean() {
            return this.equals(BOOLEAN);
        }

    }

}

