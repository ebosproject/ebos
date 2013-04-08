package ec.com.ebos.administracion.model;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ec.com.ebos.generic.model.Auditoria;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = "ADMTCONFIGURACION", schema = "EBOSADMI")
@Data @EqualsAndHashCode(callSuper=false) 
public class Configuracion extends GenericAdministracion<Configuracion> {

	private static final long serialVersionUID = -6748190361672935897L;

	@Id
	@SequenceGenerator(name = "ADMTCONFIGURACION_ID_GENERATOR", sequenceName = "EBOSADMI.ADMSCONFIGURACION")
	@GeneratedValue(generator = "ADMTCONFIGURACION_ID_GENERATOR")
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
    
    @Column(name = "IS_SMS")
    private Short enviarSms;
    
    @Column(name = "IS_EMAIL")
    private Short enviarEmail;
    
    @Column(name = "COM_SMS")
    private String comSms;
    
    @Column(name = "BAUDIO_SMS")
    private String baudioSms;
    
    @Column(name = "SERVIDOR_MAIL")
    private String servidorMail;
    
    @Column(name = "CORREO_MAIL")
    private String correoMail;
    
    @Column(name = "IS_AUTENTICAR_MAIL")    
    private Short autenticarMail;
    
    @Column(name = "USUARIO_MAIL")    
    private String usuarioMail;
    
    @Column(name = "PASSWORD_MAIL")    
    private String passwordMail;
    
    @Column(name = "IS_TLS_MAIL")
    private Short tlsMail;    
    
    @Column(name = "PORT_SMTP")
    private Integer portMail;
    
    @Column(name = "CODCENTRALSMS")
    private String centralSms;
    
    @Column(name = "IS_HTTPGATEWAY")
    private Short enviarSmsGateway;
    
    @Column(name = "URL_GATEWAY")
    private String urlSmsGateway;
    
    @Column(name = "PWD_GATEWAY")
    private String passwordSmsGateway;
    
    @Column(name = "USR_GATEWAY")
    private String usuarioSmsGateway;
        
    @Column(name = "APP_ID_GATEWAY")
    private String appidSmsGateway;
    
    @Transient       
    private HashMap<String, String> parametros = new HashMap<String, String>();
       
    
    //
    //Proxies
    //
    public boolean isEnviarSmsPrx() {
        return enviarSms.equals(Short.valueOf("1"));
    }
    
    public void setEnviarSmsPrx(boolean enviarSmsPrx) {        
        this.enviarSms = Short.valueOf(enviarSmsPrx ? "1" : "0");
    }
     
    public boolean isEnviarEmailPrx(){
        return enviarEmail.equals(Short.valueOf("1"));
    }
    
    public void setEnviarEmailPrx(boolean enviarEmailPrx) {                
        this.enviarEmail = Short.valueOf(enviarEmailPrx ? "1" : "0");
    }
    
    public boolean isEnviarSmsGatewayPrx(){
        return enviarSmsGateway.equals(Short.valueOf("1"));
    }
    
    public void setEnviarSmsGatewayPrx(boolean enviarSmsGatewayPrx) {        
        this.enviarSmsGateway = Short.valueOf(enviarSmsGatewayPrx ? "1" : "0");
    }
    
    public boolean isTlsMailPrx() {
        return tlsMail.equals(Short.valueOf("1"));
    }
    
    public void setTlsMailPrx(boolean tlsMailPrx) {
        this.tlsMail = Short.valueOf(tlsMailPrx ? "1" : "0");
    }
    
    public boolean isAutenticarMailPrx(){
        return autenticarMail.equals(Short.valueOf("1"));
    }
    
    public void setAutenticarMailPrx(boolean isAutenticarMailPrx) {
        this.autenticarMail = Short.valueOf(isAutenticarMailPrx ? "1" : "0");
    }
}
