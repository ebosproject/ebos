package ec.com.ebos.bitacora.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import lombok.Getter;

import org.hibernate.annotations.Type;

import ec.com.ebos.bitacora.resources.BitacoraMensajes;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.GenericUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
@Entity
@Table(name = Evento.TABLE_NAME, schema = Bitacora.SCHEMA)
@Data @EqualsAndHashCode(callSuper=false) 
public class Evento extends Bitacora<Evento>{

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
    @Type(type = Evento.EstadoEvento.TYPE)
    private Evento.EstadoEvento tipoEvento;
	
	@Column(name = "estado", nullable = false)
    @Type(type = Evento.EstadoEvento.TYPE)
    private Evento.EstadoEvento estado;

	@Column(name = "fechaInicio", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Column(name = "fechaFin", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	@OneToMany(mappedBy = "evento", fetch= FetchType.LAZY)
    private Set<EventoLog> eventoLogList = new HashSet<EventoLog>(0);
	
	@Transient
	private List<Visitante> visitantes; 
	
	public List<Visitante> getVisitantes(){
		if(integrantes == null){
			visitantes = new ArrayList<Visitante>();
			String[] lista = integrantes.split("|@|");
			for (String obj : lista) {
				visitantes.add((Visitante) ((Object) obj));
			}
		}
		return visitantes;
	}
	
	public void addVisitante(Visitante visitante){
		visitantes.add(visitante);
		integrantes += "|@|"+visitante.toString();
	}
	
	public void removeVisitante(Visitante visitante){
		visitantes.remove(visitante);
		integrantes.replace("|@|"+visitante.toString(), "");
	}
	
	
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
                GenericUtils.buildLabeledEnumMap(EstadoEvento.values());
        /**
         * Lists para iteraciones
         */
        public static final List<EstadoEvento> LIST = Arrays.asList(EstadoEvento.values());

        @Override
        public String getLabel() {
            return BitacoraMensajes.getString(labelKey);
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
                GenericUtils.buildLabeledEnumMap(TipoEvento.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoEvento> LIST = Arrays.asList(TipoEvento.values());

        @Override
        public String getLabel() {
            return BitacoraMensajes.getString(labelKey);
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
