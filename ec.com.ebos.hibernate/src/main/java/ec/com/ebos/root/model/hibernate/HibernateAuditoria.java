package ec.com.ebos.root.model.hibernate;

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
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.security.model.Usuario;

/**
 * Clase de auditoria para las Entity
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-10
 */
@Embeddable
public class HibernateAuditoria implements Serializable, Auditoria{

	private static final long serialVersionUID = -2195848600759541457L;

	@Getter @Setter
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_creador", nullable = false, updatable=false)
	private Usuario creador;
	
	@Getter @Setter
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_modificador")
	private Usuario modificador;

	@Getter @Setter
	@Column(name = "creacion", nullable = false, updatable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date creado;
	
	@Getter @Setter
	@Column(name = "modificacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date modificado;
}
