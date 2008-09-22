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
package org.tonguetied.datatransfer.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * DAO facade to ORM. This facade allows access to permanent storage of keyword
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 *
 */
public class TransferRepositoryImpl extends HibernateDaoSupport implements TransferRepository
{
    public Country getCountry(final CountryCode code) {
        Criteria criteria = getSession().createCriteria(Country.class);
        criteria.add(eq("code", code));
        return (Country) criteria.uniqueResult();
    }
    
    public List<Translation> findTranslations(ExportParameters parameters) {
        if (CollectionUtils.isEmpty(parameters.getBundles())) {
            throw new IllegalArgumentException("bundles cannot be null or empty");
        }
        if (CollectionUtils.isEmpty(parameters.getCountries())) {
            throw new IllegalArgumentException("countries cannot be null or empty");
        }
        if (CollectionUtils.isEmpty(parameters.getLanguages())) {
            throw new IllegalArgumentException("languages cannot be null or empty");
        }
        if (parameters.getTranslationState() == null)
            throw new IllegalArgumentException("translation state cannot be null");
        
        Query query = getSession().getNamedQuery(Translation.QUERY_FIND_TRANSLATIONS);
        query.setParameterList("countries", parameters.getCountries());
        query.setParameterList("bundles", parameters.getBundles());
        query.setParameterList("languages", parameters.getLanguages());
        query.setParameter("state", parameters.getTranslationState());
        return query.list();
    }
}
