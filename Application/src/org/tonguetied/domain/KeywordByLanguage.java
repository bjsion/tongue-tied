package org.tonguetied.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * Value object for storing {@link Translation}s in a {@link Language} centric
 * mannor.
 * 
 * This object is immutable.
 * 
 * @author bsion
 *
 */
public class KeywordByLanguage {
    private String keyword;
    private String context;
    private Bundle bundle;
    private Country country;
    private Map<LanguageCode, String> translations;
    
    /**
     * Create a new instance of KeywordByLanguage.
     * 
     * @param keyword
     * @param context
     * @param bundle
     * @param country
     * @param translations
     */
    public KeywordByLanguage(String keyword, String context, Bundle bundle, Country country, Map<LanguageCode, String> translations) {
        this.keyword = keyword;
        this.context = context;
        this.bundle = bundle;
        this.country = country;
        this.translations = translations;
    }

    /**
     * Create a new instance of KeywordByLanguage.
     * 
     * @param keyword
     * @param context
     * @param bundle
     * @param country
     */
    public KeywordByLanguage(String keyword, String context, Bundle bundle, Country country) {
        this(keyword, context, bundle, country, new HashMap<LanguageCode, String>());
    }

    /**
     * @return the {@link Bundle} associated with this {@link Translation}
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * @return the context of the {@link Keyword} associated to this
     * {@link Translation}
     */
    public String getContext() {
        return context;
    }

    /**
     * @return the {@link Country} associated with this {@link Translation}
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @return the keyword name of the {@link Keyword} associated to this
     * {@link Translation}
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @return the translations
     */
    public Map<LanguageCode, String> getTranslations() {
        return translations;
    }
    
    public Set<Entry<LanguageCode, String>> getTranslationEntries() {
        return this.translations.entrySet();
    }
    
    public void addTranslation(LanguageCode language, String value) {
        translations.put(language, value);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
            isEqual = true;
        else if (obj != null && getClass() == obj.getClass()) {
            final KeywordByLanguage other = (KeywordByLanguage) obj;
            EqualsBuilder builder = new EqualsBuilder();
            isEqual = builder.append(keyword, other.keyword).
                append(context, other.context).
                append(bundle, other.bundle).
                append(country, other.country).
                append(translations, other.translations).
                isEquals();
        }
        return isEqual;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(23, 31);
        builder.append(keyword).
            append(context).
            append(bundle).
            append(country).
            append(translations);

        return builder.toHashCode();
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
