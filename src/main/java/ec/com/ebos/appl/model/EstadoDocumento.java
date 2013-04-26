package ec.com.ebos.appl.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "TipoDocumento", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class EstadoDocumento extends Application<EstadoDocumento>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5403152185348437720L;

	@Id
	@SequenceGenerator(name = "TIPODOCUMENTO_ID_GENERATOR", sequenceName = "EBOSAPPL.S_TIPODOCUMENTO")
	@GeneratedValue(generator = "TIPODOCUMENTO_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "codigo", nullable = false, length = Entidad.CODIGO)
	private String codigo;

	@Column(name = "descripcion", nullable = false, length = Entidad.DESCRIPCION)
	private String descripcion;


	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
