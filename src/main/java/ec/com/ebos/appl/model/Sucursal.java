package ec.com.ebos.appl.model;

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

import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = "APPTSUCURSAL", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false) 
public class Sucursal extends Application<Sucursal>{

	private static final long serialVersionUID = 7508531917964868788L;

	@Id
	@SequenceGenerator(name = "APPTSUCURSAL_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSSUCURSAL")
	@GeneratedValue(generator = "APPTSUCURSAL_ID_GENERATOR")
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
	
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;

	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
	
}
