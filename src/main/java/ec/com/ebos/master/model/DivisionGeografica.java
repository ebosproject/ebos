package ec.com.ebos.master.model;

import javax.persistence.Column;
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
import ec.com.ebos.root.model.Entidad;

/**
 * Contiene la division geografica del pais
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = DivisionGeografica.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class DivisionGeografica extends Master<DivisionGeografica>{

	private static final long serialVersionUID = -3016906366966810717L;
	
	protected static final String TABLE_NAME = "DIVISION_GEOGRAFICA";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Codigo de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	/**
	 * {@link DivisionGeografica} padre
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_division_geografica")
	private DivisionGeografica padre;
	
	@Column(name = "nivel", nullable = false, length = 2)
	private int nivel = 1;
	
	@Column(name = "terminante", nullable = false, length = 2)
	private int terminante = 0;
	
	/**
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad.CODIGO_NAME, nullable = false, length = Entidad.CODIGO_LENGTH)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad.DESCRIPCION_NAME, nullable = false, length = Entidad.DESCRIPCION_LENGTH)
	private String descripcion;	
}
