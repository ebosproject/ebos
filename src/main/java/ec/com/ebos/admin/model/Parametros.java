package ec.com.ebos.admin.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.master.model.Master;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = Parametros.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Parametros extends Administracion<Parametros>{
  
	private static final long serialVersionUID = 7865213458933031067L;

	protected static final String TABLE_NAME = "PARAMETROS";
	private static final String SEQUENCE = Master.SCHEMA+"."+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@Embedded
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
