package ec.com.ebos.conta.model.hibernate;

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
import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.Contabilidad;
import ec.com.ebos.conta.model.PeriodoFiscalPais;
import ec.com.ebos.master.model.DivisionGeografica;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * Periodos fiscales definidos para cada pais
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernatePeriodoFiscalPais.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernatePeriodoFiscalPais extends HibernateContabilidad implements PeriodoFiscalPais {

	private static final long serialVersionUID = -6957465871366953630L;

	protected static final String TABLE_NAME = "PERIODO_FISCAL_PAIS";
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
	 * Division geografica
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_division_geografica")
	private DivisionGeografica divisionGeografica;
	
	/**
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;	
	
}
