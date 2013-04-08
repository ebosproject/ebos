package ec.com.ebos.fwk.crud.support;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import ec.com.ebos.fwk.crud.CrudException;
import ec.com.ebos.fwk.crud.CrudService;
import ec.com.ebos.fwk.crud.FinderException;
import ec.com.ebos.fwk.crud.FinderService;

/**
 * Servicio para operaciones de CRUD y Finder. Provee operaciones basicas:
 * creacion, modificacion, eliminacion, como asi tambien consultas. <br>
 * Se inicializa mediante el tipo de entidad a administrar.
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-05
 */
public class BaseDaoSupport implements DaoSupport {

	@Setter
    protected CrudService crud;

	@Setter
    protected FinderService finder;

    @Getter @Setter
    protected Class<?> entityType;

    @Getter @Setter
    protected String entityName;


    protected BaseDaoSupport(Class<?> entityType, String entityName) {
        this.entityType = entityType;
        this.entityName = entityName;
    }

    public BaseDaoSupport(CrudService crudService, FinderService finderService) {
		this.crud = crudService;
		this.finder = finderService;
	}

	public void deleteById(Serializable id) throws CrudException, FinderException {
        crud.delete(finder.findById(id, getEntityType()));
    }

    public void create(Object entity) throws CrudException {
        crud.create(entity);
    }

    public void update(Object entity) throws CrudException {
        crud.update(entity);
    }

    public void delete(Object entity) throws CrudException {
        crud.delete(entity);
    }
    
    public void deleteAll(Collection<?> entities) throws CrudException {
        crud.deleteAll(entities);
    }

    public Collection<?> findAll() throws FinderException {
        return finder.findAll(getEntityType());
    }

    public Object findById(Serializable id) throws FinderException {
        return finder.findById(id, getEntityType());
    }

    public Collection<?> findByNamedQuery(String queryName) throws FinderException {
        return finder.findByNamedQuery(queryName);
    }

    public Collection<?> findByNamedQuery(String queryName, Object value)
            throws FinderException {
        return finder.findByNamedQuery(queryName, value);
    }

    public Collection<?> findByNamedQuery(String queryName, Object[] values)
            throws FinderException {
        return finder.findByNamedQuery(queryName, values);
    }

    public Collection<?> findByCriteria(DetachedCriteria criteria)
            throws FinderException {
        return finder.findByCriteria(criteria);
    }

    public void bulkUpdate(String query) throws CrudException {
        crud.bulkUpdate(query);
    }

    public void bulkUpdate(String query, Object[] values) throws CrudException {
        crud.bulkUpdate(query, values);
    }

    public void saveOrUpdate(Object entity) throws CrudException {
        crud.saveOrUpdate(entity);
    }

    public Object merge(Object entity) throws CrudException {
        return crud.merge(entity);
    }

	public void evict(Object pEntity) {
		crud.evict(pEntity);
	}

	public void flush() {
		crud.flush();
	}

	public void refresh(Object pEntity) {
		crud.refresh(pEntity);
	}

	@Override
	public Session getActiveSession() {
		return getActiveSession();
	}
}
