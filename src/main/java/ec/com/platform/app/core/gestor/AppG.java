package ec.com.platform.app.core.gestor;

import java.util.List;

import ec.com.platform.app.model.MessageResource;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface AppG {

    //
    // MessageResource
    //
	public MessageResource obtenerMessageResourcePorCodeYLocale(String codigo, String localidad);
	
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad);
	
	public MessageResource guardarMessageResource(MessageResource messageResource);
}
