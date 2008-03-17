package org.tonguetied.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.tonguetied.domain.AuditLogRecord;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.User;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class DaoRepositoryStub implements DaoRepository {
    
    List<Translation> translations = new ArrayList<Translation>();
    Map<CountryCode, Country> countries = new HashMap<CountryCode, Country>();
    Map<LanguageCode, Language> languages = new HashMap<LanguageCode, Language>();
    Map<String, Bundle> bundles = new HashMap<String, Bundle>();

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#delete(java.lang.Object)
     */
    public void delete(Object object) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#findKeywords(org.tonguetied.domain.Keyword, boolean, org.hibernate.criterion.MatchMode, java.lang.Integer, java.lang.Integer)
     */
    public List<Keyword> findKeywords(Keyword keyword, boolean ignoreCase,
            Integer firstResult, Integer maxResults)
            throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#findTranslations(org.tonguetied.domain.ExportParameters)
     */
    public List<Translation> findTranslations(ExportParameters parameters) {
        Comparator<Translation> comparator = new Comparator<Translation>() {

            /* (non-Javadoc)
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(Translation o1, Translation o2) {
                int result = 
                    o1.getKeyword().getKeyword().compareTo(o2.getKeyword().getKeyword());
                if (result == 0) {
                    result = 
                        o1.getLanguage().getCode().compareTo(o2.getLanguage().getCode());
                    if (result == 0) {
                        result = 
                            o1.getCountry().getCode().compareTo(o2.getCountry().getCode());
                        if (result == 0) {
                            result = 
                                o1.getBundle().getName().compareTo(o2.getBundle().getName());
                        }
                    }
                }
                return result;
            }
        };
        Collections.sort(translations, comparator);
        return translations;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getBundle(java.lang.Long)
     */
    public Bundle getBundle(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getBundle(java.lang.String)
     */
    public Bundle getBundle(String name) {
        return this.bundles.get(name);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getBundles()
     */
    public List<Bundle> getBundles() {
        return new ArrayList<Bundle>(this.bundles.values());
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getCountries()
     */
    public List<Country> getCountries() {
        return new ArrayList<Country>(this.countries.values());
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getCountry(java.lang.Long)
     */
    public Country getCountry(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getCountry(org.tonguetied.domain.Country.CountryCode)
     */
    public Country getCountry(CountryCode code) {
        return this.countries.get(code);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getKeyword(java.lang.Long)
     */
    public Keyword getKeyword(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getKeyword(java.lang.String)
     */
    public Keyword getKeyword(String keywordString) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getKeywords(java.lang.Integer, java.lang.Integer)
     */
    public List<Keyword> getKeywords(Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getLanguage(java.lang.Long)
     */
    public Language getLanguage(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getLanguage(org.tonguetied.domain.Language.LanguageCode)
     */
    public Language getLanguage(LanguageCode code) {
        return this.languages.get(code);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getLanguages()
     */
    public List<Language> getLanguages() {
        return new ArrayList<Language>(languages.values());
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getUser(java.lang.String)
     */
    public User getUser(final String username) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getUser(java.lang.Long)
     */
    public User getUser(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getUsers()
     */
    public List<User> getUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#getAuditLog()
     */
    public List<AuditLogRecord> getAuditLog() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.dao.DaoRepository#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(Object object) throws DataAccessException {
        if (object instanceof Translation) {
            this.translations.add((Translation) object);
        }
        else if (object instanceof Country) {
            Country country = (Country) object;
            this.countries.put(country.getCode(), country);
        }
        else if (object instanceof Language) {
            Language language = (Language) object;
            this.languages.put(language.getCode(), language);
        }
        else if (object instanceof Bundle) {
            Bundle bundle = (Bundle) object;
            this.bundles.put(bundle.getName(), bundle);
        }
        else {
            throw new IllegalArgumentException("class of type " + object.getClass() + " is not implemented");
        }
    }

}
