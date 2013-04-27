package ec.com.ebos.bitacora.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "BITTRUTA_LOG", schema = "EBOSBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class RutaLog extends Bitacora<RutaLog>{

	private static final long serialVersionUID = 6923351017759818531L;

	@Id
	@SequenceGenerator(name = "BITTRUTA_LOG_ID_GENERATOR", sequenceName = "EBOSBITA.BITSRUTA_LOG")
	@GeneratedValue(generator = "BITTRUTA_LOG_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
