package ec.com.ebos.core.util.process;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.ebos.core.util.exception.UtilException;
import ec.com.ebos.core.util.mail.SendMail;

/**
 * 
 * @author Eduardo Plua Alay
 */
@Repository("utilP")
public class UtilPImpl implements UtilP {

	private static final long serialVersionUID = 8272908095248096866L;
	
	//
	// Dependencias
	//
//	@Getter @Setter
//    @Autowired
//    private Master app;
//	
//	@Getter @Setter
//    @Autowired
//    private SendSms sendSms;
	
//    @Getter @Setter
//    @Autowired
	private SendMail sendMail;
	
	//
	// Tareas Programadas
	//
	/**
	 * Sensa la tabla enviasms, envia mensajes existentes y posteriormente los
	 * mueve a la tabla historica enviahsms
	 */
	@Override
	public void senseMail() {		

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
	public void senseSms() {

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
