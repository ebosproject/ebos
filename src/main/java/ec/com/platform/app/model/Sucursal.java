package ec.com.platform.app.model;

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
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "APPTSUCURSAL", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class Sucursal extends GenericApp<Sucursal>{

	private static final long serialVersionUID = 7508531917964868788L;

	@Id
	@SequenceGenerator(name = "APPTSUCURSAL_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSSUCURSAL")
	@GeneratedValue(generator = "APPTSUCURSAL_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
	
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
	
}
