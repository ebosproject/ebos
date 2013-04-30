package ec.com.ebos.conta.model;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.root.model.Entidad;

/**
 * Relacion de {@link CentroCosto} con sub{@link Centro} de costo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = CentroSubcentro.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class CentroSubcentro extends Contabilidad<CentroSubcentro> {

	private static final long serialVersionUID = 3656927531814475058L;

	protected static final String TABLE_NAME = "CENTRO_SUBCENTRO";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	/**
	 * Centro costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_costo", nullable = false)
    private CentroCosto centroCosto;

	/**
	 * Subcentro costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_subcentro_costo")
    private CentroCosto subcentroCosto;	
	
	/**
	 * Tipo de centro de costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_centro_costo")
    private TipoCentroCosto tipoCentroCosto;	
    
	/**
	 * Estado de la relacion 
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado = Estado.ACTIVO;
	
}
