package ec.com.ebos.master.web.jsf.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.Converter;

import lombok.Getter;
import lombok.Setter;

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
	
	public ImageBean(){
//		BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);  
//	    Graphics2D g2 = bufferedImg.createGraphics();  
//	    g2.drawString("This is a text", 0, 10);  
//	    ByteArrayOutputStream os = new ByteArrayOutputStream();  
//	    try {
//			ImageIO.write(bufferedImg, "png", os);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//	    graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
	}
	
	public StreamedContent getPersonaImage(){
		return personaBean.getImage();
	}
	
	// ////////////////// AUTOCOMPLETES ///////////////////////////
	public List<Persona> completePersona(String query){
		return monaguilloBean.completePersona(query);
	}
	
	public List<Persona> getSuggestionPersona(){
		return monaguilloBean.getSuggestionPersona();
	}
	
	/////////////////// CONVERTERS ////////////////////////////////
	public Converter getPersonaConverter(){
		return monaguilloBean.getPersonaConverter();
	}
	
}
