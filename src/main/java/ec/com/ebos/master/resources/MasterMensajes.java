package ec.com.ebos.master.resources;

import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.MessageUtils;

import java.util.ResourceBundle;

/**
 * Permite obtener claves y valores del archivo de recursos del modulo Seguridad
 * 
 * @author Eduardo Plua Alay
 */
public class MasterMensajes {

	/**
	 * Path del archivo de propiedades por defecto. Este es el &uacute;nico que se debe especificar.
	 */
	public static final String BUNDLE_NAME = Constantes.DOMAIN_NAME+".master.resources.master";

	/**
	 * ResourceBundle que representa el archivo de propiedades seleccionado para obtener los mensajes.
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static String getString(String key, Object... params ){
		return MessageUtils.buildMessage(key, RESOURCE_BUNDLE, params);
	}

}
