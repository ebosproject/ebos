package ec.com.ebos.util;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para trabajar con mensajes en base a claves y valores de archivos de recursos.
 * Incluye tambien mensajes para JSF.
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class MessageUtils {

	private MessageUtils() {
	}

	////////////////////// String Messages //////////////////////

	/**
	 * Permite obtener un valor de una clave de archivo de propiedades indicado
	 * @param key Clave String
	 * @param resource Archivo de properties {@link ResourceBundle}
	 * @param params Arreglo varargs de parametros Object...
	 * @update Eduardo Plua Alay 
	 * @return String
	 */
	public static String buildMessage(String key, ResourceBundle resource, Object... params) {
		try {
			return getFormattedMessage(resource.getString(key), params);
		} catch (Exception e) {
			return new StringBuilder(Constantes.PREFIX_NOT_FOUND).append(key).append(Constantes.PREFIX_NOT_FOUND).toString();			
		}		
	}

	public static String getFormattedMessage(String message, Object... params) {
		if (message == null) {
			return StringUtils.EMPTY;
		}
		if (ObjectUtils.isEmpty(params)) {
			return message;
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof Long) {
				params[i] = ((Long) params[i]).toString();
			}
		}
		//return new MessageFormat(message).format(params, new StringBuffer(), null).toString();
		return MessageFormat.format(message, params);
	}

	////////////////////// Simple Faces Messages //////////////////////

	public static void configureSimpleFacesMessage(FacesMessage fm, String summary,
			FacesMessage.Severity severity) {
		fm.setSeverity(severity);
		fm.setSummary(summary);
	}

	public static void addSimpleFacesMessage(FacesContext facesContext, String summary,
			FacesMessage.Severity severity) {
		FacesMessage fm = new FacesMessage();
		configureSimpleFacesMessage(fm, summary, severity);
		facesContext.addMessage(null, fm);
	}

	public static void addSimpleFacesMessage(String summary,
			FacesMessage.Severity severity) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		addSimpleFacesMessage(facesContext, summary, severity);
	}

	/**
	 * Elimina los FacesMessage del FacesContext actual cuya Severity sea menor o igual que maxSeverity.
	 * Si maxSeverity es null, se eliminan todos los FacesMessage.
	 * @param maxSeverity
	 */
	public static void clearFacesMessages(FacesMessage.Severity maxSeverity) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// iterate and remove: http://www.rgagnon.com/javadetails/java-0619.html
		for (Iterator<FacesMessage> iter = facesContext.getMessages(); iter.hasNext();) {
			FacesMessage message = iter.next();
			if (maxSeverity == null || message.getSeverity().compareTo(maxSeverity) <= 0) {
				iter.remove();
			}
		}
	}

	/**
	 * Elimina todos los FacesMessage del FacesContext actual.
	 */
	public static void clearFacesMessages() {
		clearFacesMessages(null);
	}

	////////////////////// Faces Messages //////////////////////

	public static void configureFacesMessage(FacesMessage fm, String keySummary,
			FacesMessage.Severity severity, ResourceBundle resource, Object... params) {
		fm.setSeverity(severity);
		fm.setSummary(buildMessage(keySummary, resource, params));
	}

	public static void addFacesMessageToComponent(FacesContext facesContext, UIComponent component, String keySummary,
			FacesMessage.Severity severity, ResourceBundle resource, Object... params) {
		FacesMessage fm = new FacesMessage();
		configureFacesMessage(fm, keySummary, severity, resource, params);
		facesContext.addMessage(component.getClientId(facesContext), fm);
	}

	public static void addFacesMessageToComponent(UIComponent component, String keySummary,
			FacesMessage.Severity severity, ResourceBundle resource, Object... params) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		addFacesMessageToComponent(facesContext, component, keySummary, severity, resource, params);
	}

	public static void addFacesMessage(FacesContext facesContext, String keySummary,
			FacesMessage.Severity severity, ResourceBundle resource, Object... params) {
		FacesMessage fm = new FacesMessage();
		configureFacesMessage(fm, keySummary, severity, resource, params);
		facesContext.addMessage(null, fm);
	}

	public static void addFacesMessage(String keySummary,
			FacesMessage.Severity severity, ResourceBundle resource, Object... params) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		addFacesMessage(facesContext, keySummary, severity, resource, params);
	}

	////////////////////// (Simple) Labeled Faces Messages //////////////////////

//	public static void addLabeledFacesMessage(FacesContext facesContext, UIComponent component, String message, FacesMessage.Severity severity) {
//		FacesMessage fm = new FacesMessage();
//		String msg = UtilMensajes.getString("LabeledMessage",
//				getLabel(facesContext, component), message);
//		configureSimpleFacesMessage(fm, msg, severity);
//		facesContext.addMessage(null, fm);
//	}

//	public static void addLabeledFacesMessageToComponent(FacesContext facesContext, UIComponent component, String message, FacesMessage.Severity severity) {
//		FacesMessage fm = new FacesMessage();
//		String msg = RootMensajes.getString("LabeledMessage",
//				getLabel(facesContext, component), message);
//		configureSimpleFacesMessage(fm, msg, severity);
//		facesContext.addMessage(component.getClientId(facesContext), fm);
//	}

//	public static void addLabeledFacesMessageToComponent(UIComponent component, String message, FacesMessage.Severity severity) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		addLabeledFacesMessageToComponent(facesContext, component, message, severity);
//	}

//	public static void addLabeledFacesMessage(UIComponent component, String message, FacesMessage.Severity severity) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		addLabeledFacesMessage(facesContext, component, message, severity);
//	}

	/**
	 * Gets the "label" property from the component.
	 * Copied from {@link javax.faces.component.MessageFactory#getLabel(FacesContext,UIComponent)}
	 * @param context
	 * @param component
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static Object getLabel(FacesContext context, UIComponent component) {
		Object o = component.getAttributes().get("label");
		if (o == null || (o instanceof String && ((String) o).length() == 0)) {
			o = component.getValueBinding("label");
		}
		// Use the "clientId" if there was no label specified.
		if (o == null) {
			o = component.getClientId(context);
		}
		return o;
	}

}
