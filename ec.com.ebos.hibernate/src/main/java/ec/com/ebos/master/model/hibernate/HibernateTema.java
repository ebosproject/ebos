package ec.com.ebos.master.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.Tema;
import ec.com.ebos.root.model.Entidad;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = HibernateTema.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
public class HibernateTema extends HibernateMaster implements Tema {
    
	private static final long serialVersionUID = -1002580062576804439L;

	protected static final String TABLE_NAME = "TEMA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;	

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;
    
	@Column(name = "imagen", nullable = false, length = 50)
    private String imagen;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    public HibernateTema(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }
    
}

