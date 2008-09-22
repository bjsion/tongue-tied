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

import static org.hibernate.criterion.CriteriaSpecification.DISTINCT_ROOT_ENTITY;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.conjunction;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;

import java.util.List;
import java.util.SortedSet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO facade to ORM. This facade allows access permanent storage of Keyword
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 * 
 */
public class KeywordRepositoryImpl extends HibernateDaoSupport implements
        KeywordRepository
{
    public Keyword getKeyword(final Long id)
    {
        Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(idEq(id));
        return (Keyword) criteria.uniqueResult();
    }

    public Keyword getKeyword(final String keywordString)
    {
        Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(eq("keyword", keywordString));
        return (Keyword) criteria.uniqueResult();
    }

    public List<Keyword> getKeywords(final Integer firstResult,
            final Integer maxResults)
    {
        Query query = getSession().getNamedQuery("get.keywords");
        if (firstResult != null) query.setFirstResult(firstResult);
        if (maxResults != null) query.setMaxResults(maxResults);
        return query.list();
    }

    public List<Keyword> findKeywords(Keyword keyword,
            final boolean ignoreCase, final Integer firstResult,
            final Integer maxResults) throws IllegalArgumentException
    {
        if (keyword == null)
        {
            throw new IllegalArgumentException("keyword cannot be null");
        }
        MatchMode matchMode = MatchMode.ANYWHERE;
        Example criterionKeyword = Example.create(keyword);
        criterionKeyword.enableLike(matchMode);
        if (ignoreCase)
        {
            criterionKeyword.ignoreCase();
        }

        Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(criterionKeyword);
        criteria.addOrder(asc("keyword"));
        criteria.setResultTransformer(DISTINCT_ROOT_ENTITY);
        if (firstResult != null) criteria.setFirstResult(firstResult);
        if (maxResults != null) criteria.setMaxResults(maxResults);

        addTranslationCriteria(criteria, keyword.getTranslations(), ignoreCase,
                matchMode);

        return criteria.list();
    }

    /**
     * Adds the search criteria for a {@link Translation} to the set of search
     * parameters.
     * 
     * @param criteria the existing search criteria object
     * @param translations the translation to add to the criteria
     * @param ignoreCase flag indicating if case should be ignored during search
     * @param matchMode flag indicating the type of string pattern matching
     */
    private void addTranslationCriteria(Criteria criteria,
            SortedSet<Translation> translations, final boolean ignoreCase,
            final MatchMode matchMode)
    {
        if (translations != null && !translations.isEmpty())
        {
            Translation translation = translations.first();

            Example criterionTranslation = Example.create(translation);
            criterionTranslation.enableLike(matchMode);
            if (ignoreCase)
            {
                criterionTranslation.ignoreCase();
            }

            Conjunction conjunction = conjunction();
            conjunction.add(criterionTranslation);

            addBundleCriteria(conjunction, translation.getBundle());
            addCountryCriteria(conjunction, translation.getCountry());
            addLanguageCriteria(conjunction, translation.getLanguage());

            criteria.createCriteria("translations").add(conjunction);
        }
    }

    /**
     * Adds an equality comparison for a {@link Bundle}.
     * 
     * @param junction
     * @param bundle the bundle to compare
     */
    private void addBundleCriteria(Junction junction, Bundle bundle)
    {
        if (bundle != null)
        {
            junction.add(eq("bundle", bundle));
        }
    }

    /**
     * Adds an equality comparison for a {@link Country}.
     * 
     * @param junction
     * @param country the country to compare
     */
    private void addCountryCriteria(Junction junction, Country country)
    {
        if (country != null)
        {
            junction.add(eq("country", country));
        }
    }

    /**
     * Adds an equality comparison for a {@link Language}.
     * 
     * @param junction
     * @param language the language to compare
     */
    private void addLanguageCriteria(Junction junction, Language language)
    {
        if (language != null)
        {
            junction.add(eq("language", language));
        }
    }

    public void saveOrUpdate(Keyword keyword) throws DataAccessException
    {
        getHibernateTemplate().saveOrUpdate(keyword);
        getHibernateTemplate().flush();
    }

    public void delete(Keyword keyword)
    {
        getSession().delete(keyword);
    }
}
