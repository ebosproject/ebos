package ec.com.platform.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.platform.generic.model.Entidad;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = "APPTTEMA", schema = "PLTFAPPL")
@Data @EqualsAndHashCode(callSuper=false)
public class Tema extends GenericApp<Tema> {
    
	private static final long serialVersionUID = -1002580062576804439L;

	@Id
	@SequenceGenerator(name = "APPTTEMA_ID_GENERATOR", sequenceName = "PLTFAPPL.APPSTEMA")
	@GeneratedValue(generator = "APPTTEMA_ID_GENERATOR")
	private Long id;	

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;
    
	@Column(name = "imagen", nullable = false, length = 50)
    private String imagen;
	
    @Column(name = "estado", nullable = false, length = 1)
    @Type(type = Entidad.Estado.TYPE)
    private Entidad.Estado estado;
    
    public Tema(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }
    
}

