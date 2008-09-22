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
    private CountryRepository countryRepository;
    private LanguageRepository languageRepository;

    public void setKeywordRepository(KeywordRepository keywordRepository)
    {
        this.keywordRepository = keywordRepository;
    }

    /**
     * Assign the countryRepository.
     *
     * @param countryRepository the countryRepository to set
     */
    public void setCountryRepository(CountryRepository countryRepository)
    {
        this.countryRepository = countryRepository;
    }

    /**
     * Assign the languageRepository.
     *
     * @param languageRepository the languageRepository to set
     */
    public void setLanguageRepository(LanguageRepository languageRepository)
    {
        this.languageRepository = languageRepository;
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

    public void delete(Keyword keyword)
    {
        keywordRepository.delete(keyword);
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
        return countryRepository.getCountry(id);
    }

    public Country getCountry(final CountryCode code)
    {
        return countryRepository.getCountry(code);
    }

    public List<Country> getCountries()
    {
        return countryRepository.getCountries();
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
        return languageRepository.getLanguage(id);
    }

    public Language getLanguage(final LanguageCode code)
    {
        return languageRepository.getLanguage(code);
    }

    public List<Language> getLanguages()
    {
        return languageRepository.getLanguages();
    }

    public void deleteKeyword(final Long id)
    {
        Keyword keyword = getKeyword(id);
        delete(keyword);
    }

    public void saveOrUpdate(Country country)
    {
        countryRepository.saveOrUpdate(country);
    }

    public void saveOrUpdate(Language language)
    {
        languageRepository.saveOrUpdate(language);
    }

    public void saveOrUpdate(Keyword keyword)
    {
        keywordRepository.saveOrUpdate(keyword);
    }


    public void saveOrUpdate(Bundle bundle)
    {
        bundleRepository.saveOrUpdate(bundle);
    }
}
