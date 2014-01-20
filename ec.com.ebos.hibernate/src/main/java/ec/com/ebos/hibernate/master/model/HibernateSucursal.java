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

import ec.com.ebos.core.master.model.Master;
import ec.com.ebos.core.master.model.Organizacion;
import ec.com.ebos.core.master.model.Sucursal;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateSucursal.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class HibernateSucursal extends HibernateMaster implements Sucursal{

	private static final long serialVersionUID = 7508531917964868788L;

	protected static final String TABLE_NAME = "SUCURSAL";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
		
	@ManyToOne(targetEntity = HibernateOrganizacion.class)
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
	
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
