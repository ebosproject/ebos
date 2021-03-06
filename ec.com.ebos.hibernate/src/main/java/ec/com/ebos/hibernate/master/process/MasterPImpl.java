package ec.com.ebos.hibernate.master.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.ebos.core.master.exception.MasterException;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.master.model.Propiedad;
import ec.com.ebos.core.master.process.MasterP;
import ec.com.ebos.core.orm.crud.GenericCriteria;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.core.root.model.Entidad.Estado;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.NumberUtils;
import ec.com.ebos.core.util.StringUtils;
import ec.com.ebos.hibernate.master.model.HibernatePersona;
import ec.com.ebos.hibernate.master.model.HibernatePropiedad;
import ec.com.ebos.hibernate.master.model.field.Persona_;
import ec.com.ebos.hibernate.master.model.field.Propiedad_;
import ec.com.ebos.hibernate.root.process.RootPImpl;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Repository("masterP")
public class MasterPImpl extends RootPImpl<Object, MasterException> implements MasterP {

	private static final long serialVersionUID = -7535155949566180920L;
	
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
	public HibernatePropiedad buildPropiedad(){
		HibernatePropiedad propiedad = new HibernatePropiedad();
		propiedad.setEstado(Estado.INACTIVO);
		return propiedad;
	}

	@Override
	public Propiedad savePropiedad(Propiedad propiedad){
		propiedad = saveOrMerge(propiedad);
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
		criteria.addAliasedJoins(Persona_.creador);
		criteria.addAliasedLeftJoins(Persona_.modificador);
		
		criteria.addEqualsIfNotZero(Persona_.id, persona.getId());
		if(criteria.isChanged()){
			return findByCriteria(criteria, pagination);
		}
		criteria.addLikeIfNotNull(Persona_.identificacion, persona.getIdentificacion());
		criteria.addEqualsIfNotNull(Persona_.genero, persona.getGenero());
		criteria.addEqualsIfNotNull(Persona_.estado, persona.getEstado());
		criteria.addLikeTokens(Persona_.nombres, persona.getNombres());
		criteria.addLikeTokens(Persona_.apellidos, persona.getApellidos());
		criteria.addLikeTokens(Persona_.direccion, persona.getDireccion());
		
		criteria.addEqualsIsTrue(Persona_.usuario, persona.isUsuario());
		criteria.addEqualsIsTrue(Persona_.cliente, persona.isCliente());
    	criteria.addEqualsIsTrue(Persona_.empleado, persona.isEmpleado());
    	criteria.addEqualsIsTrue(Persona_.proveedor, persona.isProveedor());
    	        

        return findByCriteria(criteria, pagination);
	}
	
	/**
	 * Obtiene una lista de Personas que cumplan el criterio de busqueda
	 * @param query : String con criterios de busqueda
	 */
	public List<Persona> findPersonaList(String query){
		if(StringUtils.isBlank(query)){
			return new ArrayList<Persona>();
		}
	
		GenericCriteria<Persona> criteria = GenericCriteria.forClass(Persona.class);
		criteria.addAliasedJoins(Persona_.creador);
		
		if(NumberUtils.tryParseLong(query)){
			criteria.addEqualsIfNotZero(Persona_.id, NumberUtils.parseLong(query));
			if(criteria.isChanged()){
				return findByCriteria(criteria);
			}
		}
		
		criteria.addMultiLikeTokens(query, Persona_.nombres, Persona_.apellidos);

		return findByCriteria(criteria);
	}

	public Persona createPersona(){
		Persona persona = new HibernatePersona();
		persona.setEstado(Estado.INACTIVO);
		return persona;
	}

	public Persona savePersona(Persona persona){
		if(!EntityUtils.isPersistent(persona)){
			persona.setEstado(Estado.ACTIVO);
		}
		persona = saveOrMerge(persona);
        putSuccess("persona.success.guardar", persona.getId());
        return persona;
	}

	public void deletePersona(Persona persona){
		Long id = persona.getId();
        delete(persona);
        putSuccess("persona.success.eliminar", id);
	}


	
}
