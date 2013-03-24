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
@Table(name = "BITTRUTA", schema = "PLTFBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Ruta extends GenericBitacora<Ruta>{

	private static final long serialVersionUID = -4302893291410989102L;

	@Id
	@SequenceGenerator(name = "BITTRUTA_ID_GENERATOR", sequenceName = "PLTFBITA.BITSRUTA")
	@GeneratedValue(generator = "BITTRUTA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	
}
