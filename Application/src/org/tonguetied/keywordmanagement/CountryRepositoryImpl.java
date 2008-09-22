/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
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
