package org.tonguetied.web;


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

import static org.junit.Assert.*;


/**
 * @author bsion
 *
 */
public class TranslationTransformerTest {

    private Country singapore;
    private Country australia;

    private Language english;
    private Language chinese;
    
    private Bundle bundle1;
    
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keywordWithNoTranslations;
    
    private Translation translation1;
    private Translation translation2;
    private Translation translation3;
    private Translation translation4;
    
    private Translation translation5;
    
    private List<Keyword> keywords;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        singapore = new Country();
        singapore.setCode(CountryCode.SG);
        singapore.setName("Singapore");
        
        australia = new Country();
        australia.setCode(CountryCode.AU);
        australia.setName("Australia");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        chinese = new Language();
        chinese.setCode(LanguageCode.sc);
        chinese.setName("Simplified Chinese");
        
        bundle1 = new Bundle();
        bundle1.setName("testBundle");
        bundle1.setDescription("this is a test bundle");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        
        translation1 = new Translation();
        translation1.setBundle(bundle1);
        translation1.setCountry(singapore);
        translation1.setLanguage(chinese);
        translation1.setValue("xyz(sc)(sg)(b1)");
        translation1.setKeyword(keyword1);
        keyword1.addTranslation(translation1);
        
        translation2 = new Translation();
        translation2.setBundle(bundle1);
        translation2.setCountry(australia);
        translation2.setLanguage(chinese);
        translation2.setValue("xyz(en)(sg)(b1)");
        translation2.setKeyword(keyword1);
        keyword1.addTranslation(translation2);

        translation3 = new Translation();
        translation3.setBundle(bundle1);
        translation3.setCountry(australia);
        translation3.setLanguage(english);
        translation3.setValue("");
        translation3.setKeyword(keyword1);
        keyword1.addTranslation(translation3);
        
        translation4 = new Translation();
        translation4.setBundle(bundle1);
        translation4.setCountry(australia);
        translation4.setLanguage(null);
        translation4.setValue("xyz(en)(null)(b1)");
        translation4.setKeyword(keyword1);
        keyword1.addTranslation(translation4);

        keyword2 = new Keyword();
        keyword2.setKeyword("keywordTwo");
        keyword2.setContext("keyword 2");
        
        translation5 = new Translation();
        translation5.setBundle(bundle1);
        translation5.setCountry(singapore);
        translation5.setLanguage(chinese);
        translation5.setValue("xyz(sc)(sg)(b1)");
        translation5.setKeyword(keyword2);
        keyword2.addTranslation(translation5);
        
        keywordWithNoTranslations = new Keyword();
        keywordWithNoTranslations.setKeyword("keywordWithNoTranslations");
        keywordWithNoTranslations.setContext(null);
        
        keywords = new ArrayList<Keyword>();
        keywords.add(keyword1);
        keywords.add(keyword2);
        keywords.add(keywordWithNoTranslations);
    }
    
    @Test
    public final void testTransformKeywordsWithNullList() throws Exception {
        List<Translation> translations = TranslationTransformer.transform(null);
        
        assertTrue(translations.isEmpty());
    }
    
    @Test
    public final void testTransformKeywordsWithEmptyList() throws Exception {
        List<Translation> translations = 
            TranslationTransformer.transform(new ArrayList<Keyword>());
        
        assertTrue(translations.isEmpty());
    }
    
    @Test
    public final void testTransformKeywords() throws Exception {
        List<Translation> translations = 
            TranslationTransformer.transform(keywords);
        
        assertEquals(6, translations.size());
        assertTrue(translations.contains(translation1));
        assertTrue(translations.contains(translation2));
        assertTrue(translations.contains(translation3));
        assertTrue(translations.contains(translation4));
        assertTrue(translations.contains(translation5));
        
//        Translation translation = new Translation();
//        translation.setKeyword(keywordWithNoTranslations.clone());
//        assertTrue(translations.contains(translations));
    }
}
