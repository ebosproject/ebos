package ec.com.platform.generic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import lombok.Getter;
import lombok.Setter;
import ec.com.platform.seguridad.model.Usuario;

/**
 * Clase de auditoria para las Entity
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-10
 */
@Embeddable
public class Auditoria implements Serializable{

	private static final long serialVersionUID = -2195848600759541457L;

	@Getter @Setter
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "usuarioCreacion_id", nullable = false)
	private Usuario usuarioCreacion;
	
	@Getter @Setter
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "usuarioModificacion_id")
	private Usuario usuarioModificacion;

	@Getter @Setter
	@Column(name = "fechaCreacion", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	@Getter @Setter
	@Column(name = "fechaModificacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaModificacion;
}
