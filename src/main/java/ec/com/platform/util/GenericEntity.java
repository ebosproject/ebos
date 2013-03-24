package ec.com.platform.util;


/**
 * Plantilla para Clases y Enums que implementen los metodos {@link #getValue()} y {@link #getLabel()}.
 * @author Eduardo Plua Alay
 * @since 2012-07-27
 */
public interface GenericEntity {

	/**
	 * @return valor
	 */
	public abstract Object getValue();

	/**
	 * @return etiqueta
	 */
	public abstract String getLabel();
	
}
