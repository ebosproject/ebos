package ec.com.ebos.appl.core.process;

import java.util.List;

import ec.com.ebos.appl.model.Bundle;
import ec.com.ebos.appl.model.Persona;
import ec.com.ebos.appl.model.Propiedad;
import ec.com.ebos.appl.model.Bundle.Localidad;
import ec.com.ebos.orm.crud.Pagination;

/**
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface ApplP {

    //
    // MessageResource
    //
	public Bundle getMessageResource(String codigo, Localidad localidad);
	
	public List<String> getCodeMessageResourceList(Localidad localidad);
	
	public Bundle saveBundle(Bundle messageResource);

	public List<Bundle> findBundleList(Bundle bundle, Pagination pagination);

	public Bundle buildBundle();

	public void deleteBundle(Bundle activeEntity);
	
	//
	// Propiedad
	//

	public List<Propiedad> findPropiedadList(Propiedad propiedad, Pagination pagination);

	public Propiedad buildPropiedad();

	public Propiedad savePropiedad(Propiedad propiedad);

	public void deletePropiedad(Propiedad propiedad);

	
	//
	// Persona
	//
	
	public List<Persona> findPersonaList(Persona persona, Pagination pagination);

	public Persona buildPersona();

	public Persona savePersona(Persona persona);

	public void deletePersona(Persona persona);
	
}
