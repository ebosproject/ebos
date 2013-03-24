package ec.com.platform.administracion.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.platform.generic.model.Auditoria;
import ec.com.platform.generic.model.Generic;
import ec.com.platform.util.type.Type;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = "ADMTPARAMETROS", schema = "PLTFADMI")
@Data @EqualsAndHashCode(callSuper=false) 
public class Parametros extends GenericAdministracion<Parametros>{
  
	private static final long serialVersionUID = 7865213458933031067L;

	@Id
	@SequenceGenerator(name = "ADMTPARAMETROS_ID_GENERATOR", sequenceName = "PLTFADMI.ADMSPARAMETROS")
	@GeneratedValue(generator = "ADMTPARAMETROS_ID_GENERATOR")
    private Long id;
	
	@Embedded
	private Auditoria auditoria;

	@Column(length=10, nullable=false)
    private String grupo;
    
    @Column(length=15, nullable=false, unique= true)
    private String clave;
    
    @Column(length=10, nullable=false)
    private String valor;
    
    @Column(name = "estado")
    @Type(type = Generic.Estado.TYPE)
    private Generic.Estado estado;
    
}
