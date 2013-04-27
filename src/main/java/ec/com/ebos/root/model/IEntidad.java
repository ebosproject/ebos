package ec.com.ebos.root.model;


/**
 * Interfaz para Clases y Enums que implementen los metodos {@link #getValue()} y {@link #getLabel()}.
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @update 2013-04-25
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
