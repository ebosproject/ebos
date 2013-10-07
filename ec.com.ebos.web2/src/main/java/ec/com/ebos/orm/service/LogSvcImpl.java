package ec.com.ebos.orm.service;

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

	public void writeToLog(LogEbos pLogFwkEbos) {
		String src = pLogFwkEbos.getSrc();
		String usuario = "";//InfoContext.getLogin() + "@" + InfoContext.getIpUsuario();
		pLogFwkEbos.setUsuario(usuario);
		String msg = TAB + pLogFwkEbos.getAccion() + TAB + USUARIO + usuario + TAB
				+ pLogFwkEbos.getEntity();
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
