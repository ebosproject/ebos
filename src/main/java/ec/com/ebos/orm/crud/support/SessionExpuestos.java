package ec.com.ebos.orm.crud.support;

public interface SessionExpuestos {
	public void refresh(Object pEntity);
	public void evict(Object pEntity);
	public void flush();
}
