package ec.com.ebos.core.conta.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;

public interface TipoCuenta extends Contabilidad {

	public String getCodigo();

	public Set<CuentaContable> getCuentaContableList();

	public String getDescripcion();

	public Long getId();

	public CuentaContable.Naturaleza getNaturaleza();

	public TipoCuenta.Tipo getTipo();

	public void setCodigo(String codigo);

	public void setCuentaContableList(Set<CuentaContable> cuentaContableList);

	public void setDescripcion(String descripcion);

	public void setId(Long id);

	public void setNaturaleza(CuentaContable.Naturaleza naturaleza);

	public void setTipo(TipoCuenta.Tipo tipo);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
	/**
     * <strong>Tipos de Cuenta</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> B: Balance<br> P: Perdida o Ganancias<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum Tipo implements StringValuedEnum<Tipo> {

        BALANCE("B"),
        PERDIDA_GANANCIA("P");

        public static class Type extends StringValuedEnumType<Tipo> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".core.conta.model.TipoCuenta$Tipo$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Tipo(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Tipo> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Tipo.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Tipo> LIST = Arrays.asList(Tipo.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isBalance() {
            return this.equals(BALANCE);
        }

        public boolean isPerdidaOGanancia() {
            return this.equals(PERDIDA_GANANCIA);
        }
    }

}