package org.tonguetied.domain;

import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class TranslationEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public TranslationEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
        bundle.setResourceName("tonguetied");
        
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
        keyword.setContext("context");

        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        translation.setKeyword(keyword);
        translation.setState(TranslationState.UNVERIFIED);
        
        return translation;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
        bundle.setResourceName("tonguetied");
        
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
        keyword.setContext("context");

        Translation translation = new Translation();
        translation.setValue("another translated value");
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        translation.setKeyword(keyword);
        translation.setState(TranslationState.UNVERIFIED);
        
        return translation;
    }
}
