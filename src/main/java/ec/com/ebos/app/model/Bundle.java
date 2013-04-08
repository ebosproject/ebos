package ec.com.ebos.app.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ec.com.ebos.app.resources.AppMensajes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity //TODO (epa): Cambiar nombre de tabla a 'BUNDLE'
@Table(name = "APPTMESSAGE_RESOURCE", schema = "EBOSAPPL",
	uniqueConstraints = @UniqueConstraint(columnNames={"codigo", "localidad"}))
@Data @EqualsAndHashCode(callSuper=false)
public class Bundle extends GenericApp<Bundle>{

	private static final long serialVersionUID = -2896367216397132540L;

	@Id
	@SequenceGenerator(name = "APPTMESSAGE_RESOURCE_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSMESSAGE_RESOURCE")
	@GeneratedValue(generator = "APPTMESSAGE_RESOURCE_ID_GENERATOR")
	private Long id;
		
	@Column(name = "codigo", nullable = false, length = 50)
	private String codigo;

	@Column(name = "localidad", nullable = false, length = 5)
	@Enumerated(EnumType.STRING)
	private Bundle.Localidad localidad;

	@Column(name = "valor", nullable = false, length = 500)
	private String valor;
	
	/**
     * <strong>Localidades(es[_EC]) que soporta el sistema</strong> <br> <table border="1">
     * <tr><th valign="top"> Localidad </th>
     * <tr><td> es_EC: Espaniol Ecuador </td>
     * <tr><td> en_US: Ingles USA </td>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum Localidad implements StringValuedEnum<Localidad> {

        es("es"),
        en("en");

//        public static class Type extends StringValuedEnumType<Localidad> {
//        }
        //public static final String TYPE = Constantes.DOMAIN_NAME+".generic.model.Generic$Estado$Type";
        
        @Getter
        private String value;
        private String labelKey;

        private Localidad(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, Localidad> LABELED_MAP =
                GenericUtils.buildLabeledEnumMap(Localidad.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Localidad> LIST = Arrays.asList(Localidad.values());

        @Override
        public String getLabel() {
            return AppMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }
    }

}
