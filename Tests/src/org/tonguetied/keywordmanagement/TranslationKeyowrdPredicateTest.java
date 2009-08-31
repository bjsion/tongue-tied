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

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Test class for the {@link TranslationKeywordPredicate} class.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class TranslationKeyowrdPredicateTest
{
    private Translation translation;
    private boolean expected;
    private boolean expectedWithNullBundle;
    private boolean expectedWithNullCountry;
    private boolean expectedWithNullLanguage;
    private String keywordValue;
    
//    private static TranslationPredicate predicate;
    private static Country mexico;
    private static Language spanish;
    private static Bundle bundle1;

    @Parameters
    public static final Collection<Object[]> data()
    {
        mexico = new Country();
        mexico.setCode(CountryCode.MX);
        mexico.setName("Mexico");
        
        Country spain = new Country();
        spain.setCode(CountryCode.ES);
        spain.setName("Spain");
        
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        
        Language basque = new Language();
        basque.setCode(LanguageCode.eu);
        basque.setName("Basque");
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setResourceName("bundle1");
        
        Bundle bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
        
        return Arrays.asList(new Object[][] {
                {null, spanish, mexico, bundle1, false, false, false, false},
                {"", spanish, mexico, bundle1, false, false, false, false},
                {"unknown", spanish, mexico, bundle1, false, false, false, false},
                {"test", spanish, mexico, bundle1, true, false, false, false},
                {"test", null, mexico, bundle1, false, false, false, true},
                {"test", basque, mexico, bundle1, false, false, false, false},
                {"test", spanish, null, bundle1, false, false, true, false},
                {"test", spanish, spain, bundle1, false, false, false, false},
                {"test", spanish, mexico, null, false, true, false, false},
                {"test", spanish, mexico, bundle2, false, false, false, false}
        });
    }

    /**
     * @param language
     * @param country
     * @param bundle
     * @param expected
     * @param expectedWithNullBundle 
     * @param expectedWithNullCountry 
     * @param expectedWithNullLanguage 
     */
    public TranslationKeyowrdPredicateTest(final String keywordValue,
            final Language language,
            final Country country, 
            final Bundle bundle, 
            final boolean expected, 
            final boolean expectedWithNullBundle,
            final boolean expectedWithNullCountry,
            final boolean expectedWithNullLanguage)
    {
        this.expected = expected;
        this.expectedWithNullBundle = expectedWithNullBundle;
        this.expectedWithNullCountry = expectedWithNullCountry;
        this.expectedWithNullLanguage = expectedWithNullLanguage;
        this.translation = new Translation(bundle, country, language, "test", TranslationState.VERIFIED);
        this.keywordValue = keywordValue;
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        keyword.addTranslation(translation);
        this.translation.setKeyword(keyword);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluate()
    {
        final TranslationKeywordPredicate predicate =
            new TranslationKeywordPredicate(keywordValue, bundle1, mexico, spanish);
        final boolean actual = predicate.evaluate(translation);
        assertEquals(expected, actual);
    }
    
    @Test
    public final void testEvaluateWithNullBundle()
    {
        final TranslationKeywordPredicate predicate = 
            new TranslationKeywordPredicate(keywordValue, null, mexico, spanish);
        final boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullBundle, actual);
    }
    
    @Test
    public final void testEvaluateWithNullCountry()
    {
        final TranslationKeywordPredicate predicate = 
            new TranslationKeywordPredicate(keywordValue, bundle1, null, spanish);
        final boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullCountry, actual);
    }
    
    @Test
    public final void testEvaluateWithNullLanguage()
    {
        final TranslationKeywordPredicate predicate = 
            new TranslationKeywordPredicate(keywordValue, bundle1, mexico, null);
        final boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullLanguage, actual);
    }
}
