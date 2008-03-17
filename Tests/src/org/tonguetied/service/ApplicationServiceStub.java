package org.tonguetied.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class ApplicationServiceStub implements ApplicationService {

    private Map<Long, Country> countries = new HashMap<Long, Country>();
    private Map<Long, Language> languages = new HashMap<Long, Language>();
    private Map<Long, Bundle> bundles = new HashMap<Long, Bundle>();
    private Map<Long, Keyword> keywords = new HashMap<Long, Keyword>();
    
    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#delete(java.lang.Object)
     */
    public void delete(Object object) {
        if (object instanceof Language) {
            Language langauge = (Language) object;
            languages.remove(langauge.getId());
        }
        else if (object instanceof Bundle) {
            Bundle bundle = (Bundle) object;
            bundles.remove(bundle.getId());
        }
        else if (object instanceof Country) {
            Country country = (Country) object;
            countries.remove(country.getId());
        }
        else if (object instanceof Keyword) {
            Keyword keyword = (Keyword) object;
            deleteKeyword(keyword.getId());
        }
        else {
            throw new IllegalArgumentException("Object " + object.getClass() + " not supported");
        }
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#deleteKeyword(java.lang.Long)
     */
    public void deleteKeyword(Long id) {
        this.keywords.remove(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#findKeywords(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<Keyword> findKeywords(String searchString, Integer firstResult,
            Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#findKeywords(org.tonguetied.domain.Keyword, boolean, org.hibernate.criterion.MatchMode, java.lang.Integer, java.lang.Integer)
     */
    public List<Keyword> findKeywords(final Keyword keyword, boolean ignoreCase,
            Integer firstResult, Integer maxResults) {
        Predicate keywordFilter = new Predicate() {

            /* (non-Javadoc)
             * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
             */
            public boolean evaluate(Object object) {
                boolean result = false;
                if (keyword != null) {
                    result = keyword.equals(object);
                }
                return result;
            }
        };
        List<Keyword> results = 
            (List) CollectionUtils.select(keywords.values(), keywordFilter);
        return results;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getBundle(java.lang.Long)
     */
    public Bundle getBundle(final Long id) {
        return bundles.get(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getBundles()
     */
    public List<Bundle> getBundles() {
        return (List<Bundle>)bundles.values();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getBundle(java.lang.String)
     */
    public Bundle getBundle(final String name) {
        Predicate bundleCodeFilter = new Predicate() {

            /* (non-Javadoc)
             * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
             */
            public boolean evaluate(Object object) {
                boolean result = false;
                if (name != null) {
                    result = name.equals(((Bundle) object).getName());
                }
                return result;
            }
        }; 
        Bundle bundle= 
            (Bundle) CollectionUtils.find(bundles.values(), bundleCodeFilter);
        return bundle;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getCountries()
     */
    public List<Country> getCountries() {
        return (List<Country>)countries.values();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getCountry(java.lang.Long)
     */
    public Country getCountry(final Long id) {
        return countries.get(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getCountry(org.tonguetied.domain.Country.CountryCode)
     */
    public Country getCountry(final CountryCode code) {
        Predicate countryCodeFilter = new Predicate() {

            /* (non-Javadoc)
             * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
             */
            public boolean evaluate(Object object) {
                boolean result = false;
                if (code != null) {
                    result = code.equals(((Country) object).getCode());
                }
                return result;
            }
        }; 
        Country country = 
            (Country) CollectionUtils.find(countries.values(), countryCodeFilter);
        return country;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getKeyword(java.lang.Long)
     */
    public Keyword getKeyword(final Long id) {
        return keywords.get(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getKeyword(java.lang.String)
     */
    public Keyword getKeyword(final String keywordString) {
        Predicate keywordFilter = new Predicate() {

            /* (non-Javadoc)
             * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
             */
            public boolean evaluate(Object object) {
                boolean result = false;
                if (keywordString != null) {
                    result = keywordString.equals(((Keyword) object).getKeyword());
                }
                return result;
            }
        };
        Keyword result = 
            (Keyword) CollectionUtils.find(keywords.values(), keywordFilter);
        return result;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getKeywords()
     */
    public List<Keyword> getKeywords() {
        return (List<Keyword>)keywords.values();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getKeywords(java.lang.Integer, java.lang.Integer)
     */
    public List<Keyword> getKeywords(Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getLanguage(java.lang.Long)
     */
    public Language getLanguage(final Long id) {
        return languages.get(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getLanguage(org.tonguetied.domain.Language.LanguageCode)
     */
    public Language getLanguage(final LanguageCode code) {
        Predicate languageCodeFilter = new Predicate() {

            /* (non-Javadoc)
             * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
             */
            public boolean evaluate(Object object) {
                boolean result = false;
                if (code != null) {
                    result = code.equals(((Language) object).getCode());
                }
                return result;
            }
        }; 
        Language language = 
            (Language) CollectionUtils.find(languages.values(), languageCodeFilter);
        return language;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#getLanguages()
     */
    public List<Language> getLanguages() {
        return (List<Language>)this.languages.values();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ApplicationService#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(Object object) {
        if (object instanceof Country) {
            Country country = (Country) object;
            if (country.getId() == null)
                country.setId(Long.valueOf(countries.size()));
            this.countries.put(country.getId(), country);
        }
        else if (object instanceof Language) {
            Language language = (Language) object;
            if (language.getId() == null)
                language.setId(Long.valueOf(languages.size()));
            this.languages.put(language.getId(), language);
        }
        else if (object instanceof Bundle) {
            Bundle bundle = (Bundle) object;
            if (bundle.getId() == null)
                bundle.setId(Long.valueOf(bundles.size()));
            this.bundles.put(bundle.getId(), bundle);
        }
        else if (object instanceof Keyword) {
            Keyword keyword = (Keyword) object;
            if (keyword.getId() == null)
                keyword.setId(Long.valueOf(keywords.size()));
            this.keywords.put(keyword.getId(), keyword);
        }
        else {
            throw new IllegalArgumentException("Object " + object.getClass() + " not supported");
        }
    }

}
