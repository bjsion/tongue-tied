package org.tonguetied.keywordmanagement;

import java.util.List;

import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

/**
 * Concrete implementation of the {@link KeywordService}.
 * 
 * @author bsion
 */
public class KeywordServiceImpl implements KeywordService
{
    private KeywordRepository keywordRepository;
    private BundleRepository bundleRepository;

    public void setKeywordRepository(KeywordRepository keywordRepository)
    {
        this.keywordRepository = keywordRepository;
    }

    /**
     * Assign the bundleRepository.
     *
     * @param bundleRepository the bundleRepository to set
     */
    public void setBundleRepository(BundleRepository bundleRepository)
    {
        this.bundleRepository = bundleRepository;
    }

    public void delete(Object object)
    {
        keywordRepository.delete(object);
    }

    public Bundle getBundle(final Long id)
    {
        return bundleRepository.getBundle(id);
    }

    public Bundle getBundleByName(String name)
    {
        return bundleRepository.getBundleByName(name);
    }

    public Bundle getBundleByResourceName(String resourceName)
    {
        return bundleRepository.getBundleByResourceName(resourceName);
    }

    public Bundle getDefaultBundle()
    {
        return bundleRepository.getDefaultBundle();
    }

    public List<Bundle> getBundles()
    {
        return bundleRepository.getBundles();
    }

    public Country getCountry(final Long id)
    {
        return keywordRepository.getCountry(id);
    }

    public Country getCountry(final CountryCode code)
    {
        return keywordRepository.getCountry(code);
    }

    public List<Country> getCountries()
    {
        return keywordRepository.getCountries();
    }

    public Keyword getKeyword(final Long id)
    {
        return keywordRepository.getKeyword(id);
    }

    public Keyword getKeyword(final String keywordString)
    {
        return keywordRepository.getKeyword(keywordString);
    }

    public List<Keyword> getKeywords()
    {
        return getKeywords(0, null);
    }

    public List<Keyword> getKeywords(Integer firstResult, Integer maxResults)
    {
        return keywordRepository.getKeywords(firstResult, maxResults);
    }

    public List<Keyword> findKeywords(Keyword keyword,
            final boolean ignoreCase, final Integer firstResult,
            final Integer maxResults)
    {
        return keywordRepository.findKeywords(keyword, ignoreCase, firstResult,
                maxResults);
    }

    public Language getLanguage(final Long id)
    {
        return keywordRepository.getLanguage(id);
    }

    public Language getLanguage(final LanguageCode code)
    {
        return keywordRepository.getLanguage(code);
    }

    public List<Language> getLanguages()
    {
        return keywordRepository.getLanguages();
    }

    public void deleteKeyword(final Long id)
    {
        Keyword keyword = getKeyword(id);
        delete(keyword);
    }

    public void saveOrUpdate(Object object)
    {
        keywordRepository.saveOrUpdate(object);
    }

    public void saveOrUpdate(Bundle bundle)
    {
        bundleRepository.saveOrUpdate(bundle);
    }
}
