package ec.com.ebos.master.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.master.model.field.Bundle_;
import ec.com.ebos.master.resources.MasterMensajes;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = Bundle.TABLE_NAME, schema = Master.SCHEMA,
	uniqueConstraints = @UniqueConstraint(columnNames={Bundle_.codigo, Bundle_.localidad}))
@Data @EqualsAndHashCode(callSuper=false)
public class Bundle extends Master<Bundle>{

	private static final long serialVersionUID = -2896367216397132540L;
	
	protected static final String TABLE_NAME = "BUNDLE";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
		
	@Column(name = "codigo", nullable = false, length = 50)
	private String codigo;

	@Column(name = "localidad", nullable = false, length = 5)
	@Type(type = Localidad.TYPE)
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

        public static class Type extends StringValuedEnumType<Localidad> {
        }
        public static final String TYPE = Constantes.DOMAIN_NAME+".master.model.Bundle$Localidad$Type";
        
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
            return MasterMensajes.getString(labelKey);
        }

        @Override
        public String getDescription() {
            return getLabel();
        }
    }

}
