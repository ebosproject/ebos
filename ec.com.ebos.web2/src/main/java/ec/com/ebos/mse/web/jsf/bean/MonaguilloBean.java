package ec.com.ebos.mse.web.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.master.core.service.MasterS;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.mse.model.Monaguillo;
import ec.com.ebos.orm.crud.Pagination;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.StringUtils;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-08-23
 */
@ManagedBean(name = MonaguilloBean.BEAN_NAME)
@SessionScoped
public class MonaguilloBean extends MseBean<Monaguillo> {

	private static final long serialVersionUID = 1936050047220453830L;

	public static final String BEAN_NAME = "monaguilloBean";
	
	@SuppressWarnings("el-syntax")
	public static final String EL_BEAN_NAME = "#{"+BEAN_NAME+"}";

	@Getter @Setter
    @ManagedProperty(value = "#{masterS}")
    protected MasterS masterS;
	
	@Getter @Setter
	private List<Persona> suggestionPersona;
	
	@Override
	public void getInit() {
		// Para busquedas
		entitySearch = new Monaguillo();
		entitySearch.setPersona(new Persona());
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
		TARGET_ID = "/modules/mse/monaguillo/finder.xhtml";
	}

	// /////////////////////// DATA MODEL ////////////////////////

	@Override
	protected List<Monaguillo> loadDataTableCollection(Monaguillo monaguillo,
			Pagination pagination) {
		return mseS.findMonaguilloList(monaguillo, pagination);
	}

	// ////////////////// ACCIONES ////////////////////

	@Override
	public void crear() {
		activeEntity = mseS.createMonaguillo();
	}

	@Override
	public void editar() {
		// activeEntity = mseS.loadMonagillo(activeEntity.getId());
	}

	@Override
	public void actualizar() {
		editar();
	}

	@Override
	public void guardar() {
		activeEntity = mseS.saveMonaguillo(activeEntity);
	}

	@Override
	public void eliminar() {
		mseS.deleteMonaguillo(activeEntity);
	}

	// ////////////////////// LISTS ///////////////////////////////

	// ////////////////// AUTOCOMPLETES ///////////////////////////
	public List<Persona> completePersona(String query){
		suggestionPersona = masterS.findPersonaList(query);
		return suggestionPersona;
	}
	
	// //////////////////// CONVERTERS ////////////////////////////
	public Converter getPersonaConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String submittedValue)
					throws ConverterException {
				if (submittedValue.trim().equals("")) {
					return null;
				} else {
					try {
						for(Persona p : suggestionPersona){
                			if(p.getId().equals(Long.parseLong(submittedValue))){
                				return p;
                			}
                		}
					} catch (NumberFormatException exception) {
						throw new ConverterException(new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid object"));
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
					throws ConverterException {
				if (value == null || value.equals("")) {
					return StringUtils.EMPTY;
				} else {
					return String.valueOf(((Persona) value).getId());
				}
			}
		};
	}
	
	// ////////////////////// GETTERS /////////////////////////////////
	public Persona getPersona(Long id){
		for(Persona p : suggestionPersona){
			if(p.getId().equals(id)){
				return p;
			}
		}
		return null;
	}

}
