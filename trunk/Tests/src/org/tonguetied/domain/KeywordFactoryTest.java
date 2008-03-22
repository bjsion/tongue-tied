package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class KeywordFactoryTest {
    
    private Language japanese;
    private Language vietnamese;
    private Language french;
    private Country japan;
    private Country vietnam;
    private List<Language> languages;
    private List<Country> countries;
    private Bundle bundle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.japan = new Country();
        this.japan.setCode(CountryCode.JP);
        this.japan.setName("Japan");
        
        this.vietnam = new Country();
        this.vietnam.setCode(CountryCode.VI);
        this.vietnam.setName("Vietnam");
        
        this.countries = new ArrayList<Country>();
        this.countries.add(this.japan);
        this.countries.add(this.vietnam);
        
        this.japanese = new Language();
        this.japanese.setCode(LanguageCode.ja);
        this.japanese.setName("Japanese");
        
        this.vietnamese = new Language();
        this.vietnamese.setCode(LanguageCode.vi);
        this.vietnamese.setName("Vietnamese");
        
        this.french = new Language();
        this.french.setCode(LanguageCode.fr);
        this.french.setName("French");
        
        this.bundle = new Bundle();
        this.bundle.setName("bundle name");
        this.bundle.setResourceName("resourceName");
        
        this.languages = new ArrayList<Language>();
        this.languages.add(japanese);
        this.languages.add(vietnamese);
        this.languages.add(french);
    }

    /**
     * Test method for {@link org.tonguetied.domain.KeywordFactory#createKeyword(java.util.List, org.tonguetied.domain.Country)}.
     */
    @Test
    public final void testConstructorWithListOfLanguages() {
        Keyword keyword = KeywordFactory.createKeyword(languages, japan);
        assertEquals(languages.size(), keyword.getTranslations().size());
        for (Translation translation: keyword.getTranslations()) {
            assertTrue(languages.contains(translation.getLanguage()));
            assertEquals(japan, translation.getCountry());
            assertEquals(TranslationState.UNVERIFIED, translation.getState());
        }
    }

    /**
     * Test method for {@link org.tonguetied.domain.KeywordFactory#createKeyword(java.util.List, org.tonguetied.domain.Country)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testConstructorWithListOfNullLanguages() {
        KeywordFactory.createKeyword(null, vietnam);
    }

    /**
     * Test method for {@link org.tonguetied.domain.KeywordFactory#createKeyword(java.util.List, org.tonguetied.domain.Language)}.
     */
    @Test
    public final void testConstructorWithListOfCountries() {
        Keyword keyword = KeywordFactory.createKeyword(countries, vietnamese);
        assertEquals(countries.size(), keyword.getTranslations().size());
        for (Translation translation: keyword.getTranslations()) {
            assertTrue(countries.contains(translation.getCountry()));
            assertEquals(vietnamese, translation.getLanguage());
            assertEquals(TranslationState.UNVERIFIED, translation.getState());
        }
    }

    /**
     * Test method for {@link org.tonguetied.domain.KeywordFactory#createKeyword(java.util.List, org.tonguetied.domain.Language)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testConstructorWithListOfNullCountries() {
        KeywordFactory.createKeyword(null, french);
    }
    
}
