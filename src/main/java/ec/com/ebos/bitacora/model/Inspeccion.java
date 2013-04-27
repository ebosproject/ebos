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
@Table(name = "BITTINSPECCION", schema = "EBOSBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Inspeccion extends Bitacora<Inspeccion>{

	private static final long serialVersionUID = 3459900461151474054L;

	@Id
	@SequenceGenerator(name = "BITTINSPECCION_ID_GENERATOR", sequenceName = "EBOSBITA.BITSINSPECCION")
	@GeneratedValue(generator = "BITTINSPECCION_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
}
