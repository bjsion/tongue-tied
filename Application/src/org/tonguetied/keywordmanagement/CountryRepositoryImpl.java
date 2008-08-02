package org.tonguetied.keywordmanagement;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.tonguetied.keywordmanagement.Country.CountryCode;

/**
 * DAO facade to ORM. This facade allows access permanent storage of Country
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 * 
 */
public class CountryRepositoryImpl extends HibernateDaoSupport implements
        CountryRepository
{
    public Country getCountry(final Long id)
    {
        Criteria criteria = getSession().createCriteria(Country.class);
        criteria.add(idEq(id));
        return (Country) criteria.uniqueResult();
    }

    public Country getCountry(final CountryCode code)
    {
        Criteria criteria = getSession().createCriteria(Country.class);
        criteria.add(eq("code", code));
        return (Country) criteria.uniqueResult();
    }

    public List<Country> getCountries()
    {
        Query query = getSession().getNamedQuery("get.countries");
        return query.list();
    }

    public void saveOrUpdate(Country country) throws DataAccessException
    {
        getHibernateTemplate().saveOrUpdate(country);
        getHibernateTemplate().flush();
    }

    public void delete(Country country)
    {
        getSession().delete(country);
    }
}
