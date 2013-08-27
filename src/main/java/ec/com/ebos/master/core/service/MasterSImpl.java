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
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author Eduardo Plua Alay
 */
@Service("masterS")
public class MasterSImpl implements MasterS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("masterP")
    private MasterP masterP;
	
	//
    // Propiedad
    //

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination){
		return masterP.findPropiedadList(propiedad, pagination);
	}
	
	public Propiedad createPropiedad(){
		return masterP.buildPropiedad();
	}
	
	public Propiedad savePropiedad(Propiedad propiedad){
		return masterP.savePropiedad(propiedad);
	}
	
	public void deletePropiedad(Propiedad propiedad){
		masterP.deletePropiedad(propiedad);
	}
	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination){
		return masterP.findPersonaList(persona, pagination);
	}
	
	public List<Persona> findPersonaList(String query){
		return masterP.findPersonaList(query);
	}

	public Persona createPersona(){
		return masterP.buildPersona();
	}

	public Persona savePersona(Persona persona){
		return masterP.savePersona(persona);
	}

	public void deletePersona(Persona persona){
		masterP.deletePersona(persona);
	}
}
