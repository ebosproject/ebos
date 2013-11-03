package ec.com.ebos.conta.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

public interface Periodo {

	public String getCodigo();

	public Date getCreacion();

	public String getDescripcion();

	public Ejercicio getEjercicio();

	public Periodo.Estado getEstado();

	public Date getFechaInicial();

	public Long getId();

	public Ejercicio getPeriodoFiscalPais();

	public Set<SaldoCentroCosto> getSaldoCentroCostoList();

	public Set<SaldoCuentaCentro> getSaldoCuentaCentroList();

	public Set<SaldoCuentaContable> getSaldoCuentaContableList();

	public Set<SaldoRetencion> getSaldoRetencionList();

	public BigDecimal getValorUtilidad();

	public void setCodigo(String codigo);

	public void setCreacion(Date creacion);

	public void setDescripcion(String descripcion);

	public void setEjercicio(Ejercicio ejercicio);

	public void setEstado(Periodo.Estado estado);

	public void setFechaInicial(Date fechaInicial);

	public void setId(Long id);

	public void setPeriodoFiscalPais(Ejercicio periodoFiscalPais);

	public void setSaldoCentroCostoList(Set<SaldoCentroCosto> saldoCentroCostoList);

	public void setSaldoCuentaCentroList(Set<SaldoCuentaCentro> saldoCuentaCentroList);

	public void setSaldoCuentaContableList(Set<SaldoCuentaContable> saldoCuentaContableList);

	public void setSaldoRetencionList(Set<SaldoRetencion> saldoRetencionList);

	public void setValorUtilidad(BigDecimal valorUtilidad);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
    /**
     * <strong>Estado A/I para cualquier Entidad</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Estados </th>
     * <tr><td valign="top"> A: Activo<br> I: Inactivo<br> </td></tr>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
    public enum Estado implements StringValuedEnum<Estado> {

        PENDIENTE("P"),
        VIGENTE("V"),
        ABIERTO("A"),
        CERRADO("C"),
        ;

        public static class Type extends StringValuedEnumType<Estado> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".root.model.Periodo$Estado$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Estado(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Estado> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Estado.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Estado> LIST = Arrays.asList(Estado.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isPendiente() {
            return this.equals(PENDIENTE);
        }

        public boolean isVigente() {
            return this.equals(VIGENTE);
        }

        public boolean isCerrado() {
            return this.equals(CERRADO);
        }

        public boolean isAbierto() {
            return this.equals(ABIERTO);
        }
    }

}