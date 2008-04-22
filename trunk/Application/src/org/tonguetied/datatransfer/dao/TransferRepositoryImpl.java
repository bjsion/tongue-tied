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
        Query query = getSession().getNamedQuery(Translation.QUERY_FIND_TRANSLATIONS);
        query.setParameterList("countries", parameters.getCountries());
        query.setParameterList("bundles", parameters.getBundles());
        query.setParameterList("languages", parameters.getLanguages());
        return query.list();
    }
}
