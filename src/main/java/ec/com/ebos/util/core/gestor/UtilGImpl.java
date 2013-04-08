package ec.com.ebos.util.core.gestor;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.ebos.generic.core.gestor.GenericGImpl;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.exception.UtilException;
import ec.com.ebos.util.mail.SendMail;

/**
 * 
 * @author Eduardo Plua Alay
 */
@Repository("utilG")
public class UtilGImpl extends GenericGImpl<Object, UtilException> implements UtilG {

	private static final long serialVersionUID = 8272908095248096866L;
	
	@Override
    protected String getBundleName(){
    	return Constantes.DOMAIN_NAME+".util.resources.util";
    }
	
	//
	// Dependencias
	//
//	@Getter @Setter
//    @Autowired
//    private Application app;
//	
//	@Getter @Setter
//    @Autowired
//    private SendSms sendSms;
	
    @Getter @Setter
    @Autowired
	private SendMail sendMail;
	
	//
	// Tareas Programadas
	//
	/**
	 * Sensa la tabla enviasms, envia mensajes existentes y posteriormente los
	 * mueve a la tabla historica enviahsms
	 */
	@Override
	public void sensarMail() {		

		// TODO (epa): Implementar logica de negocio
		
		String subject = "asunto";
		String mensaje = "mensaje";
		String from = "noresponder@commsms.com.ec";
		String tos = "correo1@domain.com[,correo2@domain.com,..]";
		String ccs = "correo1@domain.com[,correo2@domain.com,..]";
		String bccs = "correo1@domain.com[,correo2@domain.com,..]";


		// Envio del correo
		sendMail(subject, mensaje, from, tos, ccs, bccs);

	}

	/**
	 * Sensa la tabla enviasms, envia mensajes existentes y posteriormente los
	 * mueve a la tabla historica enviahsms
	 */
	@Override
	public void sensarSms() {

		// TODO (epa): Implementar logica de negocio

	}

	//
	// Mail
	//
	/**
	 * Envio de correo por demanda
	 * 
	 * @param subject
	 *            Asunto
	 * @param msg
	 *            Mensaje
	 * @param from
	 *            Remitente
	 * @param tos
	 *            Destinatarios
	 * @param ccs
	 *            Copia
	 * @param bccs
	 *            Copia Oculta
	 */
	@Override
	public void sendMail(String subject, String msg, String from, String tos,
			String ccs, String bccs) {
//		try {
//			sendMail.sendMail(subject, msg, from, tos, ccs, bccs);
//		} catch (AddressException ex) {
//		} catch (MailException ex) {
//		}
	}

	//
	// Sms
	//
//	/**
//	 * Envio de sms por demanda
//	 * 
//	 * @param numero
//	 *            Numero destinatario
//	 * @param mensaje
//	 *            Mensaje de texto
//	 * @return true si el mensaje fue enviado correctamente
//	 */
//	@Override
//	public boolean sendSms(String numero, String mensaje) {
//		return sendSms(numero, mensaje);
//	}

}
