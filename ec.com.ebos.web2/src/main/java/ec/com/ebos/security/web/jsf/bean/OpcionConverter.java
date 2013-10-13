package ec.com.ebos.security.web.jsf.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.admin.core.service.AdministracionS;
import ec.com.ebos.admin.model.Opcion;
import ec.com.ebos.util.StringUtils;

@Component
@ManagedBean(name = OpcionConverter.BEAN_NAME)
@RequestScoped
public class OpcionConverter implements Converter {
	
	public static final String BEAN_NAME = "opcionConverter";
	
	@Getter @Setter
    @ManagedProperty(value = AdministracionS.EL_BEAN_NAME)
    protected AdministracionS administracionS;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String submittedValue) throws ConverterException {
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			try {
				return administracionS.getOpcion(Long.parseLong(submittedValue));
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid object"));
			}
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		if (value == null || value.equals("")) {
			return StringUtils.EMPTY;
		} else {
			return String.valueOf(((Opcion) value).getId());
		}
	}

}
