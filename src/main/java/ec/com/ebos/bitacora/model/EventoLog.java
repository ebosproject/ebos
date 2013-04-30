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
import ec.com.ebos.root.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = EventoLog.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class EventoLog extends Bitacora<EventoLog>{

	private static final long serialVersionUID = 4194216821641946007L;

	protected static final String TABLE_NAME = "EVENTO_LOG";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
			
	@Embedded
	private Auditoria auditoria;
	
	@ManyToOne
	@JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;
	
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;
			
	
}
