package ec.com.ebos.hibernate.mse.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.master.model.Persona;
import ec.com.ebos.core.mse.model.Monaguillo;
import ec.com.ebos.core.mse.model.MonaguilloGrupo;
import ec.com.ebos.core.mse.model.Mse;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.master.model.HibernatePersona;
import ec.com.ebos.hibernate.master.model.field.Persona_;
import ec.com.ebos.hibernate.mse.model.field.MonaguilloGrupo_;
import ec.com.ebos.hibernate.mse.model.field.Monaguillo_;

/**
 * Monagillo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateMonaguillo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@Auditable
public class HibernateMonaguillo extends HibernateMse implements Monaguillo {

	private static final long serialVersionUID = 8123939355501042044L;
	
	protected static final String TABLE_NAME = "MONAGUILLO";
	private static final String SEQUENCE = Mse.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id del monagillo
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	private Auditoria auditoria;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = HibernatePersona.class)
	@JoinColumn(name = Persona_.id_persona, nullable = false)
    private Persona persona;

	@Column(name = Monaguillo_.representantes, length = 200)
	private String representantes;
	
	@Column(name = Monaguillo_.centroEstudio, length = 200)
	private String centroEstudio;
	
	@OneToMany(mappedBy = MonaguilloGrupo_.monaguillo, fetch = FetchType.LAZY, targetEntity = HibernateMonaguilloGrupo.class)
    private Set<MonaguilloGrupo> monaguilloGrupoList = new HashSet<MonaguilloGrupo>(0);
	
}
