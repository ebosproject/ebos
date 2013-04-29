package ec.com.ebos.logis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ec.com.ebos.conf.model.DB_STRUCTURE;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author <a href="mailto:vipconsultoresaso@gmail.com">VIP Consultores</a>
 */
@Entity
@Table(name = ProductoBase.TABLENAME, schema = EntidadLogistica.SCHEMA_OWNER)
@Data @EqualsAndHashCode(callSuper=false) 
public abstract class ProductoBase extends EntidadLogistica<ProductoBase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4664966746836596369L;
	public static final String TABLENAME = "ProductoBase";
	
	@Id
	@SequenceGenerator(name = TABLENAME+"_ID_GENERATOR", sequenceName = EntidadLogistica.SCHEMA_OWNER+".S_"+TABLENAME)
	@GeneratedValue(generator = TABLENAME+"_ID_GENERATOR")
	private Long id;
	
}
