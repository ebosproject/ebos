package ec.com.ebos.appl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = "APPTPROPIEDAD_VALOR", schema = "EBOSAPPL")
@Data @EqualsAndHashCode(callSuper=false)
public class PropiedadValor extends Application<PropiedadValor> {
    
	private static final long serialVersionUID = -5344259206776358470L;

	@Id
	@SequenceGenerator(name = "APPTPROPIEDAD_VALOR_ID_GENERATOR", sequenceName = "EBOSAPPL.APPSPROPIEDAD_VALOR")
	@GeneratedValue(generator = "APPTPROPIEDAD_VALOR_ID_GENERATOR")
	private Long id;	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad propiedad;	
	
	@Column(name = "valor", nullable = false, length = 50)
	private String valor;    	    
           
}

