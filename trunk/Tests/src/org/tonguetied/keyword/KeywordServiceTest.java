package org.tonguetied.keywordmanagement;

import java.util.List;

import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceImpl;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * @author bsion
 *
 */
public class KeywordServiceTest extends AbstractServiceTest {
    private Country singapore;
    private Country australia;

    private Language english;
    private Language chinese;
    
    private Bundle bundle;
    
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;
    
    private KeywordService keywordService;
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
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
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setDescription("this is a test bundle");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        
        keyword2 = new Keyword();
        keyword2.setKeyword("anotherkeyword");
        keyword2.setContext("Keyword 2");
        
        keyword3 = new Keyword();
        keyword3.setKeyword("differentKeyword");
        keyword3.setContext("keyword 3");
        
        keyword4 = new Keyword();
        keyword4.setKeyword("oneOtherKeyword");
        keyword4.setContext("Keyword 4");
        
        keywordService.saveOrUpdate(singapore);
        keywordService.saveOrUpdate(australia);
        keywordService.saveOrUpdate(english);
        keywordService.saveOrUpdate(chinese);
        keywordService.saveOrUpdate(bundle);
        keywordService.saveOrUpdate(keyword1);
        keywordService.saveOrUpdate(keyword2);
        keywordService.saveOrUpdate(keyword3);
        keywordService.saveOrUpdate(keyword4);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundles()}.
     */
    @Test
    public final void testGetBundles() {
        List<Bundle> bundles = keywordService.getBundles();
        assertEquals(1, bundles.size());
        assertTrue(bundles.contains(bundle));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountries()}.
     */
    @Test
    public final void testGetCountries() {
        List<Country> countries = keywordService.getCountries();
        assertEquals(2, countries.size());
        assertTrue(countries.contains(singapore));
        assertTrue(countries.contains(australia));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeywords()}.
     */
    @Test
    public final void testGetKeywords() {
        List<Keyword> keywords = keywordService.getKeywords();
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPagination() {
        List<Keyword> keywords = keywordService.getKeywords(2, 1);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationInvalidFirstPosition() {
        List<Keyword> keywords = keywordService.getKeywords(8, 1);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullFirstPosition() {
        List<Keyword> keywords = keywordService.getKeywords(null, null);
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationZeroMax() {
        List<Keyword> keywords = keywordService.getKeywords(2,0);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullMax() {
        List<Keyword> keywords = keywordService.getKeywords(2, null);
        assertEquals(2, keywords.size());
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguages()}.
     */
    @Test
    public final void testGetLanguages() {
        List<Language> languages = keywordService.getLanguages();
        assertEquals(2, languages.size());
        assertTrue(languages.contains(english));
        assertTrue(languages.contains(chinese));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeyword() {
        Keyword retrieved = keywordService.getKeyword(keyword1.getId());
        
        assertEquals(keyword1, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeywordWithInvalidId() {
        Keyword retrieved = keywordService.getKeyword(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordByKeyword() {
        Keyword retrieved = keywordService.getKeyword(keyword1.getKeyword());
        
        assertEquals(keyword1, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordWithUnknownKeyword() {
        Keyword retrieved = keywordService.getKeyword("unknown");
        
        assertNull(retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundle() {
        Bundle retrieved = keywordService.getBundle(bundle.getId());
        
        assertEquals(bundle, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleWithInvalidId() {
        Bundle retrieved = keywordService.getBundle(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleByName() {
        Bundle retrieved = keywordService.getBundle(bundle.getName());
        
        assertEquals(bundle, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleWithInvalidName() {
        Bundle retrieved = keywordService.getBundle("invalid");
        
        assertNull(retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguage() {
        Language retrieved = keywordService.getLanguage(english.getId());
        
        assertEquals(english, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguageWithInvalidId() {
        Language retrieved = keywordService.getLanguage(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(org.tonguetied.keywordmanagement.Language.LanguageCode)}.
     */
    @Test
    public final void testGetLanguageByCode() {
        Language retrieved = keywordService.getLanguage(LanguageCode.sc);
        assertEquals(chinese, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(org.tonguetied.keywordmanagement.Language.LanguageCode)}.
     */
    @Test
    public final void testGetLanguageWithInvalidCode() {
        Language retrieved = keywordService.getLanguage(LanguageCode.ak);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountry() {
        Country retrieved = keywordService.getCountry(australia.getId());
        
        assertEquals(australia, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountryWithInvalidId() {
        Country retrieved = keywordService.getCountry(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(org.tonguetied.keywordmanagement.Country.CountryCode)}.
     */
    @Test
    public final void testGetCountryByCode() {
        Country retrieved = keywordService.getCountry(CountryCode.SG);
        assertEquals(singapore, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(org.tonguetied.keywordmanagement.Country.CountryCode)}.
     */
    @Test
    public final void testGetCountryWithInvalidCode() {
        Country retrieved = keywordService.getCountry(CountryCode.AD);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testFindKeywordsWithKeywordNull() {
        List<Keyword> keywords = null;
        try {
            keywords = keywordService.findKeywords(null, true, 0, 20);
            fail("null keyword not allowed");
        }
        catch (IllegalArgumentException iae) {
            assertNull(keywords);
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsWithNoMatches() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("unknownText");
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, 0, null);
        assertTrue(keywords.isEmpty());
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeyword() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, 0, null);
        assertEquals(2, keywords.size());
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordEmptyString() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("");
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, 0, null);
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByTranslationValue() {
        Translation translation1 = new Translation();
        translation1.setValue("test");
        translation1.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation1);
        Translation translation2 = new Translation();
        translation2.setValue("test");
        translation2.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation2);
        keywordService.saveOrUpdate(keyword3);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        translation.setValue("st");
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByBundle() {
        Translation translation1 = new Translation();
        translation1.setLanguage(english);
        translation1.setValue("test");
        translation1.setState(TranslationState.UNVERIFIED);
        translation1.setBundle(bundle);
        keyword3.addTranslation(translation1);
        keywordService.saveOrUpdate(keyword3);
        
        Translation translation2 = new Translation();
        translation2.setLanguage(english);
        translation2.setValue("test");
        translation2.setState(TranslationState.QUERIED);
        keyword4.addTranslation(translation2);
        keywordService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setBundle(bundle);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, 0, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByCountry() {
        Translation translation1 = new Translation();
        translation1.setLanguage(english);
        translation1.setValue("t1");
        translation1.setState(TranslationState.VERIFIED);
        translation1.setBundle(bundle);
        translation1.setCountry(australia);
        keyword1.addTranslation(translation1);
        keywordService.saveOrUpdate(keyword1);
        
        Translation translation2 = new Translation();
        translation2.setLanguage(english);
        translation2.setValue("t2");
        translation2.setState(TranslationState.VERIFIED);
        translation2.setCountry(singapore);
        keyword4.addTranslation(translation2);
        Translation translation3 = new Translation();
        translation3.setLanguage(chinese);
        translation3.setValue("t3");
        translation3.setState(TranslationState.VERIFIED);
        translation3.setCountry(singapore);
        keyword4.addTranslation(translation3);
        keywordService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        translation.setCountry(singapore);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword4));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByTranslationState() {
        Translation translation1 = new Translation();
        translation1.setLanguage(english);
        translation1.setValue("t1");
        translation1.setState(TranslationState.VERIFIED);
        translation1.setBundle(bundle);
        translation1.setCountry(australia);
        keyword1.addTranslation(translation1);
        keywordService.saveOrUpdate(keyword1);
        
        Translation translation2 = new Translation();
        translation2.setLanguage(english);
        translation2.setValue("t2");
        translation2.setState(TranslationState.UNVERIFIED);
        translation2.setCountry(singapore);
        keyword4.addTranslation(translation2);
        Translation translation3 = new Translation();
        translation3.setLanguage(chinese);
        translation3.setValue("t3");
        translation3.setState(TranslationState.QUERIED);
        translation3.setCountry(singapore);
        keyword4.addTranslation(translation3);
        keywordService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(TranslationState.VERIFIED);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword1));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsWithEmptyKeyword() {
        Keyword keyword = new Keyword();
        keyword.addTranslation(new Translation());
        List<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, 0, null);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeyword() {
        Translation translation = new Translation();
        translation.setLanguage(chinese);
        translation.setValue("&#20320;&#22909;");
        translation.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation);
        keywordService.saveOrUpdate(keyword2);
        
        Long keywordId = keyword2.getId();
        keywordService.deleteKeyword(keywordId);
        
        assertNull(keywordService.getKeyword(keywordId));
        
        // TODO: check that translations are deleted as well
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeywordWithTranslations() {
        Long keywordId = keyword1.getId();
        keywordService.deleteKeyword(keywordId);
        
        assertNull(keywordService.getKeyword(keywordId));
    }
    
    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testDeleteKeywordInvalidId() {
        try {
            keywordService.deleteKeyword(-1L);
        }
        catch (IllegalArgumentException iae) {
            
        }
    }
    
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }
}