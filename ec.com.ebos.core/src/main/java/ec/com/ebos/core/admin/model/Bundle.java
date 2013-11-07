package ec.com.ebos.core.admin.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import ec.com.ebos.core.util.Constantes;
import ec.com.ebos.core.util.EntityUtils;
import ec.com.ebos.core.util.type.StringValuedEnum;
import ec.com.ebos.core.util.type.StringValuedEnumReflect;
import ec.com.ebos.core.util.type.StringValuedEnumType;



public interface Bundle extends Administracion {

	public String getCodigo();

	public Long getId();

	public Localidad getLocalidad();

	public String getValor();

	public void setCodigo(String codigo);

	public void setId(Long id);

	public void setLocalidad(Localidad localidad);

	public void setValor(String valor);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public String getKeyCache();
	
	
	/**
     * <strong>Localidades(es[_EC]) que soporta el sistema</strong> <br> <table border="1">
     * <tr><th valign="top"> HibernateLocalidad </th>
     * <tr><td> es_EC: Espaniol Ecuador </td>
     * <tr><td> en_US: Ingles USA </td>
     * </table>
     *
     * @author Eduardo Plua Alay
     *
     */
	public enum Localidad implements StringValuedEnum<Localidad> {
	    es_EC("es_EC"),
	    en_US("en_US");

	    public static class Type extends StringValuedEnumType<Localidad> {
	    }
	    public static final String TYPE = Constantes.DOMAIN_NAME+".admin.model.Bundle$Localidad$Type";
	    
	    @Getter
	    private String value;
	    private String labelKey;

	    private Localidad(String value) {
	        this.value = value;
	        this.labelKey = StringValuedEnumReflect.getLabelKeyFromEnum(this);
	    }
	    public static final Map<String, Localidad> LABELED_MAP =
	            EntityUtils.buildLabeledEnumMap(Localidad.values());
	    /**
	     * Lists for iterations
	     */
	    public static final List<Localidad> LIST = Arrays.asList(Localidad.values());

	    @Override
	    public String getLabel() {        	
	    	return labelKey;
	    }

	    @Override
	    public String getDescription() {
	        return getLabel();
	    }

	}

}