package ec.com.ebos.conf.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 * 
 */
@Entity
@Table(name = DB_STRUCTURE.DIVISION_GEOGRAFICA, schema = DB_STRUCTURE.SCHEMA_OWNER)
@Data @EqualsAndHashCode(callSuper=false) 
public class DivisionGeografica extends Entidad<DivisionGeografica>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804333888657399677L;

	@Id
	@SequenceGenerator(name = DB_STRUCTURE.DIVISION_GEOGRAFICA+"_ID_GENERATOR", 
						sequenceName = DB_STRUCTURE.SCHEMA_OWNER+".S_"+DB_STRUCTURE.DIVISION_GEOGRAFICA)
	@GeneratedValue(generator = DB_STRUCTURE.DIVISION_GEOGRAFICA+"_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "codigo", nullable = false, length = Entidad.CODIGO_LENGTH)
	private String codigo;
	
	@Column(name = "descripcion", nullable = false, length = Entidad.DESCRIPCION_LENGTH)
	private String descripcion;
	
	@Column(name = "padre_id")
	private Long padre;

	@Column(name = "nivel", nullable = false)
	private Integer nivel;
	
	@Column(name = "terminante", nullable = false)
	private int terminante;
	
}
