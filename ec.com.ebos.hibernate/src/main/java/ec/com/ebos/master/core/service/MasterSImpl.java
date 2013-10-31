package ec.com.ebos.master.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.master.core.process.MasterP;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.Propiedad;
import ec.com.ebos.master.model.hibernate.HibernatePersona;
import ec.com.ebos.master.model.hibernate.HibernatePropiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author Eduardo Plua Alay
 */
@Service(MasterS.BEAN_NAME)
public class MasterSImpl implements MasterS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("masterP")
    private MasterP masterP;
	
	//
    // Propiedad
    //

	public List<HibernatePropiedad> findPropiedadList(Propiedad propiedad, Pagination pagination){
		return masterP.findPropiedadList(propiedad, pagination);
	}
	
	public HibernatePropiedad createPropiedad(){
		return masterP.buildPropiedad();
	}
	
	public HibernatePropiedad savePropiedad(HibernatePropiedad propiedad){
		return masterP.savePropiedad(propiedad);
	}
	
	public void deletePropiedad(Propiedad propiedad){
		masterP.deletePropiedad(propiedad);
	}
	
	//
	// Persona
	//
	
	public List<HibernatePersona> findPersonaList(Persona persona, Pagination pagination){
		return masterP.findPersonaList(persona, pagination);
	}
	
	public List<HibernatePersona> findPersonaList(String query){
		return masterP.findPersonaList(query);
	}

	public HibernatePersona createPersona(){
		return masterP.createPersona();
	}

	public HibernatePersona savePersona(HibernatePersona persona){
		return masterP.savePersona(persona);
	}

	public void deletePersona(HibernatePersona persona){
		masterP.deletePersona(persona);
	}
}
