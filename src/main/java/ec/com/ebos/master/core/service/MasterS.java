package ec.com.ebos.master.core.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public interface MasterS extends Serializable {
    
	//
    // Propiedad
    //

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination);
	
	public Propiedad createPropiedad();
	
	public Propiedad savePropiedad(Propiedad propiedad);
	
	public void deletePropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination);

	public Persona createPersona();

	public Persona savePersona(Persona persona);

	public void deletePersona(Persona persona);

}
