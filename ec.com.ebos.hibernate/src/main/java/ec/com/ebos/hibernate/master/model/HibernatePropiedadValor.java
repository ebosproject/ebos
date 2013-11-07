package ec.com.ebos.hibernate.master.model;

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
import ec.com.ebos.core.master.model.Master;
import ec.com.ebos.core.master.model.Propiedad;
import ec.com.ebos.core.master.model.PropiedadValor;

/**
 * Theme clase
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-02-27
 */
@Entity
@Table(name = HibernatePropiedadValor.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false)
public class HibernatePropiedadValor extends HibernateMaster implements PropiedadValor {
    
	private static final long serialVersionUID = -5344259206776358470L;

	protected static final String TABLE_NAME = "PROPIEDAD_VALOR";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;	

	@ManyToOne(fetch=FetchType.LAZY, targetEntity = HibernatePropiedad.class)
	@JoinColumn(name = "id_propiedad", nullable = false)
    private Propiedad propiedad;	
	
	@Column(name = "valor", nullable = false, length = 50)
	private String valor;    	    
           
}

