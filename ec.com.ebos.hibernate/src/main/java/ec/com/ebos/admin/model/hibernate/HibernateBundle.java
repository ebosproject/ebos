package ec.com.ebos.admin.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.admin.model.Administracion;
import ec.com.ebos.admin.model.Bundle;
import ec.com.ebos.admin.model.hibernate.field.Bundle_;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
@Entity
@Table(name = HibernateBundle.TABLE_NAME, schema = Administracion.SCHEMA,
	uniqueConstraints = @UniqueConstraint(columnNames={Bundle_.codigo, Bundle_.localidad}))
@Data @EqualsAndHashCode(callSuper=false)
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class HibernateBundle extends Administracion<HibernateBundle> implements Bundle{

	private static final long serialVersionUID = -2896367216397132540L;
	
	protected static final String TABLE_NAME = "BUNDLE";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
		
	@Column(name = "codigo", nullable = false, length = 50)
	private String codigo;

	@Column(name = "localidad", nullable = false, length = 5)
	@Type(type = Localidad.TYPE)
	private Localidad localidad;

	@Column(name = "valor", nullable = false, length = 500)
	private String valor;
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.admin.model.Bundle#getKeyCache()
	 */
	public String getKeyCache(){
		return codigo+localidad.getValue();
	}
		  

}
