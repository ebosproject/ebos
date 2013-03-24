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
@Table(name = "BITTTURNO", schema = "PLTFBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Turno extends GenericBitacora<Turno>{

	private static final long serialVersionUID = 3922934845182492539L;

	@Id
	@SequenceGenerator(name = "BITTTURNO_ID_GENERATOR", sequenceName = "PLTFBITA.BITSTURNO")
	@GeneratedValue(generator = "BITTTURNO_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
}
