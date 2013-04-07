package ec.com.platform.app.core.gestor;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.platform.app.exception.AppException;
import ec.com.platform.app.model.Bundle;
import ec.com.platform.app.model.Bundle.Localidad;
import ec.com.platform.app.model.Bundle_;
import ec.com.platform.app.model.MessageResource_;
import ec.com.platform.app.model.Persona;
import ec.com.platform.app.model.Persona_;
import ec.com.platform.app.model.Propiedad;
import ec.com.platform.app.model.Propiedad_;
import ec.com.platform.fwk.crud.GenericCriteria;
import ec.com.platform.fwk.crud.Paginacion;
import ec.com.platform.generic.core.gestor.GenericGImpl;
import ec.com.platform.generic.model.Entidad.Estado;
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
		
		criteria.addLikeIfNotNull(Bundle_.codigo, bundle.getCodigo());
        criteria.addEqualsIfNotNull(Bundle_.localidad, bundle.getLocalidad());

        return findByCriteria(criteria, paginacion);
	}
	
	@Override
	public Bundle obtenerMessageResourcePorCodeYLocale(String codigo,
			Bundle.Localidad localidad) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);
		criteria.addEquals(MessageResource_.codigo, codigo);
		criteria.addEquals(MessageResource_.localidad, localidad);
		return findFirstByCriteria(criteria);
	}

	@Override
	public List<String> obtenerCodeMessageResourcePorLocale(Localidad localidad) {
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
        putSuccess("bundle.success.guardar", bundle.getId());
        return bundle;
	}

	@Override
	public void eliminarBundle(Bundle bundle) {
		Long id = bundle.getId();
        delete(bundle);
        putSuccess("bundle.success.eliminar", id);
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
        putSuccess("propiedad.success.guardar", propiedad.getId());
        return propiedad;
	}

	@Override
	public void eliminarPropiedad(Propiedad propiedad){
		Long id = propiedad.getId();
        delete(propiedad);
        putSuccess("propiedad.success.eliminar", id);
	}
	
	//
	// Persona
	//
	
	public List<Persona> obtenerPersonaList(Persona persona, Paginacion paginacion){
		GenericCriteria<Persona> criteria = GenericCriteria.forClass(Persona.class);

		criteria.addEqualsIfNotZero(Persona_.id, persona.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, paginacion);
		}
		criteria.addLikeIfNotNull(Persona_.apellidos, persona.getApellidos());
		criteria.addLikeIfNotNull(Persona_.nombres, persona.getNombres());
        criteria.addEqualsIfNotNull(Persona_.cliente, persona.isCliente());
        criteria.addEqualsIfNotNull(Persona_.empleado, persona.isEmpleado());
        criteria.addEqualsIfNotNull(Persona_.proveedor, persona.isProveedor());
        criteria.addEqualsIfNotNull(Persona_.estado, persona.getEstado());
        criteria.addEqualsIfNotNull(Persona_.tipoIdentificacion, persona.getTipoIdentificacion());
        criteria.addEqualsIfNotNull(Persona_.tipoPersona, persona.getTipoPersona());

        return findByCriteria(criteria, paginacion);
	}

	public Persona obtenerPersonaNuevo(){
		Persona persona = new Persona();
		persona.setEstado(Estado.INACTIVO);
		return persona;
	}

	public Persona guardarPersona(Persona persona){
		persona = saveOrUpdate(persona);
        putSuccess("persona.success.guardar", persona.getId());
        return persona;
	}

	public void eliminarPersona(Persona persona){
		Long id = persona.getId();
        delete(persona);
        putSuccess("persona.success.eliminar", id);
	}
	
}
