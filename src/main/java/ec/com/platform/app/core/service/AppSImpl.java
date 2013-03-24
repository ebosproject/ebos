package ec.com.platform.app.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.platform.app.core.gestor.AppG;
import ec.com.platform.app.model.MessageResource;

/**
 * @author Eduardo Plua Alay
 */
@Service("appS")
public class AppSImpl implements AppS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("appG")
    private AppG appG;
	
	//
    // MessageResource
    //
	
	@Override
	public MessageResource obtenerMessageResourcePorCodeYLocale(String codigo, String localidad){
		return appG.obtenerMessageResourcePorCodeYLocale(codigo, localidad);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad) {
		return appG.obtenerCodeMessageResourcePorLocale(localidad);
	}

	@Override
	public MessageResource guardarMessageResource(MessageResource messageResource) {
        return appG.guardarMessageResource(messageResource);
	}
}
