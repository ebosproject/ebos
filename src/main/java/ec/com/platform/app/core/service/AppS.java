package ec.com.platform.app.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import ec.com.platform.app.model.MessageResource;

/**
 *
 * @author Eduardo Plua Alay
 */
public interface AppS extends Serializable {
    
	//
    // MessageResource
    //
	@Cacheable(value="messagesResources")
	public MessageResource obtenerMessageResourcePorCodeYLocale(String codigo, String localidad);
	
	@Cacheable(value="messagesResources")
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad);
	
	@CacheEvict(value="messagesResources",allEntries=true)
	public MessageResource guardarMessageResource(MessageResource messageResource);
    
}
