package ec.com.ebos.bitacora.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ec.com.ebos.bitacora.model.hibernate.HibernateEvento;
import ec.com.ebos.bitacora.model.hibernate.HibernateEventoLog;
import ec.com.ebos.bitacora.model.hibernate.HibernateVisitante;
import ec.com.ebos.root.model.Auditoria;

public interface Evento {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public HibernateEvento.EstadoEvento getEstado();

	public Set<HibernateEventoLog> getEventoLogList();

	public Date getFechaFin();

	public Date getFechaInicio();

	public Long getId();

	public String getIntegrantes();

	public String getObservacion();

	public HibernateEvento.EstadoEvento getTipoEvento();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEstado(HibernateEvento.EstadoEvento estado);

	public void setEventoLogList(Set<HibernateEventoLog> eventoLogList);

	public void setFechaFin(Date fechaFin);

	public void setFechaInicio(Date fechaInicio);

	public void setId(Long id);

	public void setIntegrantes(String integrantes);

	public void setObservacion(String observacion);

	public void setTipoEvento(HibernateEvento.EstadoEvento tipoEvento);

	public void setVisitantes(List<HibernateVisitante> visitantes);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public List<HibernateVisitante> getVisitantes();

	public void addVisitante(HibernateVisitante visitante);

	public void removeVisitante(Visitante visitante);

}