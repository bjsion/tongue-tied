package org.tonguetied.datatransfer.importing;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;

/**
 * This predicate is used to find {@link Translation}s based off its 
 * business keys of {@link Bundle}, {@link Country} and {@link Language}.
 * 
 * @author bsion
 *
 */
final class TranslationPredicate implements Predicate
{
    private Bundle bundle;
    private Country country;
    private Language language;
    
    /**
     * Create a new instance of TranslationPredicate.
     * 
     * @param bundle the {@link Bundle} on which to search
     * @param country the {@link Country} on which to search
     * @param language the {@link Language} on which to search
     */
    public TranslationPredicate(Bundle bundle, Country country, Language language)
    {
        this.bundle = bundle;
        this.country = country;
        this.language = language;
    }
    
    /** 
     * Evaluate if a {@link Translation}s business keys are equal. This  
     * method evaluates if the {@link Language}, {@link Bundle} and
     * {@link Country} are equal
     * 
     * @return <code>true</code> if the {@link Translation} business keys
     * match. <code>false</code> otherwise
     * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
     */
    public boolean evaluate(Object object)
    {
        Translation translation = (Translation) object;
        
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(language, translation.getLanguage()).
            append(country, translation.getCountry()).
            append(bundle, translation.getBundle());
        
        return builder.isEquals();
    }
}