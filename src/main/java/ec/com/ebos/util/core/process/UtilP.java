package ec.com.ebos.util.core.process;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eduardo Plua Alay
 */

public interface UtilP{
   
    //
    // Tareas Programadas
    //
    
    @Async    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
    public void senseMail();
   
    @Async    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
    public void senseSms();

    public void sendMail(String subject, String msg, String from, String tos, String ccs, String bccs);
    
    //public boolean sendSms(String numero, String mensaje);
    
}

