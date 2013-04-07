package ec.com.platform.fwk.service;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 *
 */
public class LogFwkPLatform implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7147564629353641097L;
	private String src;
	private String usuario;
	private String entity;
	private String accion;
	private Date fecha;
	
	
	/**
	 * @param pSrc
	 * @param pEntity
	 * @param pAccion
	 */
	public LogFwkPLatform(String pSrc, String pEntity, String pAccion) {
		src = pSrc;
		entity = pEntity;
		accion = pAccion;
		fecha = new Date();
	}

	/**
	 * @param pSrc
	 * @param pUsuario
	 * @param pEntity
	 * @param pAccion
	 */
	public LogFwkPLatform(String pSrc, String pUsuario, String pEntity, String pAccion) {
		fecha = new Date();
		src = pSrc;
		usuario = pUsuario;
		entity = pEntity;
		accion = pAccion;
	}
	
	/**
	 * Metodo (getter) de acceso a src.
	 * @return Retorna src.
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * Metodo (getter) de acceso a src.
	 * @return Retorna src.
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * Metodo (setter) de Modfificacion de src
	 * @param pSrc src a Asignar.
	 */
	public void setSrc(String pSrc) {
		src = pSrc;
	}
	/**
	 * Metodo (getter) de acceso a accion.
	 * @return Retorna accion.
	 */
	public String getAccion() {
		return accion;
	}
	/**
	 * Metodo (setter) de Modfificacion de accion
	 * @param pAccion accion a Asignar.
	 */
	public void setAccion(String pAccion) {
		accion = pAccion;
	}
	/**
	 * Metodo (getter) de acceso a entity.
	 * @return Retorna entity.
	 */
	public String getEntity() {
		return entity;
	}
	/**
	 * Metodo (setter) de Modfificacion de entity
	 * @param pEntity entity a Asignar.
	 */
	public void setEntity(String pEntity) {
		entity = pEntity;
	}

	/**
	 * Metodo (getter) de acceso a usuario.
	 * @return Retorna usuario.
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * Metodo (setter) de Modfificacion de usuario
	 * @param pUsuario usuario a Asignar.
	 */
	public void setUsuario(String pUsuario) {
		usuario = pUsuario;
	}
	
}
