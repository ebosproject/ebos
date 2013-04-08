package ec.com.ebos.util.core.gestor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eduardo Plua Alay
 */

public interface UtilG{
   
    //
    // Tareas Programadas
    //
    
    @Async    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
    public void sensarMail();
   
    @Async    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
    public void sensarSms();

    public void sendMail(String subject, String msg, String from, String tos, String ccs, String bccs);
    
    //public boolean sendSms(String numero, String mensaje);
    
}

