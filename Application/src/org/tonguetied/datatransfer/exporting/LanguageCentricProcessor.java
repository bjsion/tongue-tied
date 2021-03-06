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
package org.tonguetied.datatransfer.exporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordByLanguage;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * Performs data manipulation for language centric data.
 * 
 * @author bsion
 *
 */
public class LanguageCentricProcessor implements ExportDataPostProcessor
{
    private Country defaultCountry;
    private ExportParameters parameters;

    protected static final String KEY_LANGUAGES = "languages";
    private static final Logger logger = 
        Logger.getLogger(LanguageCentricProcessor.class);

    /**
     * Create a new instance of the LanguageCentricProcessor.
     * 
     * @param parameters the parameters used to filter and format the data. 
     * Cannot be <code>null</code>
     * @param defaultCountry the default country
     */
    public LanguageCentricProcessor(ExportParameters parameters, 
            Country defaultCountry)
    {
        if (parameters == null)
            throw new IllegalArgumentException("parameters cannot be null");
        
        this.parameters = parameters;
        this.defaultCountry = defaultCountry;
    }

    public List<KeywordByLanguage> transformData(List<Translation> translations)
    {
        List<KeywordByLanguage> results = new ArrayList<KeywordByLanguage>();
        if (translations != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("transforming "+translations.size()+" objects");
            }
            KeywordByLanguage item;
            for (Translation translation : translations)
            {
                LanguageCode languageCode = null;
                if (CountryCode.TW == translation.getCountry().getCode())
                {
                    translation.setCountry(defaultCountry);
                    languageCode = LanguageCode.zht;
                }
                item = findItem(results, translation);
                if (item == null)
                {
                    item = new KeywordByLanguage(
                            translation.getKeyword().getKeyword(),
                            translation.getKeyword().getContext(),
                            translation.getBundle(),
                            translation.getCountry());
                    results.add(item);
                }
                item.addTranslation(languageCode != null ? languageCode: translation.getLanguage().getCode(), 
                        translation.getValue());
            }
        }
        
        return results;
    }
    
    public void addItems(Map<String, Object> root)
    {
        Language traditionalChinese = new Language();
        traditionalChinese.setCode(LanguageCode.zht);
        traditionalChinese.setName("Traditional Chinese");
        List<Language> languages = parameters.getLanguages();
        languages.add(traditionalChinese);
        Collections.sort(languages);
        root.put(KEY_LANGUAGES, languages);
    }

    /**
     * Find an existing {@link KeywordByLanguage} item based on criteria 
     * provided in the <code>translation<code> parameter. If no match is 
     * found <code>null</code> is returned. 
     * 
     * @param results the list of {@link KeywordByLanguage} items to search
     * @param translation the {@link Translation} object used to construct the
     * search criteria
     * @return the first item matching the criteria, or <code>null</code> if no
     * match is found
     * 
     * @see LanguagePredicate
     */
    private KeywordByLanguage findItem(List<KeywordByLanguage> results,
            Translation translation) 
    {
        Predicate predicate = new LanguagePredicate(
                translation.getKeyword().getKeyword(), 
                translation.getKeyword().getContext(), 
                translation.getBundle(), 
                translation.getCountry());
        
        return (KeywordByLanguage) CollectionUtils.find(results, predicate);
    }

    /**
     * Filter to determine if two {@link KeywordByLanguage} objects are equal
     * based on all attributes of the Predicate.
     * 
     * @author bsion
     *
     */
    private static class LanguagePredicate implements Predicate
    {
        private String keyword;
        private String context;
        private Bundle bundle;
        private Country country;

        /**
         * Create a new instance of LanguagePredicate.
         * 
         * @param keyword the {@link Keyword} keyword to match
         * @param context the {@link Keyword} context to match
         * @param bundle the {@link Bundle} to match
         * @param country the {@link Country} to match
         */
        public LanguagePredicate(final String keyword, 
                                final String context, 
                                final Bundle bundle, 
                                final Country country) 
        {
            this.keyword = keyword;
            this.context = context;
            this.bundle = bundle;
            this.country = country;
        }

        public boolean evaluate(Object object)
        {
            KeywordByLanguage item = (KeywordByLanguage) object;
            EqualsBuilder builder = new EqualsBuilder();
            return builder.append(keyword, item.getKeyword()).
                append(context, item.getContext()).
                append(bundle, item.getBundle()).
                append(country, item.getCountry()).
                isEquals();
        }
    }
}
