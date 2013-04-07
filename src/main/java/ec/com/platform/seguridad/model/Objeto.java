package ec.com.platform.seguridad.model;

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

import org.hibernate.annotations.Type;

import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Entidad;
import ec.com.platform.seguridad.resources.SeguridadMensajes;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.GenericUtils;
import ec.com.platform.util.type.StringValuedEnum;
import ec.com.platform.util.type.StringValuedEnumReflect;
import ec.com.platform.util.type.StringValuedEnumType;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = "SEGTOBJETO", schema = "PLTFSEGU")
@Data @EqualsAndHashCode(callSuper=false) 
public class Objeto extends GenericSeguridad<Objeto> {
    
	private static final long serialVersionUID = -3052521057254508069L;

	@Id
	@SequenceGenerator(name = "SEGTOBJETO_ID_GENERATOR", sequenceName = "PLTFSEGU.SEGSOBJETO")
	@GeneratedValue(generator = "SEGTOBJETO_ID_GENERATOR")
    private Long id;

	@Embedded
	private Auditoria auditoria;	
    
    @Column(name="codigo", length=30, unique=true, nullable=false)
    private String codigo;
    
    @Column(name="descripcion", length=150)
    private String descripcion;
    
    @Column(name = "tipo", nullable = false, length = 1)
    @Type(type = Objeto.TipoObjeto.TYPE)
    private Objeto.TipoObjeto tipo;
    
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;

    @OneToMany(mappedBy = "objeto", fetch=FetchType.LAZY)
    private Set<Opcion> opcionList = new HashSet<Opcion>(0);
    
    /**
     * <strong>TipoObjeto de un {@link Objeto}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> B: Bean<br> O: Otro<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoObjeto implements StringValuedEnum<TipoObjeto> {

        BEAN("B"),
        OTRO("O");

        public static class Type extends StringValuedEnumType<TipoObjeto> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".seguridad.model.Objeto$TipoObjeto$Type";
        
        @Getter
        private String value;
        private String labelKey;

        private TipoObjeto(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoObjeto> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(TipoObjeto.values());

        /**
         * Listas para iteraciones
         */
        public static final List<TipoObjeto> LIST = Arrays.asList(TipoObjeto.values());

        @Override
        public String getLabel() {
            return SeguridadMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isBean() {
            return this.equals(BEAN);
        }
        
        public boolean isOtro() {
            return this.equals(OTRO);
        }
    }
    
}
