package ec.com.ebos.mse.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * Grupo de Monagillos
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 * @since 2013/04/28
 */
@Entity
@Table(name = MonaguilloGrupo.TABLE_NAME, schema = Mse.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class MonaguilloGrupo extends Mse<MonaguilloGrupo> {
	
	
	private static final long serialVersionUID = 3071315408504111078L;
	
	protected static final String TABLE_NAME = "MONAGUILLO_GRUPO";
	private static final String SEQUENCE = Mse.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	private Auditoria auditoria;

	@ManyToOne
    @JoinColumn(name = "id_monaguillo", nullable = false)
    private Monaguillo monaguillo;
	
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;
            
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
