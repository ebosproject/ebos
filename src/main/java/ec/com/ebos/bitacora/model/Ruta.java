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
@Table(name = "BITTRUTA", schema = "EBOSBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Ruta extends Bitacora<Ruta>{

	private static final long serialVersionUID = -4302893291410989102L;

	@Id
	@SequenceGenerator(name = "BITTRUTA_ID_GENERATOR", sequenceName = "EBOSBITA.BITSRUTA")
	@GeneratedValue(generator = "BITTRUTA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
