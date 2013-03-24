package ec.com.platform.app.core.gestor;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.platform.app.exception.AppException;
import ec.com.platform.app.model.MessageResource;
import ec.com.platform.app.model.MessageResource_;
import ec.com.platform.fwk.crud.GenericCriteria;
import ec.com.platform.generic.core.gestor.GenericGImpl;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Repository("appG")
public class AppGImpl extends GenericGImpl<Object, AppException> implements AppG {

	private static final long serialVersionUID = -7535155949566180920L;

    //
    // MessageResource
    //
	
	@Override
	public MessageResource obtenerMessageResourcePorCodeYLocale(String codigo,
			String localidad) {
		GenericCriteria<MessageResource> criteria = GenericCriteria.forClass(MessageResource.class);
		criteria.addEquals(MessageResource_.codigo, codigo);
		criteria.addEquals(MessageResource_.localidad, localidad);
		return findFirstByCriteria(criteria);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad) {
		return findByQuery("select m.codigo from MessageResource m where m.localidad = :localidad", String.class, localidad);
	}

	@Override
	public MessageResource guardarMessageResource(MessageResource messageResource) {
		messageResource = saveOrUpdate(messageResource);
        wrapSuccessMessage("MessageResource " + messageResource.getId() + " guardado correctamente");
        return messageResource;
	}
	
}
