package ec.com.ebos.appl.core.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ec.com.ebos.appl.core.process.ApplP;
import ec.com.ebos.appl.model.Bundle;
import ec.com.ebos.appl.model.Persona;
import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.orm.crud.Pagination;

/**
 * @author Eduardo Plua Alay
 */
@Service("applS")
public class ApplSImpl implements ApplS{
    
	private static final long serialVersionUID = 4753228280831766443L;

	
	@Getter @Setter
    @Autowired
    @Qualifier("applP")
    private ApplP applP;
	
	//
    // Bundle
    //
	
	@Override
	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination) {		
		return applP.findBundleList(bundle, pagination);
	}
	
	@Override
	public Bundle getMessageResource(String codigo, Bundle.Localidad localidad){
		return applP.getMessageResource(codigo, localidad);
	}

	@Override
	public List<String> getCodeMessageResourceList(Bundle.Localidad localidad) {
		return applP.getCodeMessageResourceList(localidad);
	}
	
	@Override
	public Bundle createBundle() {
		return applP.buildBundle();
	}
	
	@Override
	public Bundle saveBundle(Bundle messageResource) {
        return applP.saveBundle(messageResource);
	}

	@Override
	public void deleteBundle(Bundle bundle) {
		applP.deleteBundle(bundle);
	}
	
	
	//
    // Propiedad
    //

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination){
		return applP.findPropiedadList(propiedad, pagination);
	}
	
	public Propiedad createPropiedad(){
		return applP.buildPropiedad();
	}
	
	public Propiedad savePropiedad(Propiedad propiedad){
		return applP.savePropiedad(propiedad);
	}
	
	public void deletePropiedad(Propiedad propiedad){
		applP.deletePropiedad(propiedad);
	}
	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination){
		return applP.findPersonaList(persona, pagination);
	}

	public Persona createPersona(){
		return applP.buildPersona();
	}

	public Persona savePersona(Persona persona){
		return applP.savePersona(persona);
	}

	public void deletePersona(Persona persona){
		applP.deletePersona(persona);
	}
}
