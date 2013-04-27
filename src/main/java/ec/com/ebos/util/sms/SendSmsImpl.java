package ec.com.ebos.util.sms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ec.com.ebos.appl.Application;

@Component
public class SendSmsImpl implements SendSms {

    //
    // Dependencias
    //
    @Getter
    @Setter
    @Autowired
    private Application app; 
    
//    private Service srv;
//    ClickatellHTTPGateway gateway;

    
    @PostConstruct
    public void init() {
        if (!conectarClickatell()) {
            System.out.println("No se reconoce Gateway clickatell");
        } else {
            System.out.println("El Gateway clickatell esta listo para funcionar");
        }
    }

    private boolean conectarClickatell() {
//        Configuracion conf = app.getConfiguracion();
//        
//        OutboundNotification outboundNotification = new OutboundNotification();
//        System.out.println(Library.getLibraryDescription());
//        System.out.println("Version: " + Library.getLibraryVersion());
//
//        gateway = new ClickatellHTTPGateway(conf.getUrlSmsGateway(), conf.getAppidSmsGateway(), conf.getUsuarioSmsGateway(), conf.getPasswordSmsGateway());
//        gateway.setOutbound(true);
//        srv.getInstance();
//        srv.getInstance().setOutboundMessageNotification(outboundNotification);
//        gateway.setSecure(true);
//        try {
//            srv.getInstance().addGateway(gateway);
//            srv.getInstance().startService();
//
//            System.out.println("Estado de servicio Gateway Clickatell " + gateway.getStatus().toString());
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
    	return false;
    }
    
    
    private void desconectarClickatell() {
//        try {
//            if (srv.getInstance() != null) {
//                srv.getInstance().stopService();
//                srv.getInstance().removeGateway(gateway);
//            }
//            srv = null;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
    }

//    public class OutboundNotification implements IOutboundMessageNotification {
//
//        @Override
//        public void process(AGateway gateway, OutboundMessage msg) {
//            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
//        }
//    }

    @Override
    public void enviarSms(String num, String sms) {
    	
    	if (!app.getConfiguracion().isEnviarSmsGatewayPrx()) {
			return;
		}
    	
//        OutboundMessage msj = new OutboundMessage(formatoInternacional(num), sms);
//        boolean flag = false;
//        try {
//            System.out.println("Tiene cobertura la red del destinatario " + num + "?: " + gateway.queryCoverage(msj));
//
//            flag = srv.getInstance().sendMessage(msj);
//            System.out.println(msj);
//            System.out.println("Crédito restante " + gateway.queryBalance());
//
//            return flag;
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            return flag;
//        }    	
    }

    @SuppressWarnings("unused")
	private String formatoInternacional(String num) {
        String in = num.substring(1, num.length());
        in = "+593" + in;
        return in;
    }
    
    @PreDestroy
    public void destroy(){
        desconectarClickatell();
    }

}
