package ec.com.ebos.bitacora.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

public interface Evento extends Bitacora {

	public Auditoria getAuditoria();

	public String getDescripcion();

	public Evento.EstadoEvento getEstado();

	public Set<EventoLog> getEventoLogList();

	public Date getFechaFin();

	public Date getFechaInicio();

	public Long getId();

	public String getIntegrantes();

	public String getObservacion();

	public Evento.EstadoEvento getTipoEvento();

	public void setAuditoria(Auditoria auditoria);

	public void setDescripcion(String descripcion);

	public void setEstado(Evento.EstadoEvento estado);

	public void setEventoLogList(Set<EventoLog> eventoLogList);

	public void setFechaFin(Date fechaFin);

	public void setFechaInicio(Date fechaInicio);

	public void setId(Long id);

	public void setIntegrantes(String integrantes);

	public void setObservacion(String observacion);

	public void setTipoEvento(Evento.EstadoEvento tipoEvento);

	public void setVisitantes(List<Visitante> visitantes);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public List<Visitante> getVisitantes();

	public void addVisitante(Visitante visitante);

	public void removeVisitante(Visitante visitante);
	
    /**
     * <strong>Estado de evento</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Estados </th>
     * <tr><td valign="top"> 
     * I: Ingreso <br> 
     * S: Salida <br>
     * </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum EstadoEvento implements StringValuedEnum<EstadoEvento> {

        INGRESO("I"),
        SALIDA("S");

        public static class Type extends StringValuedEnumType<EstadoEvento> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".bitacora.model.Evento$EstadoEvento$Type";
        
        @Getter
        private String value;
        private String labelKey;

        private EstadoEvento(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, EstadoEvento> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(EstadoEvento.values());
        /**
         * Lists para iteraciones
         */
        public static final List<EstadoEvento> LIST = Arrays.asList(EstadoEvento.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isIngresoCliente() {
            return this.equals(INGRESO);
        }
        
        public boolean isIngresoEmpleado() {
            return this.equals(SALIDA);
        }

    }
    
    /**
     * <strong>Tipo de evento</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> 
     * IC: IngresoCliente <br> 
     * IE: IngresoEmpleado <br>
     * IP: IngresoProveedor <br>
     * IV: IngresoVisitante <br>
     * </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum TipoEvento implements StringValuedEnum<TipoEvento> {

        INGRESO_CLIENTE("IC"),
        INGRESO_EMPLEADO("IE"),
        INGRESO_PROVEEDOR("IP"),
        INGRESO_VISITANTE("IV") ;

        public static class Type extends StringValuedEnumType<TipoEvento> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".bitacora.model.Evento$TipoEvento$Type";

        @Getter
        private String value;
        private String labelKey;

        private TipoEvento(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        public static final Map<String, TipoEvento> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoEvento.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoEvento> LIST = Arrays.asList(TipoEvento.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isIngresoCliente() {
            return this.equals(INGRESO_CLIENTE);
        }
        
        public boolean isIngresoEmpleado() {
            return this.equals(INGRESO_EMPLEADO);
        }
        
        public boolean isIngresoProveedor() {
            return this.equals(INGRESO_PROVEEDOR);
        }
        
        public boolean isIngresoVisitante() {
            return this.equals(INGRESO_VISITANTE);
        }

    }

}