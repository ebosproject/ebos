package ec.com.ebos.conta.model;

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

import org.hibernate.annotations.Type;

import ec.com.ebos.aspect.annotation.Auditable;
import ec.com.ebos.conta.model.field.CentroCosto_;
import ec.com.ebos.conta.model.field.CentroSubcentro_;
import ec.com.ebos.conta.model.field.CuentaCentro_;
import ec.com.ebos.root.model.hibernate.HibernateEntidad;
import ec.com.ebos.root.model.hibernate.field.Entidad_;

/**
 * Tipos de centros de costo
 *
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @author <a href="mailto:vipconsultoresaso@gmail.com">Victor Viejo</a>
 * @since 2013/04/29
 */
@Entity
@Table(name = TipoCentroCosto.TABLE_NAME, schema = Contabilidad.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class TipoCentroCosto extends Contabilidad<TipoCentroCosto> {

	private static final long serialVersionUID = 4913746566919074839L;
	
	protected static final String TABLE_NAME = "TIPO_CENTRO_COSTO";
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
	 * Codigo de la estructura organizacional
	 */
	@Column(name = Entidad_.codigo, nullable = false, length = Entidad_.codigo_lenght)
	private String codigo;
	
	/**
	 * Descripcion o nombre de la estructura organizacional
	 */
	@Column(name = Entidad_.descripcion, nullable = false, length = Entidad_.descripcion_lenght)
	private String descripcion;	
	
	/**
	 * Indica si distrubuye o no
	 */
	@Column(name = "distribucion",nullable = false)
	private boolean distribucion = false;

	/**
	 * Estado del {@link TipoCentroCosto}
	 */
	@Column(name = "estado", nullable = false, length = 1)
    @Type(type = HibernateEntidad.Estado.TYPE)
    private HibernateEntidad.Estado estado = Estado.ACTIVO;
	
	@OneToMany(mappedBy = CentroCosto_.tipoCentroCosto, fetch = FetchType.LAZY)
    private Set<CentroCosto> centroCostoList = new HashSet<CentroCosto>(0);
	
	@OneToMany(mappedBy = CentroSubcentro_.tipoCentroCosto, fetch = FetchType.LAZY)
    private Set<CentroSubcentro> centroSubcentroList = new HashSet<CentroSubcentro>(0);
	
	@OneToMany(mappedBy = CuentaCentro_.tipoCentroCosto, fetch = FetchType.LAZY)
    private Set<CuentaCentro> cuentaCentroList = new HashSet<CuentaCentro>(0);
    
}
