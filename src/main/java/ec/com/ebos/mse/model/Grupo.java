package ec.com.ebos.mse.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Table(name = Grupo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Grupo extends Mse<Grupo> {
	
	private static final long serialVersionUID = 2824372366156085256L;
	
	protected static final String TABLE_NAME = "NOMBRE";
	private static final String SEQUENCE = Mse.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del grupo
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@Column(name = Grupo_.nombre, length = 30)
	private String nombre;
	
	@OneToMany(mappedBy = Monagillo_.grupo, fetch = FetchType.LAZY)
    private Set<Monaguillo> monagilloList = new HashSet<Monaguillo>(0);
	
}
