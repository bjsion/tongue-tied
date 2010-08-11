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
package org.tonguetied.web;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.web.SearchForm;

/**
 * @author bsion
 *
 */
public class SearchFormTest {
    private Language language;
    private Country country;
    private Bundle bundle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.bundle = new Bundle();
        this.bundle.setName("bundle");
        
        this.language = new Language();
        this.language.setCode(LanguageCode.pt);
        this.language.setName("Portuguese");
        
        this.country = new Country();
        this.country.setCode(CountryCode.BR);
        this.country.setName("Brasil");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.tonguetied.web.SearchForm#initialize()}.
     */
    @Test
    public void testInitialize() {
        SearchForm searchForm = new SearchForm();
        searchForm.setKeywordKey("keywordKey");
        searchForm.setIgnoreCase(false);
        searchForm.setBundle(bundle);
        searchForm.setCountry(country);
        searchForm.setLanguage(language);
        searchForm.setTranslationState(TranslationState.QUERIED);
        searchForm.setTranslatedText("translatedText");
        
        searchForm.initialize();
        
        assertNull(searchForm.getKeywordKey());
        assertNull(searchForm.getBundle());
        assertNull(searchForm.getCountry());
        assertNull(searchForm.getLanguage());
        assertNull(searchForm.getTranslatedText());
        assertNull(searchForm.getTranslationState());
        assertTrue(searchForm.getIgnoreCase());
    }

}
