package ec.com.ebos.core.master.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.core.master.model.EmpresaPersona;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.master.model.Propiedad;
import ec.com.ebos.core.orm.crud.Pagination;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public interface MasterS extends Serializable {
    
	/**
	 * Nombre del Spring Bean para {@link MasterS}
	 */
	public static final String BEAN_NAME = "masterS";
	
	//
    // Propiedad
    //

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination);
	
	public Propiedad createPropiedad();
	
	public Propiedad savePropiedad(Propiedad propiedad);
	
	public void deletePropiedad(Propiedad propiedad);
	
	public Propiedad getInstancePropiedad();

	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination);
	
	public List<Persona> findPersonaList(String query);

	public Persona createPersona();

	public Persona savePersona(Persona persona);

	public void deletePersona(Persona persona);

	public Persona getInstancePersona();
	
	//
	// EmpresaPersona
	//
	public EmpresaPersona getInstanceEmpresaPersona();

}
