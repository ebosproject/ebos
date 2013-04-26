package ec.com.ebos.appl.core.process;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.appl.exception.AppException;
import ec.com.ebos.appl.model.Bundle;
import ec.com.ebos.appl.model.Persona;
import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.appl.model.Bundle.Localidad;
import ec.com.ebos.appl.model.field.Bundle_;
import ec.com.ebos.appl.model.field.MessageResource_;
import ec.com.ebos.appl.model.field.Persona_;
import ec.com.ebos.appl.model.field.Propiedad_;
import ec.com.ebos.orm.crud.GenericCriteria;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.root.core.process.CorePImpl;
import ec.com.ebos.root.model.Entidad.Estado;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Repository("applP")
public class ApplPImpl extends CorePImpl<Object, AppException> implements ApplP {

	private static final long serialVersionUID = -7535155949566180920L;
	
    //
    // Bundle
    //
	
	@Override
	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);

		criteria.addEqualsIfNotZero(Bundle_.id, bundle.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		
		criteria.addLikeIfNotNull(Bundle_.codigo, bundle.getCodigo());
        criteria.addEqualsIfNotNull(Bundle_.localidad, bundle.getLocalidad());

        return findByCriteria(criteria, pagination);
	}
	
	@Override
	public Bundle getMessageResource(String codigo,
			Bundle.Localidad localidad) {
		GenericCriteria<Bundle> criteria = GenericCriteria.forClass(Bundle.class);
		criteria.addEquals(MessageResource_.codigo, codigo);
		criteria.addEquals(MessageResource_.localidad, localidad);
		return findFirstByCriteria(criteria);
	}

	@Override
	public List<String> getCodeMessageResourceList(Localidad localidad) {
		return findByQuery("select m.codigo from Bundle m where m.localidad = :localidad", String.class, localidad);
	}
	
	@Override
	public Bundle buildBundle() {
		Bundle bundle = new Bundle();
        return bundle;
	}

	@Override
	public Bundle saveBundle(Bundle bundle) {
		bundle = saveOrUpdate(bundle);
        putSuccess("bundle.success.guardar", bundle.getId());
        return bundle;
	}

	@Override
	public void deleteBundle(Bundle bundle) {
		Long id = bundle.getId();
        delete(bundle);
        putSuccess("bundle.success.eliminar", id);
	}
	
	//
	// Propiedad
	//
	
	@Override
	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination){
		GenericCriteria<Propiedad> criteria = GenericCriteria.forClass(Propiedad.class);

		criteria.addEqualsIfNotZero(Propiedad_.id, propiedad.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		criteria.addLikeIfNotNull(Propiedad_.valor, propiedad.getValor());
		criteria.addLikeIfNotNull(Propiedad_.valorDefecto, propiedad.getValorDefecto());
        criteria.addEqualsIfNotNull(Propiedad_.categoria, propiedad.getCategoria());
        criteria.addEqualsIfNotNull(Propiedad_.estado, propiedad.getEstado());
        criteria.addEqualsIfNotNull(Propiedad_.lista, propiedad.isLista());
        criteria.addEqualsIfNotNull(Propiedad_.requerido, propiedad.isRequerido());
        criteria.addEqualsIfNotNull(Propiedad_.tipoDato, propiedad.getTipoDato());

        return findByCriteria(criteria, pagination);
	}

	@Override
	public Propiedad buildPropiedad(){
		Propiedad propiedad = new Propiedad();
		propiedad.setEstado(Estado.INACTIVO);
		return propiedad;
	}

	@Override
	public Propiedad savePropiedad(Propiedad propiedad){
		propiedad = saveOrUpdate(propiedad);
        putSuccess("propiedad.success.guardar", propiedad.getId());
        return propiedad;
	}

	@Override
	public void deletePropiedad(Propiedad propiedad){
		Long id = propiedad.getId();
        delete(propiedad);
        putSuccess("propiedad.success.eliminar", id);
	}
	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination){
		GenericCriteria<Persona> criteria = GenericCriteria.forClass(Persona.class);

		criteria.addEqualsIfNotZero(Persona_.id, persona.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		criteria.addLikeIfNotNull(Persona_.apellidos, persona.getApellidos());
		criteria.addLikeIfNotNull(Persona_.nombres, persona.getNombres());
        criteria.addEqualsIfNotNull(Persona_.cliente, persona.isCliente());
        criteria.addEqualsIfNotNull(Persona_.empleado, persona.isEmpleado());
        criteria.addEqualsIfNotNull(Persona_.proveedor, persona.isProveedor());
        criteria.addEqualsIfNotNull(Persona_.estado, persona.getEstado());
        criteria.addEqualsIfNotNull(Persona_.tipoIdentificacion, persona.getTipoIdentificacion());
        criteria.addEqualsIfNotNull(Persona_.tipoPersona, persona.getTipoPersona());

        return findByCriteria(criteria, pagination);
	}

	public Persona buildPersona(){
		Persona persona = new Persona();
		persona.setEstado(Estado.INACTIVO);
		return persona;
	}

	public Persona savePersona(Persona persona){
		persona = saveOrUpdate(persona);
        putSuccess("persona.success.guardar", persona.getId());
        return persona;
	}

	public void deletePersona(Persona persona){
		Long id = persona.getId();
        delete(persona);
        putSuccess("persona.success.eliminar", id);
	}
	
}
