package ec.com.platform.app.core.gestor;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.platform.app.exception.AppException;
import ec.com.platform.app.model.Bundle;
import ec.com.platform.app.model.Bundle_;
import ec.com.platform.app.model.MessageResource_;
import ec.com.platform.app.model.Propiedad;
import ec.com.platform.app.model.Propiedad_;
import ec.com.platform.fwk.crud.GenericCriteria;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.core.gestor.GenericGImpl;
import ec.com.platform.generic.model.Generic.Estado;
import ec.com.platform.util.Constantes;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Repository("appG")
public class AppGImpl extends GenericGImpl<Object, AppException> implements AppG {

	private static final long serialVersionUID = -7535155949566180920L;
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".app.resources.app";
    }

    //
    // Bundle
    //
	
	@Override
	public List<Bundle> obtenerBundleList(Bundle bundle, Paginacion paginacion) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);

		criteria.addEqualsIfNotZero(Bundle_.id, bundle.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, paginacion);
		}
		
		criteria.addLike(Bundle_.codigo, bundle.getCodigo());
        criteria.addLike(Bundle_.localidad, bundle.getLocalidad());

        return findByCriteria(criteria, paginacion);
	}
	
	@Override
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo,
			String localidad) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);
		criteria.addEquals(MessageResource_.codigo, codigo);
		criteria.addEquals(MessageResource_.localidad, localidad);
		return findFirstByCriteria(criteria);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(String localidad) {
		return findByQuery("select m.codigo from Bundle m where m.localidad = :localidad", String.class, localidad);
	}
	
	@Override
	public Bundle obtenerBundleNuevo() {
		Bundle bundle = new Bundle();
        return bundle;
	}

	@Override
	public Bundle guardarBundle(Bundle bundle) {
		bundle = saveOrUpdate(bundle);
        wrapSuccessMessage("bundle.success.guardar", bundle.getId());
        return bundle;
	}

	@Override
	public void eliminarBundle(Bundle bundle) {
		Long id = bundle.getId();
        delete(bundle);
        wrapSuccessMessage("bundle.success.eliminar", id);
	}
	
	//
	// Propiedad
	//
	
	@Override
	public List<Propiedad> obtenerPropiedadList(Propiedad propiedad, Paginacion paginacion){
		GenericCriteria<Propiedad> criteria = GenericCriteria.forClass(Propiedad.class);

		criteria.addEqualsIfNotZero(Propiedad_.id, propiedad.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, paginacion);
		}
		criteria.addLikeIfNotNull(Propiedad_.valor, propiedad.getValor());
		criteria.addLikeIfNotNull(Propiedad_.valorDefecto, propiedad.getValorDefecto());
        criteria.addEqualsIfNotNull(Propiedad_.categoria, propiedad.getCategoria());
        criteria.addEqualsIfNotNull(Propiedad_.estado, propiedad.getEstado());
        criteria.addEqualsIfNotNull(Propiedad_.lista, propiedad.isLista());
        criteria.addEqualsIfNotNull(Propiedad_.requerido, propiedad.isRequerido());
        criteria.addEqualsIfNotNull(Propiedad_.tipoDato, propiedad.getTipoDato());

        return findByCriteria(criteria, paginacion);
	}

	@Override
	public Propiedad obtenerPropiedadNuevo(){
		Propiedad propiedad = new Propiedad();
		propiedad.setEstado(Estado.INACTIVO);
		return propiedad;
	}

	@Override
	public Propiedad guardarPropiedad(Propiedad propiedad){
		propiedad = saveOrUpdate(propiedad);
        wrapSuccessMessage("propiedad.success.guardar", propiedad.getId());
        return propiedad;
	}

	@Override
	public void eliminarPropiedad(Propiedad propiedad){
		Long id = propiedad.getId();
        delete(propiedad);
        wrapSuccessMessage("propiedad.success.eliminar", id);
	}
	
}
