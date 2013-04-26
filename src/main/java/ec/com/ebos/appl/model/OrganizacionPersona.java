package ec.com.ebos.appl.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Entity
@Table(name = "ORGANIZACION_PERSONA", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class OrganizacionPersona extends Application<OrganizacionPersona>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4785805315184931890L;

	@Id
	@SequenceGenerator(name = "APPTEMPRESA_PERSONA_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSEMPRESA_PERSONA")
	@GeneratedValue(generator = "APPTEMPRESA_PERSONA_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "organizacion_id", nullable = false)
    private Empresa empresa;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
