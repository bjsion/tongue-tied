package org.tonguetied.web;

import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.service.ApplicationService;


/**
 * Value object used to store and pass search criteria.
 * 
 * @author bsion
 * @see ApplicationService#findKeywords(Keyword, boolean, Integer, Integer)
 */
public class SearchForm {
    private Keyword keyword;
    private boolean ignoreCase;
    
    public SearchForm() {
        this.ignoreCase = true;
        this.keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        this.keyword.addTranslation(translation);
    }
    
    /**
     * @return the specific bundle to search for
     */
    public Bundle getBundle() {
        return this.keyword.getTranslations().first().getBundle();
    }
    
    /**
     * @param bundle the bundle to set
     */
    public void setBundle(Bundle bundle) {
        this.keyword.getTranslations().first().setBundle(bundle);
    }
    
    /**
     * @return the specific country to search for
     */
    public Country getCountry() {
        return this.keyword.getTranslations().first().getCountry();
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.keyword.getTranslations().first().setCountry(country);
    }
    
    /**
     * 
     * @return the specific language to search for
     */
    public Language getLanguage() {
        return this.keyword.getTranslations().first().getLanguage();
    }
    
    /**
     * 
     * @param language
     */
    public void setLanguage(Language language) {
        this.keyword.getTranslations().first().setLanguage(language);
    }

    /**
     * @return the specific keyword key to search for. This can be all, or part
     * of the text
     */
    public String getKeywordKey() {
        return keyword.getKeyword();
    }
    
    /**
     * @param keywordKey the keyword to set
     */
    public void setKeywordKey(String keywordKey) {
        this.keyword.setKeyword(keywordKey);
    }
    
    /**
     * @return the translatedText
     */
    public String getTranslatedText() {
        return this.keyword.getTranslations().first().getValue();
    }
    
    /**
     * Set the value of the translated text to search.
     * 
     * @param translatedText the translatedText to set
     */
    public void setTranslatedText(String translatedText) {
        this.keyword.getTranslations().first().setValue(translatedText);
    }
    
    /**
     * @return the translation state
     */
    public TranslationState getTranslationState() {
        return this.keyword.getTranslations().first().getState();
    }

    /**
     * Set the value of the translation state to search.
     * 
     * @param state the translation state to set
     */
    public void setTranslationState(TranslationState state) {
        this.keyword.getTranslations().first().setState(state);
    }

    /**
     * Return flag indicating if text case should be considered during search.
     * 
     * @return the <code>true</code> if case is to be ignored during search, 
     * <code>false</code> otherwise.
     */
    public boolean getIgnoreCase() {
        return ignoreCase;
    }

    /**
     * @param ignoreCase the ignoreCase to set
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
    
    /**
     * 
     * @return
     */
    public Keyword getKeyword() {
        return this.keyword;
    }
}