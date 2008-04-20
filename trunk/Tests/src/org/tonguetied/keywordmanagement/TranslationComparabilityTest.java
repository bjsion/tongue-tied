package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

import junitx.extensions.ComparabilityTestCase;

public class TranslationComparabilityTest extends ComparabilityTestCase {
    
    private Language hebrew;
    private Language russian;
    private Language defaultLanguage;
    private Country defaultCountry;
    private Country israel;
    private Bundle bundle;
    private Keyword keyword1;
    private Keyword keyword2;

    /**
     * @param name
     */
    public TranslationComparabilityTest(String name) {
        super(name);

        hebrew = new Language();
        hebrew.setCode(LanguageCode.he);
        hebrew.setName("Hebrew");
        
        russian = new Language();
        russian.setCode(LanguageCode.ru);
        russian.setName("Russian");
        
        defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        
        israel = new Country();
        israel.setCode(CountryCode.IL);
        israel.setName("Israel");
        
        defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        
        bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setResourceName("test");
        bundle.setDescription("description");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("keywordOne");
        keyword1.setContext("context");
        
        keyword2 = new Keyword();
        keyword2.setKeyword("keywordTwo");
        keyword2.setContext("context");
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<?> createEqualInstance() throws Exception {
        Translation translation = 
            new Translation(bundle, israel, hebrew, "value", TranslationState.QUERIED);
        translation.setKeyword(keyword1);
        return translation;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<?> createGreaterInstance() throws Exception {
        Translation translation = 
            new Translation(bundle, israel, russian, "value", TranslationState.QUERIED);
        translation.setKeyword(keyword1);
        return translation;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<?> createLessInstance() throws Exception {
        Translation translation = 
            new Translation(bundle, defaultCountry, hebrew, "value", TranslationState.UNVERIFIED);
        translation.setKeyword(keyword1);
        return translation;
    }

}
