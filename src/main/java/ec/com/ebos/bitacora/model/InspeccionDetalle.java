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
@Table(name = "BITTINSPECCION_DETALLE", schema = "EBOSBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class InspeccionDetalle extends Bitacora<InspeccionDetalle>{

	private static final long serialVersionUID = -7309331268578348471L;

	@Id
	@SequenceGenerator(name = "BITTINSPECCION_DETALLE_ID_GENERATOR", sequenceName = "EBOSBITA.BITSINSPECCION_DETALLE")
	@GeneratedValue(generator = "BITTINSPECCION_DETALLE_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
