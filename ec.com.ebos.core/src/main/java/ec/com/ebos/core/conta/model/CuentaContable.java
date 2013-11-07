package ec.com.ebos.core.conta.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ec.com.ebos.core.root.model.Auditoria;
import ec.com.ebos.core.root.model.Entidad;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;

public interface CuentaContable extends Contabilidad{

	public Set<AsientoDetalle> getAsientoDetalleList();

	public Auditoria getAuditoria();

	public String getCodigo();

	public String getCodigoFormato();

	public Set<CuentaCentro> getCuentaCentroList();

	public Set<CuentaContableEmpresa> getCuentaContableEmpresaList();

	public Set<CuentaContable> getCuentaContableList();

	public String getDescripcion();

	public Set<Ejercicio> getEjercicioList();

	public Entidad.Estado getEstado();

	public Long getId();

	public CuentaContable.Naturaleza getNaturaleza();

	public int getNivel();

	public CuentaContable getPadre();

	public Set<SaldoCuentaCentro> getSaldoCuentaCentroList();

	public Set<SaldoCuentaContable> getSaldoCuentaContableList();

	public int getTerminante();

	public TipoCuenta getTipoCuenta();

	public CuentaContable.TipoProceso getTipoProceso();

	public boolean isPideCentroCosto();

	public void setAsientoDetalleList(Set<AsientoDetalle> asientoDetalleList);

	public void setAuditoria(Auditoria auditoria);

	public void setCodigo(String codigo);

	public void setCodigoFormato(String codigoFormato);

	public void setCuentaCentroList(Set<CuentaCentro> cuentaCentroList);

	public void setCuentaContableEmpresaList(Set<CuentaContableEmpresa> cuentaContableEmpresaList);

	public void setCuentaContableList(Set<CuentaContable> cuentaContableList);

	public void setDescripcion(String descripcion);

	public void setEjercicioList(Set<Ejercicio> ejercicioList);

	public void setEstado(Entidad.Estado estado);

	public void setId(Long id);

	public void setNaturaleza(CuentaContable.Naturaleza naturaleza);

	public void setNivel(int nivel);

	public void setPadre(CuentaContable padre);

	public void setPideCentroCosto(boolean pideCentroCosto);

	public void setSaldoCuentaCentroList(Set<SaldoCuentaCentro> saldoCuentaCentroList);

	public void setSaldoCuentaContableList(Set<SaldoCuentaContable> saldoCuentaContableList);

	public void setTerminante(int terminante);

	public void setTipoCuenta(TipoCuenta tipoCuenta);

	public void setTipoProceso(CuentaContable.TipoProceso tipoProceso);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();
	
    /**
     * <strong>Naturalezas de una {@link HibernateCuentaContable}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> D: Deudora<br> A: Acreedora<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum Naturaleza implements StringValuedEnum<Naturaleza> {

        DEUDORA("D"),
        ACREEDORA("A");

        public static class Type extends StringValuedEnumType<Naturaleza> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".core.conta.model.CuentaContable$Naturaleza$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private Naturaleza(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, Naturaleza> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(Naturaleza.values());
        /**
         * Lists para iteraciones
         */
        public static final List<Naturaleza> LIST = Arrays.asList(Naturaleza.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isDeudora() {
            return this.equals(DEUDORA);
        }

        public boolean isAcreedora() {
            return this.equals(ACREEDORA);
        }
    }
    
    /**
     * <strong>Tipos de procesos de una {@link HibernateCuentaContable}</strong> <br>
     * <table border="1">
     * <tr><th valign="top"> Tipos </th>
     * <tr><td valign="top"> X: Manual<br> A: Automatica<br> M: Mixta<br> </td></tr>
     * </table>
     *
     *  @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
     *
     */
    public enum TipoProceso implements StringValuedEnum<TipoProceso> {

        MIXTA("X"),
        AUTOMATICA("A"),
        MANUAL("M");
        

        public static class Type extends StringValuedEnumType<TipoProceso> {
        }
        
        public static final String TYPE = Constantes.DOMAIN_NAME+".core.conta.model.CuentaContable$TipoProceso$Type";
        
        @Getter
        private String value;
        private String labelKey;
        
        private TipoProceso(String value) {
            this.value = value;
            this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
        }
        
        public static final Map<String, TipoProceso> LABELED_MAP =
                EntityUtils.buildLabeledEnumMap(TipoProceso.values());
        /**
         * Lists para iteraciones
         */
        public static final List<TipoProceso> LIST = Arrays.asList(TipoProceso.values());

        @Override
        public String getLabel() {
            return labelKey;
        }

        @Override
        public String getDescription() {
            return getLabel();
        }

        public boolean isManual() {
            return this.equals(MANUAL);
        }

        public boolean isAutomatica() {
            return this.equals(AUTOMATICA);
        }
        
        public boolean isMixta() {
            return this.equals(MIXTA);
        }
    }

}