package org.tonguetied.usermanagement;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;
import static org.tonguetied.usermanagement.User.FIELD_EMAIL;
import static org.tonguetied.usermanagement.User.FIELD_FIRSTNAME;
import static org.tonguetied.usermanagement.User.FIELD_LASTNAME;
import static org.tonguetied.usermanagement.User.FIELD_USERNAME;
import static org.tonguetied.usermanagement.User.QUERY_GET_USERS;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO facade to ORM. This facade allows access to permanent storage of User
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 * 
 */
public class UserRepositoryImpl extends HibernateDaoSupport implements
        UserRepository
{
    public User getUser(final String username)
    {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(eq(FIELD_USERNAME, username));
        return (User) criteria.uniqueResult();
    }

    public User getUser(final Long id)
    {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(idEq(id));
        return (User) criteria.uniqueResult();
    }

    public List<User> getUsers()
    {
        Query query = getSession().getNamedQuery(QUERY_GET_USERS).setCacheable(true);
        return query.list();
    }

    public List<User> findUsers(final User user)
    {
        Criteria criteria = 
            getSession().createCriteria(User.class).setCacheable(true);
        addCriteria(criteria, FIELD_USERNAME, user.getUsername());
        addCriteria(criteria, FIELD_FIRSTNAME, user.getFirstName());
        addCriteria(criteria, FIELD_LASTNAME, user.getLastName());
        addCriteria(criteria, FIELD_EMAIL, user.getEmail());
        
        return criteria.list();
    }

    /**
     * Add a restriction to the search criteria.
     * 
     * @param criteria the criteria object to amend
     * @param fieldName the name of the field on which to apply the restriction
     * @param value the value of the field restriction
     */
    private void addCriteria(Criteria criteria, final String fieldName, final String value)
    {
        if (StringUtils.isNotEmpty(value))
            criteria.add(Restrictions.ilike(fieldName, value, MatchMode.ANYWHERE));
    }

    public void saveOrUpdate(User user) throws DataAccessException
    {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
    }

    public void delete(User user)
    {
        getSession().delete(user);
    }
}
