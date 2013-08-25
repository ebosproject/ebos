package ec.com.ebos.master.web.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.StreamedContent;

@ManagedBean(name = ImageBean.BEAN_NAME)
@RequestScoped
public class ImageBean implements Serializable{
	
	private static final long serialVersionUID = -8970475930766912497L;

	public static final String BEAN_NAME = "imageBean";
	
	@Getter @Setter
    @ManagedProperty(value = PersonaBean.EL_BEAN_NAME)
	protected PersonaBean personaBean;
	
	@Getter
	private StreamedContent graphicText;

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
	
	public StreamedContent getImage(){
		return personaBean.getImage();
	}
    
}
