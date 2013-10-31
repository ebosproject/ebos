package ec.com.ebos.master.model;

import java.util.Set;

import ec.com.ebos.conta.model.hibernate.HibernateAsiento;

public interface TipoDocumento {

	public Set<HibernateAsiento> getAsientoList();

	public Long getId();

	public TipoDocumento getTipoDocumentoReversa();

	public boolean isContabiliza();

	public boolean isContable();

	public boolean isImpresionAutomatica();

	public boolean isPidePreimpreso();

	public void setAsientoList(Set<HibernateAsiento> asientoList);

	public void setContabiliza(boolean contabiliza);

	public void setContable(boolean contable);

	public void setId(Long id);

	public void setImpresionAutomatica(boolean impresionAutomatica);

	public void setPidePreimpreso(boolean pidePreimpreso);

	public void setTipoDocumentoReversa(
			TipoDocumento tipoDocumentoReversa);

	public java.lang.String toString();

	public boolean canEqual(java.lang.Object other);

	public boolean equals(java.lang.Object o);

	public int hashCode();

}