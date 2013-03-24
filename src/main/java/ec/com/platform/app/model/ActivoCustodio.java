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
@Table(name = "APPTACTIVO_CUSTODIO", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class ActivoCustodio extends GenericApp<ActivoCustodio>{

	private static final long serialVersionUID = 8578280108376775316L;

	@Id
	@SequenceGenerator(name = "APPTACTIVO_CUSTODIO_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSACTIVO_CUSTODIO")
	@GeneratedValue(generator = "APPTACTIVO_CUSTODIO_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "activo_id", nullable = false)
    private Activo activo;
        
    @ManyToOne
    @JoinColumn(name = "empresapersona_id", nullable = false)
    private EmpresaPersona empresaPersona;
	
	@Column(name = "estado", nullable = false)
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
	
}
