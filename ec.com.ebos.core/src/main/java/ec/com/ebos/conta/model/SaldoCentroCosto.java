package ec.com.ebos.conta.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import ec.com.ebos.root.model.Auditoria;
import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.Constantes;
import ec.com.ebos.util.EntityUtils;
import ec.com.ebos.util.type.StringValuedEnum;
import ec.com.ebos.util.type.StringValuedEnumReflect;
import ec.com.ebos.util.type.StringValuedEnumType;

public interface SaldoCentroCosto {

	public Auditoria getAuditoria();

	public CentroCosto getCentroCosto();

	public Entidad.Estado getEstado();

	public Long getId();

	public Periodo getPeriodo();

	public BigDecimal getSaldoInicial();

	public SaldoCentroCosto.TipoSaldo getTipoSaldo();

	public BigDecimal getValorDebe();

	public BigDecimal getValorHaber();

	public void setAuditoria(Auditoria auditoria);

	public void setCentroCosto(CentroCosto centroCosto);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setPeriodo(Periodo periodo);

	public void setSaldoInicial(BigDecimal saldoInicial);

	public void setTipoSaldo(SaldoCentroCosto.TipoSaldo tipoSaldo);

	public void setValorDebe(BigDecimal valorDebe);

	public void setValorHaber(BigDecimal valorHaber);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
    /**
     * <strong>Tipo saldo de un {@link HibernateSaldoCentroCosto}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> CTR: Centro<br> SCT: Subcentro<br> FLJ: Flujo de caja<br> </td></tr>
     * </table>
     *
     * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum TipoSaldo implements StringValuedEnum<TipoSaldo> {
    	
        CENTRO("CTR"),
        SUBCENTRO("SCT"),
        FLUJO_CAJA("FLJ");

        public static class Type extends StringValuedEnumType<TipoSaldo> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".conta.model.SaldoCentroCosto$TipoSaldo$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private TipoSaldo(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, TipoSaldo> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoSaldo.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoSaldo> LIST = Arrays.asList(TipoSaldo.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isCentro() {
            return this.equals(CENTRO);
        }

        public boolean isSubcentro() {
            return this.equals(SUBCENTRO);
        }
        
        public boolean isFlujoCaja() {
            return this.equals(FLUJO_CAJA);
        }
    }

}