package ec.com.ebos.conta.model;

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
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.master.model.Organizacion;

/**
 * Centros de costo que puede usar una empresa
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/28
 */
@Entity
@Table(name = CentroCostoEmpresa.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class CentroCostoEmpresa extends Contabilidad<CentroCostoEmpresa> {


	private static final long serialVersionUID = 3057838065566530983L;
	
	protected static final String TABLE_NAME = "CENTRO_COSTO_EMPRESA";
	private static final String SEQUENCE = Contabilidad.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Id de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
    private Long id;
	
	/**
	 * Centro costo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_padre", nullable = false)
    private CentroCosto centroCosto;
	
	/**
	 * Empresa
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion empresa;
		
}
