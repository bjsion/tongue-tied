package org.tonguetied.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.tonguetied.datatransfer.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * Data importer that handles input in resource or property file format. The 
 * resource file is read and entries are transformed into {@link Translation}s
 * and added to the system.
 *   
 * @author bsion
 * @see Properties
 */
public class PropertiesImporter extends Importer {
    private Bundle bundle;
    private Country country;
    private Language language;

    /**
     * Create a new instance of PropertiesImporter.
     * 
     * @param keywordService the interface to keyword functions
     */
    protected PropertiesImporter(KeywordService keywordService) {
        super(keywordService);
    }

    @Override
    protected void doImport(byte[] input, TranslationState state) throws ImportException {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(input);
            Properties properties = new Properties();
            properties.load(bais);
            Keyword keyword;
            Translation translation;
            String value;
            for (Entry<Object, Object> entry : properties.entrySet()) {
                keyword = getKeywordService().getKeyword((String) entry.getKey());
                value = "".equals(entry.getValue())? null: (String)entry.getValue();
                if (keyword == null) {
                    keyword = new Keyword();
                    keyword.setKeyword((String) entry.getKey());
                    translation = 
                        new Translation(bundle, country, language, value, state);
                    keyword.addTranslation(translation);
                }
                else {
                    Predicate predicate = 
                        new TranslationPredicate(bundle, country, language);
                    translation = (Translation) CollectionUtils.find(
                            keyword.getTranslations(), predicate);
                    if (translation == null) {
                        translation = 
                            new Translation(bundle, country, language, value, state);
                        keyword.addTranslation(translation);
                    }
                    else {
                        translation.setState(state);
                        translation.setValue(value);
                    }
                }
                
                getKeywordService().saveOrUpdate(keyword);
            }
        } 
        catch (IOException ioe) {
            throw new ImportException(ioe);
        }
        finally {
            
        }
    }

    /**
     * Validates the <code>fileName</code> to ensure that the fileName 
     * corresponds to an existing bundle.
     * 
     */
    @Override
    protected void validate(final String fileName, List<ImportErrorCode> errorCodes)
            throws ImportException 
    {
        String[] tokens = fileName.split("_");
        
        CountryCode countryCode = null;
        LanguageCode languageCode = null;
        switch (tokens.length) {
            case 1:
                // this is the default bundle, so no country or language
                countryCode = CountryCode.DEFAULT;
                languageCode = LanguageCode.DEFAULT;
                break;
            case 2:
                if (isCountryCode(tokens[1])) {
                    countryCode = getCountryCode(tokens[1], errorCodes);
                    languageCode = LanguageCode.DEFAULT;
                }
                else {
                    countryCode = CountryCode.DEFAULT;
                    languageCode = getLanguageCode(tokens[1], errorCodes);
                }
                break;
            case 3:
                countryCode = getCountryCode(tokens[2], errorCodes);
                languageCode = getLanguageCode(tokens[1], errorCodes);
                break;
            default:
                errorCodes.add(ImportErrorCode.invalidNameFormat);
                break;
        }

        bundle = getKeywordService().getBundleByResourceName(tokens[0]);
        if (bundle == null)
            errorCodes.add(ImportErrorCode.unknownBundle);
        
        this.country = getKeywordService().getCountry(countryCode);
        if (country == null)
            errorCodes.add(ImportErrorCode.unknownCountry);
        
        this.language = getKeywordService().getLanguage(languageCode);
        if (language == null)
            errorCodes.add(ImportErrorCode.unknownLanguage);
    }

    /**
     * Find the {@link CountryCode} based on the string <code>code</code>. If
     * the code is not a valid enum value then an 
     * {@link ImportErrorCode#illegalCountry} is added
     * 
     * @param code the string code to evaluate
     * @param errorCodes the list of existing {@link ImportErrorCode}
     * @return the {@link CountryCode} matching <code>code</code> or 
     * <code>null</code> if no match is found
     */
    private CountryCode getCountryCode(final String code,
            List<ImportErrorCode> errorCodes) {
        CountryCode countryCode = null;
        try {
            countryCode = CountryCode.valueOf(code);
        }
        catch (IllegalArgumentException iae) {
            errorCodes.add(ImportErrorCode.illegalCountry);
        }
        return countryCode;
    }

    /**
     * Find the {@link LanguageCode} based on the string <code>code</code>. If
     * the code is not a valid enum value then an 
     * {@link ImportErrorCode#illegalLanguage} is added
     * 
     * @param code the string code to evaluate
     * @param errorCodes the list of existing {@link ImportErrorCode}
     * @return the {@link LanguageCode} matching <code>code</code> or 
     * <code>null</code> if no match is found
     */
    private LanguageCode getLanguageCode(final String code,
            List<ImportErrorCode> errorCodes) {
        LanguageCode languageCode = null;
        try {
            languageCode = LanguageCode.valueOf(code);
        }
        catch (IllegalArgumentException iae) {
            errorCodes.add(ImportErrorCode.illegalLanguage);
        }
        return languageCode;
    }

    /**
     * Determines if the string component is a country code or not.
     * 
     * @param code the code to evaluate
     * @return <code>true</code> if the string corresponds to a potential 
     * country code, <code>false</code> otherwise
     */
    protected boolean isCountryCode(String code) {
        boolean isCountryCode = false;
        if (code != null && !"".equals(code))
            isCountryCode = Character.isUpperCase(code.charAt(0));
        
        return isCountryCode;
    }

    /**
     * @return the {@link Bundle} the imported file corresponds to
     */
    protected Bundle getBundle() {
        return bundle;
    }

    /**
     * @return the {@link Country} the imported file corresponds to
     */
    protected Country getCountry() {
        return country;
    }

    /**
     * @return the {@link Language} the imported file corresponds to
     */
    protected Language getLanguage() {
        return language;
    }
}
