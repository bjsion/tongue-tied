package org.tonguetied.keywordmanagement;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.idEq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

/**
 * DAO facade to ORM. This facade allows access permanent storage of Language
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 * 
 */
public class LanguageRepositoryImpl extends HibernateDaoSupport implements
        LanguageRepository
{
    public List<Language> getLanguages()
    {
        Query query = getSession().getNamedQuery("get.languages");
        return query.list();
    }

    public Language getLanguage(final Long id)
    {
        Criteria criteria = getSession().createCriteria(Language.class);
        criteria.add(idEq(id));
        return (Language) criteria.uniqueResult();
    }

    public Language getLanguage(final LanguageCode code)
    {
        Criteria criteria = getSession().createCriteria(Language.class);
        criteria.add(eq("code", code));
        return (Language) criteria.uniqueResult();
    }

    public void saveOrUpdate(Language language) throws DataAccessException
    {
        getHibernateTemplate().saveOrUpdate(language);
        getHibernateTemplate().flush();
    }

    public void delete(Language language)
    {
        getSession().delete(language);
    }
}
