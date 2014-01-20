package ec.com.ebos.hibernate.master.model;

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

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.master.model.Activo;
import ec.com.ebos.core.master.model.ActivoCustodio;
import ec.com.ebos.core.master.model.EmpresaPersona;
import ec.com.ebos.core.master.model.Master;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateActivoCustodio.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateActivoCustodio extends HibernateMaster implements ActivoCustodio{

	private static final long serialVersionUID = 8578280108376775316L;
	
	protected static final String TABLE_NAME = "ACTIVO_CUSTODIO";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
		
	@ManyToOne(targetEntity = HibernateActivo.class)
	@JoinColumn(name = "id_activo", nullable = false)
    private Activo activo;
        
    @ManyToOne(targetEntity = HibernateEmpresaPersona.class)
    @JoinColumn(name = "id_empresapersona", nullable = false)
    private EmpresaPersona empresaPersona;
	
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
