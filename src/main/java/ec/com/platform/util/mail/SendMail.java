package ec.com.platform.util.mail;

import javax.mail.internet.AddressException;

/**
 * @author Eduardo Plua Alay
 */
public interface SendMail {

    public void sendMail(String subject, String msg, String from, String tos, String ccs, String bccs) throws AddressException;

    public void sendMailGenerate(String subject, String msg, String from, String tos, String ccs, String bccs) throws AddressException;
    
}
