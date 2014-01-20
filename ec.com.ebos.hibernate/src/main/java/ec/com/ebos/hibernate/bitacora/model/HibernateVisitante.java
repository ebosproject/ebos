package ec.com.ebos.hibernate.bitacora.model;

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

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.bitacora.model.Bitacora;
import ec.com.ebos.core.bitacora.model.Visitante;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.hibernate.root.model.HibernateAuditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateVisitante.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateVisitante extends HibernateBitacora implements Visitante{

	private static final long serialVersionUID = -372384679228539239L;

	protected static final String TABLE_NAME = "VISITANTE";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	@Target(HibernateAuditoria.class)
	private Auditoria auditoria;
	
	@Column(name = "cedula", unique = true, nullable = false, length = 20)
	private String cedula;
		
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "apellidos", nullable = false, length = 50)
	private String apellidos;
	
}
