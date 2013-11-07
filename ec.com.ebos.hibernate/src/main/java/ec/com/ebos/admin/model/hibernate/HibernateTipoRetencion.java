package ec.com.ebos.admin.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.conta.model.hibernate.HibernateSaldoRetencion;
import ec.com.ebos.conta.model.hibernate.field.SaldoRetencion_;
import ec.com.ebos.core.admin.model.Administracion;
import ec.com.ebos.core.admin.model.TipoRetencion;
import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.conta.model.SaldoRetencion;

/**
 * Documento
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateTipoRetencion.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateTipoRetencion extends HibernateAdministracion implements TipoRetencion {

	private static final long serialVersionUID = -6748190361672935897L;

	protected static final String TABLE_NAME = "TIPO_RETENCION";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@OneToMany(mappedBy = SaldoRetencion_.tipoRetencion, fetch = FetchType.LAZY, targetEntity = HibernateSaldoRetencion.class)
    private Set<SaldoRetencion> saldoRetencionList = new HashSet<SaldoRetencion>(0);
    
}
