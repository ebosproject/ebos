package ec.com.ebos.bitacora.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.generic.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "BITTEVENTO_LOG", schema = "EBOSBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class EventoLog extends GenericBitacora<EventoLog>{

	private static final long serialVersionUID = 4194216821641946007L;

	@Id
	@SequenceGenerator(name = "BITTEVENTO_LOG_ID_GENERATOR", sequenceName = "EBOSBITA.BITSEVENTO_LOG")
	@GeneratedValue(generator = "BITTEVENTO_LOG_ID_GENERATOR")
	private Long id;
			
	@Embedded
	private Auditoria auditoria;
	
	@ManyToOne
	@JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
	
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;
			
	
}
