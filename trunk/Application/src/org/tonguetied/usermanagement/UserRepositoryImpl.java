package org.tonguetied.usermanagement;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * DAO facade to ORM. This facade allows access to permanent storage of User
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 *
 */
public class UserRepositoryImpl extends HibernateDaoSupport implements UserRepository
{
    public User getUser(final String username) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(eq("username", username));
        return (User) criteria.uniqueResult();
    }

    public User getUser(final Long id) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(idEq(id));
        return (User) criteria.uniqueResult();
    }
    
    public List<User> getUsers() 
    {           
        Query query = getSession().getNamedQuery("get.users");
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
