package ec.com.ebos.master.web.jsf.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.mse.web.jsf.bean.MonaguilloBean;

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
	
	@Setter
	@ManagedProperty("#{param.id}")
    private Long id;
	
	@Getter
	private StreamedContent monaguilloImage;
	
	public ImageBean(){
	}
	
	@PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
        	monaguilloImage = new DefaultStreamedContent();
        }
        else {
        	// So, browser is requesting the image. Return a real StreamedContent with the image bytes.
        	Persona p = monaguilloBean.getPersona(id);
        	monaguilloImage = new DefaultStreamedContent(new ByteArrayInputStream(p.getImagen()), p.getContentType()); 
        }
        
    }
	
	public StreamedContent getPersonaImage(){
		return personaBean.getImage();
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
