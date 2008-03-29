package org.tonguetied.keywordmanagement;

import java.util.List;

import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class KeywordFactory {

    /**
     * Create a new {@link Keyword} with a new {@link Translation} for each 
     * {@link Language} provided. This factory method instantiates each
     * {@link Translation} with a {@link Language} from <code>languages</code>
     * and the {@link Country} from <code>country</code>.
     * 
     * @param languages the list of {@link Language}s for which translations 
     * should be created
     * @param country the default {@link Country} for which each translation 
     * should be created
     * @return A new {@link Keyword} with a list of predefined 
     * {@link Translation}s.
     * @throws IllegalArgumentException if the list of {@link Language}s is 
     * <code>null</code>
     */
    public static Keyword createKeyword(List<Language> languages, Country country) 
            throws IllegalArgumentException {
        if (languages == null) {
            throw new IllegalArgumentException(
                    "Cannot provide a null list of languages");
        }
        
        Keyword keyword = new Keyword();
        for(Language language: languages) {
            Translation translation = new Translation();
            translation.setLanguage(language);
            translation.setCountry(country);
            translation.setState(TranslationState.UNVERIFIED);
            keyword.addTranslation(translation);
        }
        
        return keyword;
    }
    
    /**
     * Create a new {@link Keyword} with a new {@link Translation} for each 
     * {@link Country} provided. This factory method instantiates each
     * {@link Translation} with a {@link Country} from <code>countries</code>
     * and the {@link Language} from <code>language</code>.
     * 
     * @param countries the list of {@link Country}s for which translations 
     * should be created
     * @param language the default {@link Language} for which each translation 
     * should be created
     * @return A new {@link Keyword} with a list of predefined 
     * {@link Translation}s.
     * @throws IllegalArgumentException if the list of {@link Country}s is 
     * <code>null</code>
     */
    public static Keyword createKeyword(List<Country> countries, Language language) 
            throws IllegalArgumentException {
        if (countries == null) {
            throw new IllegalArgumentException(
                    "Cannot provide a null list of countries");
        }
        
        Keyword keyword = new Keyword();
        for(Country country: countries) {
            Translation translation = new Translation();
            translation.setCountry(country);
            translation.setLanguage(language);
            translation.setState(TranslationState.UNVERIFIED);
            keyword.addTranslation(translation);
        }
        
        return keyword;
    }
}
