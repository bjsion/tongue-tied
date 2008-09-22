package org.tonguetied.datatransfer.importing;


import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;

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
}
