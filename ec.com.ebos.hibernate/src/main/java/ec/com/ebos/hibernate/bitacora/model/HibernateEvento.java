package ec.com.ebos.hibernate.bitacora.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import ec.com.ebos.core.aspect.annotation.Auditable;
import ec.com.ebos.core.bitacora.model.Bitacora;
import ec.com.ebos.core.bitacora.model.Evento;
import ec.com.ebos.core.bitacora.model.EventoLog;
import ec.com.ebos.core.bitacora.model.Visitante;
import ec.com.ebos.core.root.model.Auditoria;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = HibernateEvento.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
@Auditable
public class HibernateEvento extends HibernateBitacora implements Evento{

	private static final long serialVersionUID = 3922934845182492539L;

	protected static final String TABLE_NAME = "EVENTO";
	private static final String SEQUENCE = Bitacora.SCHEMA+".S"+TABLE_NAME;
	private static final String GENERATOR = TABLE_NAME+"_ID_GENERATOR";

	@Id
	@SequenceGenerator(name = GENERATOR, sequenceName = SEQUENCE)
	@GeneratedValue(generator = GENERATOR)
	private Long id;
	
	@Embedded
	private Auditoria auditoria;
		
	@Column(name = "descripcion", nullable = false, length = 50)
	private String descripcion;
	
	@Column(name = "observacion", length = 500)
	private String observacion;

	@Column(name = "integrantes", nullable = false, length = 1000)
	private String integrantes;

	@Column(name = "tipoEvento", nullable = false)
    @Type(type = HibernateEvento.EstadoEvento.TYPE)
    private HibernateEvento.EstadoEvento tipoEvento;
	
	@Column(name = "estado", nullable = false)
    @Type(type = HibernateEvento.EstadoEvento.TYPE)
    private HibernateEvento.EstadoEvento estado;

	@Column(name = "fechaInicio", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Column(name = "fechaFin", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	@OneToMany(mappedBy = "evento", fetch= FetchType.LAZY, targetEntity = HibernateEventoLog.class)
    private Set<EventoLog> eventoLogList = new HashSet<EventoLog>(0);
	
	@Transient
	private List<Visitante> visitantes; 
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.bitacora.model.Evento#getVisitantes()
	 */
	public List<Visitante> getVisitantes(){
		if(integrantes == null){
			visitantes = new ArrayList<Visitante>();
			String[] lista = integrantes.split("|@|");
			for (String obj : lista) {
				visitantes.add((HibernateVisitante) ((Object) obj));
			}
		}
		return visitantes;
	}
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.bitacora.model.Evento#addVisitante(ec.com.ebos.bitacora.model.HibernateVisitante)
	 */
	public void addVisitante(Visitante visitante){
		visitantes.add(visitante);
		integrantes += "|@|"+visitante.toString();
	}
	
	/* (non-Javadoc)
	 * @see ec.com.ebos.bitacora.model.Evento#removeVisitante(ec.com.ebos.bitacora.model.HibernateVisitante)
	 */
	public void removeVisitante(Visitante visitante){
		visitantes.remove(visitante);
		integrantes.replace("|@|"+visitante.toString(), "");
	}
	
}
