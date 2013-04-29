package ec.com.ebos.conta.model;

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

import ec.com.ebos.master.model.Master;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * Asientos contables
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = TipoAsiento.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class TipoAsiento extends Contabilidad<TipoAsiento> {

	private static final long serialVersionUID = -7133451003821826238L;
	
	protected static final String TABLE_NAME = "TIPO_ASIENTO";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
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
	
	@Column(name = "formato_referencia", length = 20, nullable = false)
	private String formatoReferencia = "###-###-##########";
	
	/**
	 * Indica si distrubuye o no
	 */
	@Column(name = "distribucion",nullable = false)
	private boolean distribucion = false;
	
	@Embedded
	private Auditoria auditoria;
	
	/**
	 * Estado del {@link TipoAsiento}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;		
    
}
