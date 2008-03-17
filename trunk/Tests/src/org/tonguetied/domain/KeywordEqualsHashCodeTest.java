package org.tonguetied.domain;

import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;

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
        bundle.setResourceDestination("home");
        bundle.setResourceName("bundle1");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        
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
        bundle.setResourceDestination("home");
        bundle.setResourceName("bundle2");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Translation translation = new Translation();
        translation.setValue("another translated value");
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        
        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        keyword.addTranslation(translation);
        
        return keyword;
    }
}
