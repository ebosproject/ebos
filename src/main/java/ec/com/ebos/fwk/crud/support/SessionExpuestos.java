package ec.com.ebos.fwk.crud.support;

public interface SessionExpuestos {
	public void refresh(Object pEntity);
	public void evict(Object pEntity);
	public void flush();
}
