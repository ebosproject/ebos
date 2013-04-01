package ec.com.platform.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = "APPTMESSAGE_RESOURCE", schema = "PLTFAPPL",
	uniqueConstraints = @UniqueConstraint(columnNames={"codigo", "localidad"}))
@Data @EqualsAndHashCode(callSuper=false)
public class Bundle extends GenericApp<Bundle>{

	private static final long serialVersionUID = -2896367216397132540L;

	@Id
	@SequenceGenerator(name = "APPTMESSAGE_RESOURCE_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSMESSAGE_RESOURCE")
	@GeneratedValue(generator = "APPTMESSAGE_RESOURCE_ID_GENERATOR")
	private Long id;
		
	@Column(name = "codigo", nullable = false, length = 50)
	private String codigo;

	@Column(name = "localidad", nullable = false, length = 5)
	private String localidad; //TODO (epa): cambiar tipo String por Enum

	@Column(name = "valor", nullable = false, length = 500)
	private String valor;

}
