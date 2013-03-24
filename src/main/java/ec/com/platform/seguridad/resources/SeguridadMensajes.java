package ec.com.platform.seguridad.resources;

import ec.com.platform.app.web.resources.DatabaseDrivenResourceBundle;
import ec.com.platform.util.Constantes;
import ec.com.platform.util.MessageUtils;

import java.util.ResourceBundle;

/**
 * Permite obtener claves y valores del archivo de recursos del modulo Seguridad
 * 
 * @author Eduardo Plua Alay
 */
public class SeguridadMensajes {

	/**
	 * Path del archivo de propiedades por defecto. Este es el &uacute;nico que se debe especificar.
	 */
	private static final String BUNDLE_NAME = Constantes.DOMAIN_NAME+".seguridad.resources.seguridad";

	/**
	 * ResourceBundle que representa el archivo de propiedades seleccionado para obtener los mensajes.
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		
	public static String getString(String key, Object... params ){
		return MessageUtils.buildMessage(key, RESOURCE_BUNDLE, params);
	}

}
