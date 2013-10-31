package ec.com.ebos.master.core.service;

import java.io.Serializable;
import java.util.List;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.master.model.hibernate.HibernatePersona;
import ec.com.ebos.master.model.hibernate.HibernatePropiedad;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.security.core.process.SecurityP;

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

	public List<HibernatePropiedad> findPropiedadList(Propiedad propiedad, Pagination pagination);
	
	public HibernatePropiedad createPropiedad();
	
	public HibernatePropiedad savePropiedad(HibernatePropiedad propiedad);
	
	public void deletePropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<HibernatePersona> findPersonaList(Persona persona, Pagination pagination);
	
	public List<HibernatePersona> findPersonaList(String query);

	public HibernatePersona createPersona();

	public HibernatePersona savePersona(HibernatePersona persona);

	public void deletePersona(HibernatePersona persona);


}
