package ec.com.platform.fwk.crud;

import java.util.Collection;

import org.hibernate.Session;

import ec.com.platform.fwk.crud.support.SessionExpuestos;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-04
 */
public interface CrudService extends SessionExpuestos{
	
	/**
     * Persiste la entidad.
     * 
     * @param entity
     * @throws CrudException
     */
    public void create(Object entity) throws CrudException;

    /**
     * Persiste o actualiza una entidad. Si esta ya existe en la BD, entonces
     * actualiza su contenido.
     * 
     * @param entity
     * @throws CrudException
     */
    public void saveOrUpdate(Object entity) throws CrudException;

    /**
     * Actualiza entidad.
     * 
     * @param entity
     * @throws CrudException
     */
    public void update(Object entity) throws CrudException;
    
    /**
     * Merge una entidad.
     * 
     * @param entity
     * @return
     * @throws CrudException
     */
    public Object merge(Object entity) throws CrudException;

    /**
     * Elimina entidad pasada por parametro.
     * 
     * @param entity
     * @throws CrudException
     */
    public void delete(Object entity) throws CrudException;
    
    /**
     * Elimina una coleccion de entidades pasada por parametro.
     * 
     * @param entity
     * @throws CrudException
     */
    public void deleteAll(Collection<?> entities) throws CrudException;

    /**
     * @param query
     * @throws CrudException
     */
    public void bulkUpdate(String query) throws CrudException;

    /**
     * @param query
     * @param values
     * @throws CrudException
     */
    public void bulkUpdate(String query, Object[] values) throws CrudException;

    /**
     * @return Session
     */
    public Session getCurrentSession();
	
}
