package org.tonguetied.service;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.junit.Test;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class AppilcationServiceTest extends AbstractServiceTest {
    private Country singapore;
    private Country australia;

    private Language english;
    private Language chinese;
    
    private Bundle bundle;
    
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;
    
    private ApplicationService appService;
    
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
        
        appService.saveOrUpdate(singapore);
        appService.saveOrUpdate(australia);
        appService.saveOrUpdate(english);
        appService.saveOrUpdate(chinese);
        appService.saveOrUpdate(bundle);
        appService.saveOrUpdate(keyword1);
        appService.saveOrUpdate(keyword2);
        appService.saveOrUpdate(keyword3);
        appService.saveOrUpdate(keyword4);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getBundles()}.
     */
    @Test
    public final void testGetBundles() {
        List<Bundle> bundles = appService.getBundles();
        assertEquals(1, bundles.size());
        assertTrue(bundles.contains(bundle));
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getCountries()}.
     */
    @Test
    public final void testGetCountries() {
        List<Country> countries = appService.getCountries();
        assertEquals(2, countries.size());
        assertTrue(countries.contains(singapore));
        assertTrue(countries.contains(australia));
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getKeywords()}.
     */
    @Test
    public final void testGetKeywords() {
        List<Keyword> keywords = appService.getKeywords();
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link ApplicationServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPagination() {
        List<Keyword> keywords = appService.getKeywords(2, 1);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }

    /**
     * Test method for {@link ApplicationServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationInvalidFirstPosition() {
        List<Keyword> keywords = appService.getKeywords(8, 1);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link ApplicationServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullFirstPosition() {
        List<Keyword> keywords = appService.getKeywords(null, null);
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link ApplicationServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationZeroMax() {
        List<Keyword> keywords = appService.getKeywords(2,0);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link ApplicationServiceImpl#getKeywords(Integer, Integer)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullMax() {
        List<Keyword> keywords = appService.getKeywords(2, null);
        assertEquals(2, keywords.size());
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getLanguages()}.
     */
    @Test
    public final void testGetLanguages() {
        List<Language> languages = appService.getLanguages();
        assertEquals(2, languages.size());
        assertTrue(languages.contains(english));
        assertTrue(languages.contains(chinese));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeyword() {
        Keyword retrieved = appService.getKeyword(keyword1.getId());
        
        assertEquals(keyword1, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeywordWithInvalidId() {
        Keyword retrieved = appService.getKeyword(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordByKeyword() {
        Keyword retrieved = appService.getKeyword(keyword1.getKeyword());
        
        assertEquals(keyword1, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordWithUnknownKeyword() {
        Keyword retrieved = appService.getKeyword("unknown");
        
        assertNull(retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundle() {
        Bundle retrieved = appService.getBundle(bundle.getId());
        
        assertEquals(bundle, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleWithInvalidId() {
        Bundle retrieved = appService.getBundle(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleByName() {
        Bundle retrieved = appService.getBundle(bundle.getName());
        
        assertEquals(bundle, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleWithInvalidName() {
        Bundle retrieved = appService.getBundle("invalid");
        
        assertNull(retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguage() {
        Language retrieved = appService.getLanguage(english.getId());
        
        assertEquals(english, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguageWithInvalidId() {
        Language retrieved = appService.getLanguage(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getLanguage(LanguageCode)}.
     */
    @Test
    public final void testGetLanguageByCode() {
        Language retrieved = appService.getLanguage(LanguageCode.sc);
        assertEquals(chinese, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getLanguage(LanguageCode)}.
     */
    @Test
    public final void testGetLanguageWithInvalidCode() {
        Language retrieved = appService.getLanguage(LanguageCode.ak);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountry() {
        Country retrieved = appService.getCountry(australia.getId());
        
        assertEquals(australia, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountryWithInvalidId() {
        Country retrieved = appService.getCountry(-1L);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getCountry(CountryCode)}.
     */
    @Test
    public final void testGetCountryByCode() {
        Country retrieved = appService.getCountry(CountryCode.SG);
        assertEquals(singapore, retrieved);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#getCountry(CountryCode)}.
     */
    @Test
    public final void testGetCountryWithInvalidCode() {
        Country retrieved = appService.getCountry(CountryCode.AD);
        
        assertNull(retrieved);
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testFindKeywordsWithKeywordNull() {
        List<Keyword> keywords = null;
        try {
            keywords = appService.findKeywords(null, true, 0, 20);
            fail("null keyword not allowed");
        }
        catch (IllegalArgumentException iae) {
            assertNull(keywords);
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test
    public final void testFindKeywordsWithNoMatches() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("unknownText");
        List<Keyword> keywords = 
            appService.findKeywords(keyword, true, 0, null);
        assertTrue(keywords.isEmpty());
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test
    public final void testFindKeywordsByKeyword() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        List<Keyword> keywords = 
            appService.findKeywords(keyword, true, 0, null);
        assertEquals(2, keywords.size());
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test
    public final void testFindKeywordsByKeywordEmptyString() {
        Keyword keyword = new Keyword();
        keyword.setKeyword("");
        List<Keyword> keywords = 
            appService.findKeywords(keyword, true, 0, null);
        assertEquals(4, keywords.size());
        assertTrue(keywords.contains(keyword1));
        assertTrue(keywords.contains(keyword2));
        assertTrue(keywords.contains(keyword3));
        assertTrue(keywords.contains(keyword4));
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
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
        appService.saveOrUpdate(keyword3);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        translation.setValue("st");
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            appService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test
    public final void testFindKeywordsByBundle() {
        Translation translation1 = new Translation();
        translation1.setLanguage(english);
        translation1.setValue("test");
        translation1.setState(TranslationState.UNVERIFIED);
        translation1.setBundle(bundle);
        keyword3.addTranslation(translation1);
        appService.saveOrUpdate(keyword3);
        
        Translation translation2 = new Translation();
        translation2.setLanguage(english);
        translation2.setValue("test");
        translation2.setState(TranslationState.QUERIED);
        keyword4.addTranslation(translation2);
        appService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setBundle(bundle);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            appService.findKeywords(keyword, false, 0, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword3));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
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
        appService.saveOrUpdate(keyword1);
        
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
        appService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        translation.setCountry(singapore);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            appService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword4));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
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
        appService.saveOrUpdate(keyword1);
        
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
        appService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(TranslationState.VERIFIED);
        keyword.addTranslation(translation);
        List<Keyword> keywords = 
            appService.findKeywords(keyword, false, null, null);
        assertEquals(1, keywords.size());
        assertTrue(keywords.contains(keyword1));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#findKeywords(Keyword, boolean, MatchMode)}.
     */
    @Test
    public final void testFindKeywordsWithEmptyKeyword() {
        Keyword keyword = new Keyword();
        keyword.addTranslation(new Translation());
        List<Keyword> keywords = 
            appService.findKeywords(keyword, true, 0, null);
        assertTrue(keywords.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeyword() {
        Translation translation = new Translation();
        translation.setLanguage(chinese);
        translation.setValue("&#20320;&#22909;");
        translation.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation);
        appService.saveOrUpdate(keyword2);
        
        Long keywordId = keyword2.getId();
        appService.deleteKeyword(keywordId);
        
        assertNull(appService.getKeyword(keywordId));
        
        // TODO: check that translations are deleted as well
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeywordWithTranslations() {
        Long keywordId = keyword1.getId();
        appService.deleteKeyword(keywordId);
        
        assertNull(appService.getKeyword(keywordId));
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ApplicationServiceImpl#deleteKeyword(Long)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testDeleteKeywordInvalidId() {
        try {
            appService.deleteKeyword(-1L);
        }
        catch (IllegalArgumentException iae) {
            
        }
    }
    
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }
}
