package ec.com.ebos.master.web.jsf.bean.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import ec.com.ebos.context.EbosContext;
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
	protected void crear() {
		activeEntity = masterS.createPersona();
		image = null;
	}

	@Override
	protected void editar() {
		setImage();
	}

	@Override
	protected void actualizar() {
		editar();
	}

	@Override
	protected void guardar() {
//		if(uploadImage != null){
//			activeEntity.setImagen(uploadImage.getContents());
//		}
		activeEntity = masterS.savePersona(activeEntity);
		
		setImage();
	}
	
	private void setImage(){
		if(activeEntity.getImagen() != null){
			image = new DefaultStreamedContent(new ByteArrayInputStream(activeEntity.getImagen()),activeEntity.getContentType());
		}
	}

	@Override
	protected void eliminar() {
		masterS.deletePersona(activeEntity);
	}
	
//	public void upload() {
//        if(uploadImage != null) {
//            FacesMessage msg = new FacesMessage("Succesful", uploadImage.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }
	
	public void handleFileUpload(FileUploadEvent event) {
		//FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		activeEntity.setImagen(event.getFile().getContents());
		activeEntity.setContentType(event.getFile().getContentType());
		//image = new DefaultStreamedContent(new ByteArrayInputStream(activeEntity.getImagen()),activeEntity.getContentType());
		setImage();
		//FacesContext.getCurrentInstance().addMessage(null, msg);
		putSuccess("image.success.uploaded", event.getFile().getFileName());
		//EbosContext.updateComponent("image");
	}

	/////////////////////////// LISTS ///////////////////////////

	@Getter
	private List<Persona.TipoIdentificacion> tipoIdentificacionList = new ArrayList<Persona.TipoIdentificacion>(
			Persona.TipoIdentificacion.LIST);

	@Getter
	private List<Persona.TipoPersona> tipoPersonaList = new ArrayList<Persona.TipoPersona>(
			Persona.TipoPersona.LIST);
	
	@Getter
	private List<Persona.Genero> generoList = new ArrayList<Persona.Genero>(Persona.Genero.LIST);

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
