package ec.com.platform.bitacora.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "BITTINSPECCION", schema = "PLTFBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Inspeccion extends GenericBitacora<Inspeccion>{

	private static final long serialVersionUID = 3459900461151474054L;

	@Id
	@SequenceGenerator(name = "BITTINSPECCION_ID_GENERATOR", sequenceName = "PLTFBITA.BITSINSPECCION")
	@GeneratedValue(generator = "BITTINSPECCION_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
}
