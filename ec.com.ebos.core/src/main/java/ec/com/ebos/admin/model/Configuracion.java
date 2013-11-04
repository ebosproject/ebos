package ec.com.ebos.admin.model;

import java.util.HashMap;

import ec.com.ebos.root.model.Auditoria;

public interface Configuracion extends Administracion{

	public String getAppidSmsGateway();

	public Auditoria getAuditoria();

	public Short getAutenticarMail();

	public String getBaudioSms();

	public String getCentralSms();

	public String getComSms();

	public String getCorreoMail();

	public Short getEnviarEmail();

	public Short getEnviarSms();

	public Short getEnviarSmsGateway();

	public Long getId();

	public HashMap<String, String> getParametros();

	public String getPasswordMail();

	public String getPasswordSmsGateway();

	public Integer getPortMail();

	public String getServidorMail();

	public Short getTlsMail();

	public String getUrlSmsGateway();

	public String getUsuarioMail();

	public String getUsuarioSmsGateway();

	public void setAppidSmsGateway(String appidSmsGateway);

	public void setAuditoria(Auditoria auditoria);

	public void setAutenticarMail(Short autenticarMail);

	public void setBaudioSms(String baudioSms);

	public void setCentralSms(String centralSms);

	public void setComSms(String comSms);

	public void setCorreoMail(String correoMail);

	public void setEnviarEmail(Short enviarEmail);

	public void setEnviarSms(Short enviarSms);

	public void setEnviarSmsGateway(Short enviarSmsGateway);

	public void setId(Long id);

	public void setParametros(HashMap<String, String> parametros);

	public void setPasswordMail(String passwordMail);

	public void setPasswordSmsGateway(String passwordSmsGateway);

	public void setPortMail(Integer portMail);

	public void setServidorMail(String servidorMail);

	public void setTlsMail(Short tlsMail);

	public void setUrlSmsGateway(String urlSmsGateway);

	public void setUsuarioMail(String usuarioMail);

	public void setUsuarioSmsGateway(String usuarioSmsGateway);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	//
	//Proxies
	//
	public boolean isEnviarSmsPrx();

	public void setEnviarSmsPrx(boolean enviarSmsPrx);

	public boolean isEnviarEmailPrx();

	public void setEnviarEmailPrx(boolean enviarEmailPrx);

	public boolean isEnviarSmsGatewayPrx();

	public void setEnviarSmsGatewayPrx(boolean enviarSmsGatewayPrx);

	public boolean isTlsMailPrx();

	public void setTlsMailPrx(boolean tlsMailPrx);

	public boolean isAutenticarMailPrx();

	public void setAutenticarMailPrx(boolean isAutenticarMailPrx);

}