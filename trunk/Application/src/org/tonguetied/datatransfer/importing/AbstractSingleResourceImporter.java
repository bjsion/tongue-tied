package org.tonguetied.datatransfer.importing;

import java.util.List;

import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

/**
 * Abstract class for importers that process a input data that relates to a
 * specific resource combination of {@link Bundle}, {@link Country} and 
 * {@link Language}.
 * 
 * @author bsion
 *
 */
public abstract class AbstractSingleResourceImporter extends Importer
{
    private Bundle bundle;
    private Country country;
    private Language language;

    /**
     * Create a new instance of AbstractSingleResourceImporter.
     *
     * @param keywordService
     */
    public AbstractSingleResourceImporter(KeywordService keywordService)
    {
        super(keywordService);
    }

    /**
     * @return the {@link Bundle} the imported file corresponds to
     */
    protected Bundle getBundle()
    {
        return bundle;
    }

    /**
     * Assign the bundle.
     *
     * @param bundle the bundle to set
     */
    protected void setBundle(final Bundle bundle)
    {
        this.bundle = bundle;
    }

    /**
     * @return the {@link Country} the imported file corresponds to
     */
    protected Country getCountry()
    {
        return country;
    }

    /**
     * Assign the country.
     *
     * @param country the country to set
     */
    protected void setCountry(final Country country)
    {
        this.country = country;
    }

    /**
     * @return the {@link Language} the imported file corresponds to
     */
    protected Language getLanguage()
    {
        return language;
    }

    /**
     * Assign the language.
     *
     * @param language the language to set
     */
    protected void setLanguage(final Language language)
    {
        this.language = language;
    }

    /**
     * Find the {@link CountryCode} based on the string <code>code</code>. If
     * the code is not a valid enum value then an 
     * {@link ImportErrorCode#illegalCountry} is added.
     * 
     * @param code the string code to evaluate
     * @param errorCodes the list of existing {@link ImportErrorCode}
     * @return the {@link CountryCode} matching <code>code</code> or 
     * <code>null</code> if no match is found
     */
    protected CountryCode getCountryCode(final String code, List<ImportErrorCode> errorCodes)
    {
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
     * {@link ImportErrorCode#illegalLanguage} is added.
     * 
     * @param code the string code to evaluate
     * @param errorCodes the list of existing {@link ImportErrorCode}
     * @return the {@link LanguageCode} matching <code>code</code> or 
     * <code>null</code> if no match is found
     */
    protected LanguageCode getLanguageCode(final String code, List<ImportErrorCode> errorCodes)
    {
        LanguageCode languageCode = null;
        try {
            languageCode = LanguageCode.valueOf(code);
        }
        catch (IllegalArgumentException iae) {
            errorCodes.add(ImportErrorCode.illegalLanguage);
        }
        return languageCode;
    }
}
