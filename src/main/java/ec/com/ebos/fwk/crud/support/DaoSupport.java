package ec.com.ebos.fwk.crud.support;

import ec.com.ebos.fwk.crud.Crud;
import ec.com.ebos.fwk.crud.CrudException;
import ec.com.ebos.fwk.crud.FinderException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Servicio para operaciones de CRUD y Finder. Provee operaciones basicas:
 * creacion, modificacion, eliminacion, como asi tambien consultas. <br>
 * Se inicializa mediante el tipo de entidad a administrar.
 * 
 * @author <a href="mailto:juanleonsolis@gmail.com">Ing. Juan Leon Solis</a>
 * @update <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * 
 */
public interface DaoSupport extends Crud {

    /**
     * Devuelve el tipo de entidad.
     * 
     * @return
     */
    public Class<?> getEntityType();

    /**
     * Devuelve el nombre de la entidad.
     * 
     * @return
     */
    public String getEntityName();

    /**
     * Persiste la entidad.
     * 
     * @param entity
     * @throws CrudException
     */
    public void create(Object entity) throws CrudException;

    /**
     * Actualiza entidad.
     * 
     * @param entity
     * @throws CrudException
     */
    public void update(Object entity) throws CrudException;
    
 
    
    /**
     * Elimina entidad cuyo id es pasada por parametro.
     * 
     * @param entity
     * @throws CrudException,
     *             FinderException
     */
    public void deleteById(Serializable id) throws CrudException, FinderException;

    /**
     * Busca todas las entidades cuyo tipo es igual a entityType.
     * 
     * @return Collection
     * @throws FinderException
     */
    public Collection<?> findAll() throws FinderException;

    /**
     * Busca una entidad cuyo id es el pasado por parametro.
     * 
     * @param id
     * 
     * @return entidad de tipo entityClass
     * @throws FinderException
     */
    public Object findById(Serializable id) throws FinderException;

    /**
     * Ejecuta una query cuyo nombre es queryName. Esta query debe estar
     * configurada en el mapping de hibernate.
     * 
     * @param queryName
     * @return Collection
     * @throws FinderException
     */
    public Collection<?> findByNamedQuery(String queryName) throws FinderException;

    /**
     * Ejecuta una query cuyo nombre es queryName y la cual posee un binding
     * "?". Esta query debe estar configurada en el mapping de hibernate.<br>
     * 
     * @param queryName
     * @param value,
     *            el valor del binding
     * @return Collection
     * @throws FinderException
     */
    public Collection<?> findByNamedQuery(String queryName, Object value)
            throws FinderException;

    /**
     * Ejecuta una query cuyo nombre es queryName y la cual posee bindings "?".
     * Esta query debe estar configurada en el mapping de hibernate.<br>
     * 
     * @param queryName
     * @param values,
     *            valores correspondientes a los bindings.
     * @return Collection
     * @throws FinderException
     */
    public Collection<?> findByNamedQuery(String queryName, Object[] values)
            throws FinderException;

}