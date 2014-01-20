package ec.com.ebos.hibernate.admin.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;

import ec.com.ebos.core.admin.model.Administracion;
import ec.com.ebos.core.admin.model.Parametros;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = HibernateParametros.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateParametros extends HibernateAdministracion implements Parametros, Auditoria{
  
	private static final long serialVersionUID = 7865213458933031067L;

	protected static final String TABLE_NAME = "PARAMETROS";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;

	@Column(length = 10, nullable = false)
    private String grupo;
    
    @Column(length = 15, nullable = false, unique= true)
    private String clave;
    
    @Column(length = 10, nullable=false)
    private String valor;
    
    @Column(name = "ESTADO", nullable=false, length=1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado  ;
    
}
