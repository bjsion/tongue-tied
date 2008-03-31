package org.tonguetied.keywordmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class KeywordTest {
    
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
     * Test method for {@link org.tonguetied.keywordmanagement.Keyword#clone()}.
     */
    @Test
    public final void testCloneWithEmptyValues() {
        Keyword keyword = new Keyword();
        keyword.setKeyword(null);
        keyword.setContext(null);
        keyword.setTranslations(null);
        
        Keyword clone = keyword.clone();
        
        assertFalse(keyword == clone);
        assertTrue(keyword.getClass() == clone.getClass());
        assertEquals(keyword, clone);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.Keyword#clone()}.
     */
    @Test
    public final void testCloneWithTranslation() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("a keyword");
        keyword.setContext("context");
        Translation translation = new Translation();
        translation.setBundle(bundle);
        translation.setCountry(vietnam);
        translation.setLanguage(vietnamese);
        translation.setValue("the value");
        keyword.addTranslation(translation);
        
        Keyword clone = keyword.clone();
        
        assertFalse(keyword == clone);
        assertTrue(keyword.getClass() == clone.getClass());
        assertEquals(keyword, clone);
        assertFalse(keyword.getTranslations().first() == clone.getTranslations().first());
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.Keyword#removeTranslation(Long)}.
     */
    @Test
    public final void testRemoveTranslation() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("a keyword");
        keyword.setContext("context");
        
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setBundle(bundle);
        translation.setCountry(vietnam);
        translation.setLanguage(vietnamese);
        translation.setValue("the value");
        keyword.addTranslation(translation);

        translation = new Translation();
        translation.setId(2L);
        translation.setBundle(bundle);
        translation.setCountry(vietnam);
        translation.setLanguage(french);
        translation.setValue("value 2");
        keyword.addTranslation(translation);
        
        assertEquals(2, keyword.getTranslations().size());
        
        keyword.removeTranslation(1L);
        
        assertEquals(1, keyword.getTranslations().size());
        assertTrue(keyword.getTranslations().contains(translation));
    }
}
