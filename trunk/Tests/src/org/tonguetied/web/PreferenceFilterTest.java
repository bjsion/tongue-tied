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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
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
@RunWith(value=Parameterized.class)
public class PreferenceFilterTest
{
    private boolean expected;
    private Translation translation;
    
    private PreferenceForm preferences;
    
    private static Country china;
    private static Country newZealand;
    private static Language english;
    private static Language spanish;
    private static Bundle bundle1;
    private static Bundle bundle2;

    /**
     * @param expected
     * @param value
     * @param language
     * @param country
     * @param bundle
     * @param keywordStr
     */
    public PreferenceFilterTest(final boolean expected, 
                                final String value, 
                                final Language language, 
                                final Country country, 
                                final Bundle bundle, 
                                final String keywordStr)
    {
        this.expected = expected;
        if (value == null && keywordStr == null) {
            this.translation = null;
        }
        else {
            this.translation = new Translation();
            translation.setValue(value);
            translation.setLanguage(language);
            translation.setCountry(country);
            translation.setBundle(bundle);
            Keyword keyword = new Keyword();
            keyword.setKeyword(keywordStr);
            keyword.addTranslation(translation);
            translation.setKeyword(keyword);
        }
    }

    @Parameters
    public static final Collection<Object[]> data()
    {
        china = new Country();
        china.setCode(CountryCode.CN);
        china.setName("China");
        newZealand = new Country();
        newZealand.setCode(CountryCode.NZ);
        newZealand.setName("New Zealand");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setDescription("test description");
        bundle1.setResourceName("test");
        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        
        return Arrays.asList(new Object[][] {
                {true, "test1", spanish, newZealand, bundle1, "keyword"},
                {false, "test1", english, newZealand, bundle1, "keyword"},
                {false, "test1", spanish, china, bundle1, "keyword"},
                {false, "test1", spanish, newZealand, bundle2, "keyword"},
                {true, "test1", null, newZealand, bundle1, "keyword"},
                {true, "test1", spanish, null, bundle1, "keyword"},
                {true, "test1", spanish, newZealand, null, "keyword"},
                {false, null, null, null, null, null},
                {true, null, null, null, null, "keyword"}
                });
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        preferences = new PreferenceForm();
        
        List<Language> selectedLanguages = 
            Arrays.asList(new Language[] {spanish});
        List<Country> selectedCountries = 
            Arrays.asList(new Country[] {newZealand});
        List<Bundle> selectedBundles = 
            Arrays.asList(new Bundle[] {bundle1});
        
        preferences.setSelectedLanguages(selectedLanguages);
        preferences.setSelectedCountries(selectedCountries);
        preferences.setSelectedBundles(selectedBundles);
    }

    /**
     * Test method for {@link org.tonguetied.web.PreferenceFilter#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluate()
    {
        PreferenceFilter filter = new PreferenceFilter(preferences);
        
        boolean actual = filter.evaluate(this.translation);
        
        assertEquals(this.expected, actual);
    }
}
