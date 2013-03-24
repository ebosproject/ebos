package ec.com.platform.util.core.servicio;

import ec.com.platform.util.core.gestor.UtilG;
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
    @Qualifier("utilG")
    private UtilG utilG;
        
    /**
     * Proceso se ejecuta cada minuto
     */
    @Override
    @Scheduled(fixedDelay = 1*60000)
    public void sensarMail() {       
         utilG.sensarMail();
    }
    
    /**
     * Proceso se ejecuta cada dos minutos
     */
    @Override
    @Scheduled(fixedDelay = 2*60000)
    public void sensarSms() {       
         utilG.sensarSms();
    }
    
}

