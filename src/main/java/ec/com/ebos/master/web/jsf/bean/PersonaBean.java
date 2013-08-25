package ec.com.ebos.master.web.jsf.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import ec.com.ebos.master.model.Persona;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-04-02
 */
@ManagedBean(name = PersonaBean.BEAN_NAME)
@SessionScoped
public class PersonaBean extends MasterBean<Persona> {

	private static final long serialVersionUID = 783070179851922363L;

	public static final String BEAN_NAME = "personaBean";
	
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";
	
	@Getter
	@Setter
	private UploadedFile uploadImage;
	
	@Getter
	@Setter
	private StreamedContent image;
	
	@Override
	public void getInit() {
		entitySearch = new Persona();
	}

	@Override
	protected void habilitaControles() {
		setHabilitaCrear();
		setHabilitaEditar();
		setHabilitaGuardar();
		setHabilitaEliminar();

		if (EntityUtils.isPersistent(activeEntity)) {

		} else {
			setHabilitaEliminar(false);
			// TODO (epa): La plataforma no debe permitir para todos las
			// pantallas mostrar el boton eliminar si la
			// entidad activa no esta persistida en la base de datos
		}
	}

	@Override
	protected void initTarget() {
		TARGET_ID = "/modules/master/persona/finder.xhtml";
	}

	// /////////////////////// DATA MODEL ////////////////////////

	@Override
	protected List<Persona> loadDataTableCollection(Persona persona,
			Pagination pagination) {
		return masterS.findPersonaList(persona, pagination);
	}

	// ////////////////// ACCIONES ////////////////////

	@Override
	public void crear() {
		activeEntity = masterS.createPersona();
	}

	@Override
	public void editar() {
		image = new DefaultStreamedContent(new ByteArrayInputStream(activeEntity.getImagen()),activeEntity.getContentType());
	}

	@Override
	public void actualizar() {
		editar();
	}

	@Override
	public void guardar() {
		if(uploadImage != null){
			activeEntity.setImagen(uploadImage.getContents());
		}
		activeEntity = masterS.savePersona(activeEntity);
	}

	@Override
	public void eliminar() {
		masterS.deletePersona(activeEntity);
	}
	
	public void upload() {
        if(uploadImage != null) {
            FacesMessage msg = new FacesMessage("Succesful", uploadImage.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		activeEntity.setImagen(event.getFile().getContents());
		activeEntity.setContentType(event.getFile().getContentType());
		image = new DefaultStreamedContent(new ByteArrayInputStream(activeEntity.getImagen()),activeEntity.getContentType());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/////////////////////////// LISTS ///////////////////////////

	@Getter
	private List<Persona.TipoIdentificacion> tipoIdentificacionList = new ArrayList<Persona.TipoIdentificacion>(
			Persona.TipoIdentificacion.LIST);

	@Getter
	private List<Persona.TipoPersona> tipoPersonaList = new ArrayList<Persona.TipoPersona>(
			Persona.TipoPersona.LIST);

	//////////////////////// VALIDATORS ////////////////////////
	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		UploadedFile file = (UploadedFile) value;
		int fileByte = file.getContents().length;
		if (fileByte > 15360) {
			msgs.add(new FacesMessage("Too big must be at most 15KB"));
		}
		if (!(file.getContentType().startsWith("image"))) {
			msgs.add(new FacesMessage("not an Image file"));
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}
	
}
