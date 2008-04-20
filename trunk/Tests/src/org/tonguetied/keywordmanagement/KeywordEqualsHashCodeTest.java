package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class KeywordEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public KeywordEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        Bundle bundle = new Bundle();
        bundle.setName("bundle1");
        bundle.setDescription("resources");
        bundle.setResourceName("bundle1");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Translation translation = 
            new Translation(bundle, country, language, "translated value", TranslationState.UNVERIFIED);
        
        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        keyword.addTranslation(translation);
        
        return keyword;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        Bundle bundle = new Bundle();
        bundle.setName("Bundle 2");
        bundle.setDescription("resources");
        bundle.setResourceName("bundle2");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Translation translation = 
            new Translation(bundle, country, language, "another translated value", TranslationState.UNVERIFIED);
        
        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        keyword.addTranslation(translation);
        
        return keyword;
    }
}
