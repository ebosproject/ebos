package ec.com.ebos.hibernate.mse.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.AsientoDetalle;
import ec.com.ebos.core.mse.model.Grupo;
import ec.com.ebos.core.mse.model.MonaguilloGrupo;
import ec.com.ebos.core.mse.model.Mse;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.conta.model.HibernateAsientoDetalle;
import ec.com.ebos.hibernate.conta.model.field.AsientoDetalle_;
import ec.com.ebos.hibernate.mse.model.field.Grupo_;
import ec.com.ebos.hibernate.mse.model.field.MonaguilloGrupo_;

/**
 * Monagillo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 *
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateGrupo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
@Auditable
public class HibernateGrupo extends HibernateMse implements Grupo {
	
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
	
	@Embedded
	private Auditoria auditoria;

	@Column(name = Grupo_.nombre, length = 30, nullable = false)
	private String nombre;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
	@OneToMany(mappedBy = AsientoDetalle_.asiento, fetch = FetchType.LAZY, targetEntity = HibernateAsientoDetalle.class)
    private Set<AsientoDetalle> asientoDetalleList = new HashSet<AsientoDetalle>(0);
	
	@OneToMany(mappedBy = MonaguilloGrupo_.grupo, fetch = FetchType.LAZY, targetEntity = HibernateMonaguilloGrupo.class)
    private Set<MonaguilloGrupo> monaguilloGrupoList = new HashSet<MonaguilloGrupo>(0);
	
}
