package ec.com.ebos.mse.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.conta.model.AsientoDetalle;
import ec.com.ebos.conta.model.field.AsientoDetalle_;
import ec.com.ebos.master.model.Persona;
import ec.com.ebos.master.model.field.Persona_;
import ec.com.ebos.mse.model.field.Grupo_;
import ec.com.ebos.mse.model.field.Monagillo_;

/**
 * Monagillo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = Monaguillo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Monaguillo extends Mse<Monaguillo> {

	private static final long serialVersionUID = 8123939355501042044L;
	
	protected static final String TABLE_NAME = "MONAGILLO";
	private static final String SEQUENCE = Mse.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del monagillo
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Persona_.join)
    private Persona persona;

	@Column(name = Monagillo_.representantes, length = 200)
	private String representantes;
	
	@Column(name = Monagillo_.centroEstudio, length = 200)
	private String centroEstudio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Grupo_.join)
    private Grupo grupo;
	
	
	@OneToMany(mappedBy = AsientoDetalle_.asiento, fetch = FetchType.LAZY)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
}
