package ec.com.ebos.util.core.service;

import ec.com.ebos.util.core.process.UtilP;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eduardo Plua Alay
 */

@Service("utilS")
public class UtilSImpl implements UtilS {

    //
    // Servicios modulos
    //
    @Getter @Setter
    @Autowired
    @Qualifier("utilP")
    private UtilP utilP;
        
    /**
     * Proceso se ejecuta cada minuto
     */
    @Override
    @Scheduled(fixedDelay = 1*60000)
    public void sensarMail() {       
         utilP.sensarMail();
    }
    
    /**
     * Proceso se ejecuta cada dos minutos
     */
    @Override
    @Scheduled(fixedDelay = 2*60000)
    public void sensarSms() {       
         utilP.sensarSms();
    }
    
}

