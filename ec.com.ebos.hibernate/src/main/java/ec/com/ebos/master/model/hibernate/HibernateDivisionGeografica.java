package ec.com.ebos.master.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ec.com.ebos.conta.model.PeriodoFiscalPais;
import ec.com.ebos.conta.model.hibernate.field.PeriodoFiscalPais_;
import ec.com.ebos.master.model.DivisionGeografica;
import ec.com.ebos.master.model.Master;
import ec.com.ebos.master.model.field.DivisionGeografica_;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * Contiene la division geografica del pais
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = HibernateDivisionGeografica.TABLE_NAME, schema = Master.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class HibernateDivisionGeografica extends HibernateMaster implements DivisionGeografica{

	private static final long serialVersionUID = -3016906366966810717L;
	
	protected static final String TABLE_NAME = "DIVISION_GEOGRAFICA";
	private static final String SEQUENCE = Master.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	/**
	 * Codigo de la estructura organizacional
	 */
	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	/**
	 * {@link HibernateDivisionGeografica} padre
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_division_geografica")
	private DivisionGeografica padre;
	
	@Column(name = "nivel", nullable = false, length = 2)
	private int nivel = 1;
	
	@Column(name = "terminante", nullable = false, length = 2)
	private int terminante = 0;
	
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
	
	@OneToMany(mappedBy = PeriodoFiscalPais_.divisionGeografica, fetch = FetchType.LAZY)
    private Set<PeriodoFiscalPais> periodoFiscalPaisList = new HashSet<PeriodoFiscalPais>(0);
	
	@OneToMany(mappedBy = DivisionGeografica_.padre, fetch = FetchType.LAZY)
    private Set<DivisionGeografica> divisionGeograficaList = new HashSet<DivisionGeografica>(0);
}
