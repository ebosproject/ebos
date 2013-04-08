package ec.com.ebos.util.mail;

import java.util.Properties;
import javax.mail.internet.AddressException;
import lombok.Getter;
import lombok.Setter;
import ec.com.ebos.administracion.model.Configuracion;
import ec.com.ebos.app.Application;
import ec.com.ebos.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class SendMailImpl extends JavaMailSenderImpl implements MailSender, SendMail {

    //
    // Dependencias
    //
    @Getter @Setter
    @Autowired
    private Application app;
    
    private String remitente = null;
    
    public SendMailImpl() {                
    }
    
    /**
     * Metodo que lee configuracion del sistema
     */
    private void obtenerConfiguracion(){
        Configuracion conf = app.getConfiguracion();
        this.remitente = conf.getCorreoMail();
        
        this.setHost(conf.getServidorMail());
        this.setPort(conf.getPortMail());
        this.setUsername(conf.getUsuarioMail());
        this.setPassword(conf.getPasswordMail());

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", conf.isAutenticarMailPrx());
        javaMailProperties.put("mail.smtp.starttls.enable", conf.isTlsMailPrx());

        this.setJavaMailProperties(javaMailProperties);
    }

    @Override
    public void sendMail(String subject, String msg, String from, String tos, String ccs, String bccs) throws AddressException{        
    	
    	if (!app.getConfiguracion().isEnviarEmailPrx()) {
			return;
		}
    	
        obtenerConfiguracion();
        
        if (tos != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(this.remitente);
            message.setSubject(subject);
            message.setText(msg);
            message.setTo(StringUtils.split(tos, ","));
            message.setCc(StringUtils.split(ccs == null ? "" : ccs, ","));
            message.setBcc(StringUtils.split(bccs == null ? "" : bccs, ","));
            send(message);
        }
    }

    @Override
    public void sendMailGenerate(String subject, String msg, String from, String tos, String ccs, String bccs) throws AddressException{

        if (from == null) {
            from = "noresponder@commsms.com.ec";
        }
        msg = msg + "\r\n\r\n\r\nCommsms: Este correo ha sido generado automaticamente";

        sendMail(subject, msg, from, tos, ccs, bccs);
    }
  
}
