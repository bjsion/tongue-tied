/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.keywordmanagement;

import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.util.List;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.utils.pagination.Order;
import org.tonguetied.utils.pagination.PaginatedList;

/**
 * Unit tests for methods of the {@link KeywordServiceImpl} implementation of
 * the {@link KeywordService}.
 * 
 * @author bsion
 * 
 */
public class KeywordServiceTest extends AbstractServiceTest
{
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
    @Rollback
    protected void onSetUpInTransaction() throws Exception
    {
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
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        bundle.setDefault(false);
        bundle.setGlobal(false);

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

        getCountryRepository().saveOrUpdate(singapore);
        getCountryRepository().saveOrUpdate(australia);
        getLanguageRepository().saveOrUpdate(english);
        getLanguageRepository().saveOrUpdate(chinese);
        getBundleRepository().saveOrUpdate(bundle);
        getKeywordRepository().saveOrUpdate(keyword1);
        getKeywordRepository().saveOrUpdate(keyword2);
        getKeywordRepository().saveOrUpdate(keyword3);
        getKeywordRepository().saveOrUpdate(keyword4);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundles()}.
     */
    @Test
    public final void testGetBundles()
    {
        final List<Bundle> bundles = keywordService.getBundles();
        assertEquals(1, bundles.size());
        assertTrue(bundles.contains(bundle));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountries()}.
     */
    @Test
    public final void testGetCountries()
    {
        final List<Country> countries = keywordService.getCountries();
        assertEquals(2, countries.size());
        assertTrue(countries.contains(singapore));
        assertTrue(countries.contains(australia));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeywords()}.
     */
    @Test
    public final void testGetKeywords()
    {
        final List<Keyword> keywords = keywordService.getKeywords();
        assertEquals(4, keywords.size());
        assertEquals(keyword1, keywords.get(0));
        assertEquals(keyword2, keywords.get(1));
        assertEquals(keyword3, keywords.get(2));
        assertEquals(keyword4, keywords.get(3));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeywords()}.
     */
    @Test
    public final void testGetKeywordsOrderDesc()
    {
        final List<Keyword> keywords = keywordService.getKeywords(0, null, Order.desc);
        assertEquals(4, keywords.size());
        assertEquals(keyword4, keywords.get(0));
        assertEquals(keyword3, keywords.get(1));
        assertEquals(keyword2, keywords.get(2));
        assertEquals(keyword1, keywords.get(3));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPagination()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(2, 1, null);
        assertEquals(1, keywords.size());
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(keyword3, keywords.get(0));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationInvalidFirstPositionGreaterThanSize()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(8, 1, null);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNegativeFirstPosition()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(-1, 1, null);
        assertEquals(1, keywords.size());
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(keyword1, keywords.get(0));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullFirstPosition()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(null, null, null);
        assertEquals(4, keywords.size());
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(keyword1, keywords.get(0));
        assertEquals(keyword2, keywords.get(1));
        assertEquals(keyword3, keywords.get(2));
        assertEquals(keyword4, keywords.get(3));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationZeroMax()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(2, 0, null);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
        
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationNullMax()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(2, null, null);
        assertEquals(2, keywords.size());
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(keyword3, keywords.get(0));
        assertEquals(keyword4, keywords.get(1));
    }

    /**
     * Test method for {@link KeywordServiceImpl#getKeywords(Integer, Integer, Order)}.
     */
    @Test
    public final void testGetKeywordsWithPaginationMaxGreaterThanSize()
    {
        final PaginatedList<Keyword> keywords = keywordService.getKeywords(0, 7, null);
        assertEquals(4, keywords.size());
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(keyword1, keywords.get(0));
        assertEquals(keyword2, keywords.get(1));
        assertEquals(keyword3, keywords.get(2));
        assertEquals(keyword4, keywords.get(3));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguages()}.
     */
    @Test
    public final void testGetLanguages()
    {
        final List<Language> languages = keywordService.getLanguages();
        assertEquals(2, languages.size());
        assertTrue(languages.contains(english));
        assertTrue(languages.contains(chinese));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeyword()
    {
        final Keyword retrieved = keywordService.getKeyword(keyword1.getId());

        assertEquals(keyword1, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(Long)}.
     */
    @Test
    public final void testGetKeywordWithInvalidId()
    {
        final Keyword retrieved = keywordService.getKeyword(-1L);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordByKeyword()
    {
        final Keyword retrieved = keywordService.getKeyword(keyword1
                .getKeyword());

        assertEquals(keyword1, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getKeyword(String)}.
     */
    @Test
    public final void testGetKeywordWithUnknownKeyword()
    {
        final Keyword retrieved = keywordService.getKeyword("unknown");

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleById()
    {
        final Bundle retrieved = keywordService.getBundle(bundle.getId());

        assertEquals(bundle, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleWithInvalidId()
    {
        final Bundle retrieved = keywordService.getBundle(-1L);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundle(Long)}.
     */
    @Test
    public final void testGetBundleByName()
    {
        final Bundle retrieved = keywordService.getBundleByName(bundle
                .getName());

        assertEquals(bundle, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundleByName(String)}.
     */
    @Test
    public final void testGetBundleWithInvalidName()
    {
        final Bundle retrieved = keywordService.getBundleByName("invalid");

        assertNull(retrieved);
    }

    /**
     * Test the
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundleByResourceName(String)}
     * method with a valid value.
     */
    @Test
    public final void testGetBundleByResourceName()
    {
        final Bundle retrieved = keywordService.getBundleByResourceName(bundle
                .getResourceName());

        assertEquals(bundle, retrieved);
    }

    /**
     * Test the
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getBundleByResourceName(String)}
     * method with a invalid value. This should return <code>null</code>.
     */
    @Test
    public final void testGetBundleWithInvalidResourceName()
    {
        final Bundle retrieved = keywordService
                .getBundleByResourceName("invalid");

        assertNull(retrieved);
    }

    @Test
    public final void testGetDefaultBundle()
    {
        Bundle defaultBundle = new Bundle();
        defaultBundle.setDefault(true);
        defaultBundle.setDescription("default bundle");
        defaultBundle.setName("default");
        defaultBundle.setResourceName("default");
        keywordService.saveOrUpdate(defaultBundle);

        final Bundle retrieved = keywordService.getDefaultBundle();

        assertEquals(defaultBundle, retrieved);
    }

    @Test
    public final void testGetDefaultBundleWithNoDefault()
    {
        final Bundle retrieved = keywordService.getDefaultBundle();

        assertNull(retrieved);
    }

    @Test
    public final void testSaveOrUpdateDefaultBundle()
    {
        Bundle defaultBundle = new Bundle();
        defaultBundle.setDefault(true);
        defaultBundle.setDescription("bundle2");
        defaultBundle.setName("bundle2");
        defaultBundle.setResourceName("bundle2");
        keywordService.saveOrUpdate(defaultBundle);

        List<Bundle> bundles = keywordService.getBundles();
        assertEquals(2, bundles.size());
        validateBundles(defaultBundle, bundles);

        Bundle newBundle = new Bundle();
        newBundle.setDefault(true);
        newBundle.setDescription("bundle3");
        newBundle.setName("bundle3");
        newBundle.setResourceName("bundle3");
        keywordService.saveOrUpdate(newBundle);

        bundles = keywordService.getBundles();
        assertEquals(3, bundles.size());
        validateBundles(newBundle, bundles);

        Bundle anotherBundle = new Bundle();
        anotherBundle.setDefault(false);
        anotherBundle.setDescription("bundle4");
        anotherBundle.setName("bundle4");
        anotherBundle.setResourceName("bundle4");
        keywordService.saveOrUpdate(anotherBundle);

        bundles = keywordService.getBundles();
        assertEquals(4, bundles.size());
        validateBundles(newBundle, bundles);
    }

    private void validateBundles(Bundle defaultBundle, List<Bundle> bundles)
    {
        for (Bundle testBundle : bundles)
        {
            if (testBundle.getName().equals(defaultBundle.getName()))
                assertTrue(testBundle.isDefault());
            else
                assertFalse(testBundle.isDefault());
        }
    }

    @Test
    public final void testSaveOrUpdateBundleWithNoDefault()
    {
        Bundle newBundle = new Bundle();
        newBundle.setDefault(false);
        newBundle.setDescription("bundle2");
        newBundle.setName("bundle2");
        newBundle.setResourceName("bundle2");
        keywordService.saveOrUpdate(newBundle);

        final List<Bundle> bundles = keywordService.getBundles();
        assertEquals(2, bundles.size());
        for (Bundle testBundle : bundles)
        {
            assertFalse(testBundle.isDefault());
        }
    }

    @Test
    public final void testFindBundlesName()
    {
        final List<Bundle> bundles = keywordService.findBundles(bundle.getName(), null);
        assertEquals(1, bundles.size());
        assertTrue(bundles.contains(bundle));
    }
    
    @Test
    public final void testFindBundlesResourceName()
    {
        final List<Bundle> bundles = keywordService.findBundles(null, bundle.getResourceName());
        assertEquals(1, bundles.size());
        assertTrue(bundles.contains(bundle));
    }
    
    @Test
    public final void testFindBundlesWithNullParams()
    {
        final List<Bundle> bundles = keywordService.findBundles(null, null);
        assertTrue("bundles contains a value when it should be empty", bundles.isEmpty());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindBundlesWithNoMatches()
    {
        final List<Bundle> bundles = keywordService.findBundles("unknown", "unknown");
        assertTrue(bundles.isEmpty());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguage()
    {
        final Language retrieved = keywordService.getLanguage(english.getId());

        assertEquals(english, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(Long)}.
     */
    @Test
    public final void testGetLanguageWithInvalidId()
    {
        final Language retrieved = keywordService.getLanguage(-1L);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(org.tonguetied.keywordmanagement.Language.LanguageCode)}.
     */
    @Test
    public final void testGetLanguageByCode()
    {
        final Language retrieved = keywordService.getLanguage(LanguageCode.sc);
        assertEquals(chinese, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getLanguage(org.tonguetied.keywordmanagement.Language.LanguageCode)}.
     */
    @Test
    public final void testGetLanguageWithInvalidCode()
    {
        final Language retrieved = keywordService.getLanguage(LanguageCode.ak);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountry()
    {
        final Country retrieved = keywordService.getCountry(australia.getId());

        assertEquals(australia, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(Long)}.
     */
    @Test
    public final void testGetCountryWithInvalidId()
    {
        final Country retrieved = keywordService.getCountry(-1L);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(org.tonguetied.keywordmanagement.Country.CountryCode)}.
     */
    @Test
    public final void testGetCountryByCode()
    {
        final Country retrieved = keywordService.getCountry(CountryCode.SG);
        assertEquals(singapore, retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#getCountry(org.tonguetied.keywordmanagement.Country.CountryCode)}.
     */
    @Test
    public final void testGetCountryWithInvalidCode()
    {
        final Country retrieved = keywordService.getCountry(CountryCode.AD);

        assertNull(retrieved);
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFindKeywordsWithKeywordNull()
    {
        PaginatedList<Keyword> keywords = null;
        try
        {
            keywords = keywordService.findKeywords(null, true, null, 0, 20);
            fail("null keyword not allowed");
        }
        catch (IllegalArgumentException iae)
        {
            assertNull(keywords);
        }
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsWithNoMatches()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("unknownText");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, null);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeyword()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, null);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(2, keywords.size());
        assertEquals(keyword2, keywords.get(0));
        assertEquals(keyword4, keywords.get(1));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListInvalidFirstPositionGreaterThanSize()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 20, 1);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListInvalidFirstPositionNegative()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, -1, 1);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword2, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListNullFirstPosition()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, null, 1);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword2, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListNullMax()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, null);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(2, keywords.size());
        assertEquals(keyword2, keywords.get(0));
        assertEquals(keyword4, keywords.get(1));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListZeroMax()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, Order.asc, 0, 0);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedListMaxGreaterThanSize()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, 9);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(2, keywords.size());
        assertEquals(keyword2, keywords.get(0));
        assertEquals(keyword4, keywords.get(1));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordWithPagedList()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("the");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, 1);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword2, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordAndTranslationWithPagedList()
    {
        Translation translation1_1 = new Translation();
        translation1_1.setLanguage(english);
        translation1_1.setValue("TEST");
        translation1_1.setCountry(australia);
        translation1_1.setState(TranslationState.UNVERIFIED);
        translation1_1.setBundle(bundle);
        keyword1.addTranslation(translation1_1);
        Translation translation1_2 = new Translation();
        translation1_2.setLanguage(english);
        translation1_2.setValue("testing");
        translation1_2.setCountry(singapore);
        translation1_2.setState(TranslationState.QUERIED);
        translation1_2.setBundle(bundle);
        keyword1.addTranslation(translation1_2);
        Translation translation1_3 = new Translation();
        translation1_3.setLanguage(chinese);
        translation1_3.setValue("some more test");
        translation1_3.setCountry(singapore);
        translation1_3.setState(TranslationState.UNVERIFIED);
        translation1_3.setBundle(bundle);
        keyword1.addTranslation(translation1_3);
        keywordService.saveOrUpdate(keyword1);
        
        Translation translation2_1 = new Translation();
        translation2_1.setLanguage(english);
        translation2_1.setValue("TEST");
        translation2_1.setCountry(australia);
        translation2_1.setState(TranslationState.UNVERIFIED);
        translation2_1.setBundle(bundle);
        keyword2.addTranslation(translation2_1);
        Translation translation2_2 = new Translation();
        translation2_2.setLanguage(english);
        translation2_2.setValue("testing");
        translation2_2.setCountry(singapore);
        translation2_2.setState(TranslationState.QUERIED);
        translation2_2.setBundle(bundle);
        keyword2.addTranslation(translation2_2);
        Translation translation2_3 = new Translation();
        translation2_3.setLanguage(chinese);
        translation2_3.setValue("some more test");
        translation2_3.setCountry(singapore);
        translation2_3.setState(TranslationState.UNVERIFIED);
        translation2_3.setBundle(bundle);
        keyword2.addTranslation(translation2_3);
        keywordService.saveOrUpdate(keyword2);
        
        Translation translation3_1 = new Translation();
        translation3_1.setLanguage(english);
        translation3_1.setValue("TEST");
        translation3_1.setCountry(australia);
        translation3_1.setState(TranslationState.UNVERIFIED);
        translation3_1.setBundle(bundle);
        keyword3.addTranslation(translation3_1);
        Translation translation3_2 = new Translation();
        translation3_2.setLanguage(english);
        translation3_2.setValue("testing");
        translation3_2.setCountry(singapore);
        translation3_2.setState(TranslationState.QUERIED);
        translation3_2.setBundle(bundle);
        keyword3.addTranslation(translation3_2);
        Translation translation3_3 = new Translation();
        translation3_3.setLanguage(chinese);
        translation3_3.setValue("some more test");
        translation3_3.setCountry(singapore);
        translation3_3.setState(TranslationState.UNVERIFIED);
        translation3_3.setBundle(bundle);
        keyword3.addTranslation(translation3_3);
        keywordService.saveOrUpdate(keyword3);
        
        Translation translation4_1 = new Translation();
        translation4_1.setLanguage(english);
        translation4_1.setValue("TEST");
        translation4_1.setCountry(australia);
        translation4_1.setState(TranslationState.UNVERIFIED);
        translation4_1.setBundle(bundle);
        keyword4.addTranslation(translation4_1);
        Translation translation4_2 = new Translation();
        translation4_2.setLanguage(english);
        translation4_2.setValue("testing");
        translation4_2.setCountry(singapore);
        translation4_2.setState(TranslationState.UNVERIFIED);
        translation4_2.setBundle(bundle);
        keyword4.addTranslation(translation4_2);
        Translation translation4_3 = new Translation();
        translation4_3.setLanguage(chinese);
        translation4_3.setValue("some more test");
        translation4_3.setCountry(singapore);
        translation4_3.setState(TranslationState.UNVERIFIED);
        translation4_3.setBundle(bundle);
        keyword4.addTranslation(translation4_3);
        keywordService.saveOrUpdate(keyword4);
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
//        Translation translation = new Translation();
//        translation.setValue("test");
//        translation.setState(TranslationState.UNVERIFIED);
//        keyword.addTranslation(translation);
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, 2);
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(2, keywords.size());
        assertEquals(keyword1, keywords.get(0));
        assertEquals(keyword2, keywords.get(1));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByKeywordEmptyString()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, Order.asc, 0, null);
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(4, keywords.size());
        assertEquals(keyword1, keywords.get(0));
        assertEquals(keyword2, keywords.get(1));
        assertEquals(keyword3, keywords.get(2));
        assertEquals(keyword4, keywords.get(3));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsOrderDecending()
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("");
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, Order.desc, 0, null);
        assertEquals(4, keywords.getMaxListSize());
        assertEquals(4, keywords.size());
        assertEquals(keyword4, keywords.get(0));
        assertEquals(keyword3, keywords.get(1));
        assertEquals(keyword2, keywords.get(2));
        assertEquals(keyword1, keywords.get(3));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByTranslationValue()
    {
        Translation translation1 = new Translation();
        translation1.setValue("test");
        translation1.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation1);
        Translation translation2 = new Translation();
        translation2.setValue("testing");
        translation2.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation2);
        keywordService.saveOrUpdate(keyword3);
        Translation translation3 = new Translation();
        translation3.setValue("Street");
        translation3.setState(TranslationState.VERIFIED);
        keyword4.addTranslation(translation3);
        keywordService.saveOrUpdate(keyword4);

        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setState(null);
        translation.setValue("st");
        keyword.addTranslation(translation);
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, null, null);
        assertEquals(2, keywords.getMaxListSize());
        assertEquals(2, keywords.size());
        assertEquals(keyword3, keywords.get(0));
        assertEquals(keyword4, keywords.get(1));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByBundle()
    {
        Translation translation1 = new Translation();
        translation1.setLanguage(english);
        translation1.setValue("test");
        translation1.setState(TranslationState.UNVERIFIED);
        translation1.setBundle(bundle);
        keyword3.addTranslation(translation1);
        Translation translation2 = new Translation();
        translation2.setLanguage(english);
        translation2.setCountry(australia);
        translation2.setValue("test");
        translation2.setState(TranslationState.UNVERIFIED);
        translation2.setBundle(bundle);
        keyword3.addTranslation(translation2);
        keywordService.saveOrUpdate(keyword3);

        Translation translation3 = new Translation();
        translation3.setLanguage(english);
        translation3.setValue("test");
        translation3.setState(TranslationState.QUERIED);
        keyword4.addTranslation(translation3);
        keywordService.saveOrUpdate(keyword4);

        Keyword keyword = new Keyword();
        Translation translation = new Translation();
        translation.setBundle(bundle);
        keyword.addTranslation(translation);
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, 0, null);
        assertEquals(1, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword3, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByCountry()
    {
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
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, null, 1);
        assertEquals(1, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword4, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsByTranslationState()
    {
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
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, false, null, null, null);
        assertEquals(1, keywords.getMaxListSize());
        assertEquals(1, keywords.size());
        assertEquals(keyword1, keywords.get(0));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#findKeywords(Keyword, boolean, Order, Integer, Integer)}.
     */
    @Test
    public final void testFindKeywordsWithEmptyKeyword()
    {
        Keyword keyword = new Keyword();
        keyword.addTranslation(new Translation());
        final PaginatedList<Keyword> keywords = 
            keywordService.findKeywords(keyword, true, null, 0, null);
        assertTrue(keywords.isEmpty());
        assertEquals(0, keywords.getMaxListSize());
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeyword()
    {
        Translation translation = new Translation();
        translation.setLanguage(chinese);
        translation.setValue("&#20320;&#22909;");
        translation.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation);
        keywordService.saveOrUpdate(keyword2);

        final Long keywordId = keyword2.getId();
        keywordService.deleteKeyword(keywordId);

        assertNull(keywordService.getKeyword(keywordId));

        // TODO: check that translations are deleted as well
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test
    public final void testDeleteKeywordWithoutTranslations()
    {
        final Long keywordId = keyword1.getId();
        keywordService.deleteKeyword(keywordId);

        assertNull(keywordService.getKeyword(keywordId));
    }

    /**
     * Test method for
     * {@link org.tonguetied.keywordmanagement.KeywordServiceImpl#deleteKeyword(Long)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testDeleteKeywordInvalidId()
    {
        try
        {
            keywordService.deleteKeyword(-1L);
        }
        catch (IllegalArgumentException iae)
        {

        }
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }

    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }
}
