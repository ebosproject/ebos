package ec.com.ebos.master.web.jsf.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.mse.web.jsf.bean.MonaguilloBean;
import ec.com.ebos.util.NumberUtils;

@Component
@ManagedBean(name = ImageBean.BEAN_NAME)
@RequestScoped
public class ImageBean implements Serializable{
	
	private static final long serialVersionUID = -8970475930766912497L;

	public static final String BEAN_NAME = "imageBean";
	
	@Getter @Setter
    @ManagedProperty(value = PersonaBean.EL_BEAN_NAME)
	protected PersonaBean personaBean;
	
	@Getter @Setter
    @ManagedProperty(value = MonaguilloBean.EL_BEAN_NAME)
	protected MonaguilloBean monaguilloBean;
	
	public StreamedContent getPersonaImage(){
		return personaBean.getImage();
	}
	
	public StreamedContent obtenerMonaguilloImage(Long id){
		Persona p = monaguilloBean.getPersona(id);
		StreamedContent image = new DefaultStreamedContent(new ByteArrayInputStream(p.getImagen()), p.getContentType());
		return image;
	}
	
	
//	// ////////////////// AUTOCOMPLETES ///////////////////////////
//	public List<Persona> completePersona(String query){
//		return monaguilloBean.completePersona(query);
//	}
//	
//	public List<Persona> getSuggestionPersona(){
//		return monaguilloBean.getSuggestionPersona();
//	}
//	
//	/////////////////// CONVERTERS ////////////////////////////////
//	public Converter getPersonaConverter(){
//		return monaguilloBean.getPersonaConverter();
//	}
	
}
