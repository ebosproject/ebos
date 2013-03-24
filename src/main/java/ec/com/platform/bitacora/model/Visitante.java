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

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "BITTEVISITANTE", schema = "PLTFBITA")
@Data @EqualsAndHashCode(callSuper=false) 
public class Visitante extends GenericBitacora<Visitante>{

	private static final long serialVersionUID = -372384679228539239L;

	@Id
	@SequenceGenerator(name = "BITTVISITANTE_ID_GENERATOR", sequenceName = "PLTFBITA.BITSVISITANTE")
	@GeneratedValue(generator = "BITTVISITANTE_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
	@Column(name = "cedula", unique = true, nullable = false, length = 20)
	private String cedula;
		
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "apellidos", nullable = false, length = 50)
	private String apellidos;
	
}
