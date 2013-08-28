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
import ec.com.ebos.conta.model.AsientoDetalle;
import ec.com.ebos.conta.model.field.AsientoDetalle_;
import ec.com.ebos.mse.model.field.Grupo_;
import ec.com.ebos.mse.model.field.MonaguilloGrupo_;

/**
 * Monagillo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 *
 * @since 2013/04/28
 */
@Entity
@Table(name = Grupo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Grupo extends Mse<Grupo> {
	
	private static final long serialVersionUID = 2824372366156085256L;
	
	protected static final String TABLE_NAME = "GRUPO";
	private static final String SEQUENCE = Mse.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del grupo
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;

	@Column(name = Grupo_.nombre, length = 30, nullable = false)
	private String nombre;
	
	@OneToMany(mappedBy = AsientoDetalle_.asiento, fetch = FetchType.LAZY)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
	@OneToMany(mappedBy = MonaguilloGrupo_.grupo, fetch = FetchType.LAZY)
    private Set<MonaguilloGrupo> monaguilloGrupoList = new HashSet<MonaguilloGrupo>(0);
	
}
