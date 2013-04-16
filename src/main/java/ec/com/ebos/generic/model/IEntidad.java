package ec.com.ebos.generic.model;


/**
 * Interfaz para Clases y Enums que implementen los metodos {@link #getValue()} y {@link #getLabel()}.
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2012-07-27
 */
public interface IEntidad {

	/**
	 * @return valor
	 */
	public abstract Object getValue();

	/**
	 * @return etiqueta
	 */
	public abstract String getLabel();
	
}
