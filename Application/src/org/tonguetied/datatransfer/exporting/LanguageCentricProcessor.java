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
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordByLanguage;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class LanguageCentricProcessor implements ExportDataPostProcessor {
    
    private static final Logger logger = 
        Logger.getLogger(LanguageCentricProcessor.class);

    /* (non-Javadoc)
     * @see org.tonguetied.service.ExportDataPostProcessor#transformData()
     */
    public List<KeywordByLanguage> transformData(List<Translation> translations, TransferRepository transferRepository) {
        List<KeywordByLanguage> results = new ArrayList<KeywordByLanguage>();
        if (translations != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("transforming " + translations.size() + 
                        " objects");
            }
            KeywordByLanguage item;
            Country defaultCountry = transferRepository.getCountry(CountryCode.DEFAULT);
            for (Translation translation : translations) {
                LanguageCode languageCode = null;
                if (CountryCode.TW == translation.getCountry().getCode()) {
                    translation.setCountry(defaultCountry);
                    languageCode = LanguageCode.zht;
                }
                item = findItem(results, translation);
                if (item == null) {
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

    /* (non-Javadoc)
     * @see org.tonguetied.service.ExportDataPostProcessor#addData(java.util.Map)
     */
    public void addData(Map<String, Object> root, ExportParameters parameters) {
        List<Language> languages = new ArrayList<Language>();
        for (Language language: parameters.getLanguages()) {
            languages.add(language);
            if (LanguageCode.zh.equals(language.getCode())) {
                for (Country country : parameters.getCountries()) {
                    if (CountryCode.TW.equals(country.getCode())) {
                        Language traditionalChinese = new Language();
                        traditionalChinese.setCode(LanguageCode.zht);
                        traditionalChinese.setName("Traditional Chinese");
                        languages.add(traditionalChinese);
                    }
                }
            }
        }
        Collections.sort(languages);
        root.put("languages", languages);
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
    private static class LanguagePredicate implements Predicate {
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

        /* (non-Javadoc)
         * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
         */
        public boolean evaluate(Object object) {
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
