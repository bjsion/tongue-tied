package org.tonguetied.audit;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * DAO facade to ORM. This facade allows access to permanent storage of Audit
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 *
 */
public class AuditRepositoryImpl extends HibernateDaoSupport implements AuditRepository
{
    public List<AuditLogRecord> getAuditLog() {
        Query query = getSession().getNamedQuery("get.audit.log");
        return query.list();
    }

    public void saveOrUpdate(Object object) throws DataAccessException 
    {
        getHibernateTemplate().saveOrUpdate(object);
        getHibernateTemplate().flush();
    }
    
    public void delete(Object object) {
        getSession().delete(object);
    }
}
