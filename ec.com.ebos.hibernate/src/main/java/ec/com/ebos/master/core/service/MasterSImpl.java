package ec.com.ebos.master.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.core.master.model.EmpresaPersona;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.master.model.Propiedad;
import ec.com.ebos.core.master.process.MasterP;
import ec.com.ebos.core.master.service.MasterS;
import ec.com.ebos.core.orm.crud.Pagination;
import ec.com.ebos.master.model.hibernate.HibernateEmpresaPersona;
import ec.com.ebos.master.model.hibernate.HibernatePersona;
import ec.com.ebos.master.model.hibernate.HibernatePropiedad;

/**
 * @author Eduardo Plua Alay
 */
@Service(MasterS.BEAN_NAME)
public class MasterSImpl implements MasterS {
    
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
	
	public Propiedad getInstancePropiedad(){
		return new HibernatePropiedad();
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
		return masterP.createPersona();
	}

	public Persona savePersona(Persona persona){
		return masterP.savePersona(persona);
	}

	public void deletePersona(Persona persona){
		masterP.deletePersona(persona);
	}
	
	public Persona getInstancePersona(){
		return new HibernatePersona();
	}
	
	public EmpresaPersona getInstanceEmpresaPersona(){
		return new HibernateEmpresaPersona();
	}
}
