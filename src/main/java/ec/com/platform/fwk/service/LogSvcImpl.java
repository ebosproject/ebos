package ec.com.platform.fwk.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 */
public class LogSvcImpl implements LogSvc {

	private static String TAB = "\t";
	private static String USUARIO = " Usuario: ";

	private static Log loggerCrud = LogFactory.getLog(LOG_CRUD);

	private static Log loggerNeg = LogFactory.getLog(LOG_INTCPT);

	private static Log loggerSAD = LogFactory.getLog(LOG_SAD);

	public void writeToLog(LogFwkSwiss pLogFwkSwiss) {
		String src = pLogFwkSwiss.getSrc();
		String usuario = "";//InfoContext.getLogin() + "@" + InfoContext.getIpUsuario();
		pLogFwkSwiss.setUsuario(usuario);
		String msg = TAB + pLogFwkSwiss.getAccion() + TAB + USUARIO + usuario + TAB
				+ pLogFwkSwiss.getEntity();
		if (src == null)
			throw new IllegalArgumentException(
					"No se conoce la fuente del mensaje");
		if (src.equals(LOG_INTCPT)) {
			loggerNeg.info(msg);
		} else if (src.equals(LOG_SAD)) {
			loggerSAD.info(msg);
		} else if (src.equals(LOG_CRUD)) {
			loggerCrud.info(msg);
		} else {
			msg = src + " " + msg;
			loggerNeg.info(msg);
		}
	}

}
