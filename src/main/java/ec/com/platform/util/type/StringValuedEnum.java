package ec.com.platform.util.type;

import ec.com.platform.generic.model.Entidad;
import ec.com.platform.util.IEntidad;

/**
 * Plantilla para grupos de constantes (Enum) que implementan {@link Entidad}.
 * Pueden ser usados como tipo de dato para Hibernate con ayuda de {@link StringValuedEnumType}.
 * Ver ejemplo de implementacion {@link ExampleEnum}.
 *
 * @update <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2012-07-27
 * @see StringValuedEnumType
 * @see ExampleEnum
 * @see http://community.jboss.org/wiki/Java5StringValuedEnumUserType
 */
public interface StringValuedEnum<T extends Enum<T>> extends IEntidad {

    /**
     * Etiqueta para este enumType
     *
     * @since 2008-06-28
     */
    @Override
    public String getLabel();

    /**
     * Current string value stored in the enum.
     *
     * @return string value.
     */
    @Override
    public String getValue();

    /**
     * Descripcion para este enumType, usado para selectors (F9 y suggestion).
     * Debe llamar a {@link #getLabel()}.
     *
     * @return string value.
     */
    public String getDescription();
}
