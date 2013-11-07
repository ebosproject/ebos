package ec.com.ebos.hibernate.orm.crud.impl;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import ec.com.ebos.core.orm.crud.CrudException;
import ec.com.ebos.core.orm.crud.CrudService;

/**
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 * @since 2013-03-04
 */
public class CrudServiceImpl implements CrudService{

	private static final long serialVersionUID = 7352744870068538629L;
	
	private HibernateTemplate hibernateTemplate = null;
	
	public CrudServiceImpl(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
	

    public void create(Object entity) throws CrudException {
        try {
            hibernateTemplate.save(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public void update(Object entity) throws CrudException {
        try {
        	hibernateTemplate.update(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public void delete(Object entity) throws CrudException {
        try {
        	hibernateTemplate.delete(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }
    
    public void deleteAll(Collection<?> entities) throws CrudException {
        try {
        	hibernateTemplate.deleteAll(entities);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public void bulkUpdate(String query) throws CrudException {
        try {
        	hibernateTemplate.bulkUpdate(query);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public void bulkUpdate(String query, Object[] values) throws CrudException {
        try {
        	hibernateTemplate.bulkUpdate(query, values);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public void save(Object entity) throws CrudException {
        try {
        	hibernateTemplate.save(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }
    
    public void saveOrUpdate(Object entity) throws CrudException {
        try {
        	hibernateTemplate.saveOrUpdate(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }

    public Session getCurrentSession() {
    	return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    public Object merge(Object entity) throws CrudException {
        try {
            return hibernateTemplate.merge(entity);
        } catch (Exception e) {
            throw new CrudException(e);
        }
    }
    
	public void evict(Object pEntity){		
		getCurrentSession().evict(pEntity);
	}

	public void flush() {
		getCurrentSession().flush();
	}

	public void refresh(Object pEntity)  {
		getCurrentSession().refresh(pEntity);
	}
	
}
