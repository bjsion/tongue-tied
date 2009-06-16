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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.utils.pagination.Order;
import org.tonguetied.utils.pagination.PaginatedList;


/**
 * @author bsion
 *
 */
public class KeywordServiceStub implements KeywordService
{

    private Map<Long, Country> countries = new HashMap<Long, Country>();
    private Map<Long, Language> languages = new HashMap<Long, Language>();
    private Map<Long, Bundle> bundles = new HashMap<Long, Bundle>();
    private Map<Long, Keyword> keywords = new HashMap<Long, Keyword>();
    
    public void delete(Object object)
    {
        if (object instanceof Language)
        {
            Language langauge = (Language) object;
            languages.remove(langauge.getId());
        }
        else if (object instanceof Bundle)
        {
            Bundle bundle = (Bundle) object;
            bundles.remove(bundle.getId());
        }
        else if (object instanceof Country)
        {
            Country country = (Country) object;
            countries.remove(country.getId());
        }
        else {
            throw new IllegalArgumentException("Object " + object.getClass() + " not supported");
        }
    }

    public void delete(Keyword keyword)
    {
        deleteKeyword(keyword.getId());
    }

    public void deleteKeyword(Long id)
    {
        this.keywords.remove(id);
    }

    public PaginatedList<Keyword> findKeywords(final Keyword keyword, boolean ignoreCase,
            Order order, Integer firstResult, Integer maxResults)
    {
        final Predicate keywordFilter = new KeywordFilter(keyword);
        List<Keyword> results = 
            (List<Keyword>) CollectionUtils.select(keywords.values(), keywordFilter);
        return new PaginatedList<Keyword>(results, results.size());
    }

    public Bundle getBundle(final Long id)
    {
        return bundles.get(id);
    }

    public List<Bundle> getBundles()
    {
        return (List<Bundle>)bundles.values();
    }

    public Bundle getBundleByName(final String name)
    {
        final Predicate bundleCodeFilter = new BundleFilter(name); 
        Bundle bundle= 
            (Bundle) CollectionUtils.find(bundles.values(), bundleCodeFilter);
        return bundle;
    }

    public Bundle getBundleByResourceName(final String resourceName)
    {
        final Predicate bundleCodeFilter = new ResourceBundleFilter(resourceName); 
        Bundle bundle= 
            (Bundle) CollectionUtils.find(bundles.values(), bundleCodeFilter);
        return bundle;
    }

    public Bundle getDefaultBundle()
    {
        final Predicate bundleCodeFilter = new DefaultBundleFilter(); 
        Bundle bundle= 
            (Bundle) CollectionUtils.find(bundles.values(), bundleCodeFilter);
        return bundle;
    }

    public List<Bundle> findBundles(final String name, final String resourceName)
    {
        final Predicate bundleFilter = new BundleNamesFilter(name, resourceName);
        List<Bundle> results = 
            (List<Bundle>) CollectionUtils.select(bundles.values(), bundleFilter);
        return results;
    }

    public List<Country> getCountries()
    {
        return (List<Country>)countries.values();
    }

    public Country getCountry(final Long id)
    {
        return countries.get(id);
    }

    public Country getCountry(final CountryCode code)
    {
        final Predicate countryCodeFilter = new CountryCodeFilter(code); 
        Country country = 
            (Country) CollectionUtils.find(countries.values(), countryCodeFilter);
        return country;
    }

    public Keyword getKeyword(final Long id)
    {
        return keywords.get(id);
    }

    public Keyword getKeyword(final String keywordString) {
        final Predicate keywordFilter = new KeywordStringFilter(keywordString);
        Keyword result = 
            (Keyword) CollectionUtils.find(keywords.values(), keywordFilter);
        return result;
    }

    public List<Keyword> getKeywords()
    {
        return (List<Keyword>)keywords.values();
    }

    public PaginatedList<Keyword> getKeywords(Integer firstResult, Integer maxResults, Order order)
    {
        return new PaginatedList<Keyword>(keywords.values(), keywords.size());
    }

    public Language getLanguage(final Long id)
    {
        return languages.get(id);
    }

    public Language getLanguage(final LanguageCode code) {
        final Predicate languageCodeFilter = new LanguageCodeFilter(code); 
        Language language = 
            (Language) CollectionUtils.find(languages.values(), languageCodeFilter);
        return language;
    }

    public List<Language> getLanguages() {
        return (List<Language>)this.languages.values();
    }

    public void saveOrUpdate(Country country)
    {
        if (country.getId() == null)
            country.setId(Long.valueOf(countries.size()));
        this.countries.put(country.getId(), country);
    }

    public void saveOrUpdate(Language language)
    {
        if (language.getId() == null)
            language.setId(Long.valueOf(languages.size()));
        this.languages.put(language.getId(), language);
    }
    
    public void saveOrUpdate(Keyword keyword)
    {
        if (keyword.getId() == null)
            keyword.setId(Long.valueOf(keywords.size()));
        this.keywords.put(keyword.getId(), keyword);
    }

    public void saveOrUpdate(Bundle bundle)
    {
        if (bundle.getId() == null)
            bundle.setId(Long.valueOf(bundles.size()));
        this.bundles.put(bundle.getId(), bundle);
    }

    private static final class LanguageCodeFilter implements Predicate
    {
        private final LanguageCode code;

        private LanguageCodeFilter(LanguageCode code)
        {
            this.code = code;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (code != null)
            {
                result = code.equals(((Language) object).getCode());
            }
            return result;
        }
    }

    private static final class BundleFilter implements Predicate
    {
        private final String name;

        private BundleFilter(String name)
        {
            this.name = name;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (name != null)
            {
                result = name.equals(((Bundle) object).getName());
            }
            return result;
        }
    }

    private static final class ResourceBundleFilter implements Predicate
    {
        private final String resourceName;

        private ResourceBundleFilter(String resourceName)
        {
            this.resourceName = resourceName;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (resourceName != null)
            {
                result = resourceName.equals(((Bundle) object).getResourceName());
            }
            return result;
        }
    }

    private static final class DefaultBundleFilter implements Predicate
    {
        public boolean evaluate(Object object)
        {
            return ((Bundle) object).isDefault();
        }
    }

    private static final class BundleNamesFilter implements Predicate
    {
        private final String name;
        private final String resourceName;

        private BundleNamesFilter(String name, String resourceName)
        {
            this.name = name;
            this.resourceName = resourceName;
        }

        public boolean evaluate(Object object)
        {
            Bundle other = (Bundle) object;
            return (other.getName().equals(name)) ||
                (other.getResourceName().equals(resourceName));
        }
    }

    private static final class CountryCodeFilter implements Predicate
    {
        private final CountryCode code;

        private CountryCodeFilter(CountryCode code)
        {
            this.code = code;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (code != null)
            {
                result = code.equals(((Country) object).getCode());
            }
            return result;
        }
    }

    private static final class KeywordStringFilter implements Predicate
    {
        private final String keywordString;

        private KeywordStringFilter(String keywordString)
        {
            this.keywordString = keywordString;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (keywordString != null)
            {
                result = keywordString.equals(((Keyword) object).getKeyword());
            }
            return result;
        }
    }

    private static final class KeywordFilter implements Predicate
    {
        private final Keyword keyword;

        private KeywordFilter(Keyword keyword)
        {
            this.keyword = keyword;
        }

        public boolean evaluate(Object object)
        {
            boolean result = false;
            if (keyword != null)
            {
                result = keyword.equals(object);
            }
            return result;
        }
    }
}
