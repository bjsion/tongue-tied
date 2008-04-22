package org.tonguetied.datatransfer.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Value object to transport export selection criteria. This object does not
 * have a state.
 *  
 * @author bsion
 *
 */
public class ExportParameters {
    private List<Country> countries;
    private List<Bundle> bundles;
    private List<Language> languages;
    private TranslationState translationState;
    private FormatType formatType;
    
    /**
     * Create a new instance of the export parameters.  
     */
    public ExportParameters() {
        this.countries = new ArrayList<Country>();
        this.bundles = new ArrayList<Bundle>();
        this.languages = new ArrayList<Language>();
    }

    /**
     * @return the collection of {@link Bundle}s to export
     */
    public List<Bundle> getBundles() {
        return bundles;
    }

    /**
     * @param bundles the collection of bundles to export
     */
    public void setBundles(List<Bundle> bundles) {
        this.bundles = bundles;
    }
    
    public void addBundle(Bundle bundle) {
        this.bundles.add(bundle);
    }

    /**
     * @return the collection of {@link Country}s selected to export
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * @param countries the collection of {@link Country}s to export
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    
    public void addCountry(Country country) {
        this.countries.add(country);
    }

    /**
     * @return the {@link FormatType} to use for this export
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * @param formatType the {@link FormatType} to set
     */
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    /**
     * @return the collection of {@link Language}s to export
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * @param languages the collection of {@link Language}s to export
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
    
    public void addLanguage(Language language) {
        this.languages.add(language);
    }
    
    /**
     * @return the {@link TranslationState} to export
     */
    public TranslationState getTranslationState() {
        return translationState;
    }

    /**
     * @param translationState the {@link TranslationState} to export
     */
    public void setTranslationState(TranslationState translationState) {
        this.translationState = translationState;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
