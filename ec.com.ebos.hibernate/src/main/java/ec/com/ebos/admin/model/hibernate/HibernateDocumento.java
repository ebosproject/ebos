package ec.com.ebos.admin.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.admin.model.Administracion;
import ec.com.ebos.admin.model.Documento;
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.hibernate.HibernateAsiento;
import ec.com.ebos.conta.model.hibernate.HibernateAsientoDetalle;
import ec.com.ebos.conta.model.hibernate.HibernateDocumentoDistribucion;
import ec.com.ebos.conta.model.hibernate.field.AsientoDetalle_;
import ec.com.ebos.conta.model.hibernate.field.Asiento_;
import ec.com.ebos.conta.model.hibernate.field.DocumentoDistribucion_;

/**
 * Documento
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = HibernateDocumento.TABLE_NAME, schema = Administracion.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateDocumento extends Administracion<HibernateDocumento> implements Documento {

	private static final long serialVersionUID = -6748190361672935897L;

	protected static final String TABLE_NAME = "DOCUMENTO";
	private static final String SEQUENCE = Administracion.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	@OneToMany(mappedBy = Asiento_.documento, fetch = FetchType.LAZY)
    private Set<HibernateAsiento> asientoList = new HashSet<HibernateAsiento>(0);
	
	@OneToMany(mappedBy = AsientoDetalle_.documento, fetch = FetchType.LAZY)
    private Set<HibernateAsientoDetalle> asientoDetalleList = new HashSet<HibernateAsientoDetalle>(0);
    
	@OneToMany(mappedBy = DocumentoDistribucion_.documento, fetch = FetchType.LAZY)
    private Set<HibernateDocumentoDistribucion> documentoDistribucionList = new HashSet<HibernateDocumentoDistribucion>(0);
	
	@Column
	Date emitido;

	@Column
	Date vencimiento;

	@Column
	Date enviado;
	
	@Column
	Date autorizado;
	
	@Column
	Date procesado;

	@Column
	Date contabilizado;

}
