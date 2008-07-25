package org.tonguetied.keywordmanagement;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;
import static org.tonguetied.keywordmanagement.Bundle.QUERY_GET_BUNDLES;
import static org.tonguetied.keywordmanagement.Bundle.QUERY_GET_DEFAULT_BUNDLE;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO facade to ORM. This facade allows access permanent storage via Hibernate
 * orm model.
 * 
 * @author bsion
 * 
 */
public class BundleRepositoryImpl extends HibernateDaoSupport implements
        BundleRepository
{
    public Bundle getBundle(final Long id)
    {
        Criteria criteria = getSession().createCriteria(Bundle.class);
        criteria.add(idEq(id));
        return (Bundle) criteria.uniqueResult();
    }

    public Bundle getBundleByName(String name)
    {
        Criteria criteria = getSession().createCriteria(Bundle.class);
        criteria.add(eq("name", name));
        return (Bundle) criteria.uniqueResult();
    }

    public Bundle getBundleByResourceName(String resourceName)
    {
        Criteria criteria = getSession().createCriteria(Bundle.class);
        criteria.add(eq("resourceName", resourceName));
        return (Bundle) criteria.uniqueResult();
    }

    public Bundle getDefaultBundle()
    {
        Query query = getSession().getNamedQuery(QUERY_GET_DEFAULT_BUNDLE);
        return (Bundle) query.uniqueResult();
    }

    public List<Bundle> getBundles()
    {
        Query query = getSession().getNamedQuery(QUERY_GET_BUNDLES);
        return query.list();
    }

    public void saveOrUpdate(Bundle bundle) throws DataAccessException
    {
        if (bundle.isDefault())
        {
            Bundle defaultBundle = getDefaultBundle();
            if (defaultBundle != null
                    && !defaultBundle.getName().equals(bundle.getName()))
            {
                defaultBundle.setDefault(false);
                getHibernateTemplate().save(defaultBundle);
            }
        }
        getHibernateTemplate().saveOrUpdate(bundle);
        getHibernateTemplate().flush();
    }

    public void delete(Bundle bundle)
    {
        getSession().delete(bundle);
    }
}
