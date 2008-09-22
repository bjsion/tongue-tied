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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordFactory;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


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
        this.bundle.setDefault(true);
        
        this.languages = new ArrayList<Language>();
        this.languages.add(japanese);
        this.languages.add(vietnamese);
        this.languages.add(french);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordFactory#createKeyword(java.util.List, org.tonguetied.keywordmanagement.Country, Bundle)}.
     */
    @Test
    public final void testConstructorWithListOfLanguages() {
        Keyword keyword = KeywordFactory.createKeyword(languages, japan, bundle);
        assertEquals(languages.size(), keyword.getTranslations().size());
        for (Translation translation: keyword.getTranslations()) {
            assertTrue(languages.contains(translation.getLanguage()));
            assertEquals(japan, translation.getCountry());
            assertEquals(bundle, translation.getBundle());
            assertEquals(TranslationState.UNVERIFIED, translation.getState());
        }
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordFactory#createKeyword(java.util.List, org.tonguetied.keywordmanagement.Country, Bundle)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testConstructorWithListOfNullLanguages() {
        KeywordFactory.createKeyword(null, vietnam, bundle);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordFactory#createKeyword(java.util.List, org.tonguetied.keywordmanagement.Language, Bundle)}.
     */
    @Test
    public final void testConstructorWithListOfCountries() {
        Keyword keyword = KeywordFactory.createKeyword(countries, vietnamese, null);
        assertEquals(countries.size(), keyword.getTranslations().size());
        for (Translation translation: keyword.getTranslations()) {
            assertTrue(countries.contains(translation.getCountry()));
            assertEquals(vietnamese, translation.getLanguage());
            assertEquals(TranslationState.UNVERIFIED, translation.getState());
        }
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.KeywordFactory#createKeyword(java.util.List, org.tonguetied.keywordmanagement.Language, Bundle)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testConstructorWithListOfNullCountries() {
        KeywordFactory.createKeyword(null, french, null);
    }
    
}
