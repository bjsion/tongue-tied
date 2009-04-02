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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.tonguetied.web.KeywordValidator.FIELD_TRANSLATIONS;

import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.TranslationPredicate;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Test the input validation of the {@link Keyword} object.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class KeywordTranslationValidatorTest
{
    private String fieldName;
    private TranslationPredicate predicate;

    private final static Country country;
    private final static Language language;
    private final static Bundle bundle;
    private final static SortedSet<Translation> translations = new TreeSet<Translation>();
    
    static
    {
        country = new Country();
        country.setCode(CountryCode.JP);
        country.setName("Japan");
        
        language = new Language();
        language.setCode(LanguageCode.ja);
        language.setName("Japanese");
        
        bundle = new Bundle();
        bundle.setName("bundle");

        Translation translation1 = new Translation();
        translation1.setBundle(bundle);
        translation1.setCountry(country);
        translation1.setLanguage(language);
        translation1.setState(TranslationState.QUERIED);
        translation1.setValue(null);

        Translation translation2 = new Translation();
        
        translations.add(translation1);
        translations.add(translation2);

    }
    
    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {null, null, null, FIELD_TRANSLATIONS},
                {bundle, country, language, FIELD_TRANSLATIONS}
                });
    }
    
    public KeywordTranslationValidatorTest(final Bundle bundle,
            final Country country,
            final Language language,
            final String fieldName)
    {
        this.predicate = new TranslationPredicate(bundle, country, language);
        this.fieldName = fieldName;
    }
    
    /**
     * Test method for {@link org.tonguetied.web.KeywordValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidate()
    {
        KeywordValidator validator = new KeywordValidator();
        final Keyword keyword = new Keyword();
        keyword.setTranslations(translations);
        Errors errors = new BindException(keyword, "keyword");
        validator.validateDuplicates(translations, predicate, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_TRANSLATIONS.equals(fieldName))
        {
            assertEquals(translations, error.getRejectedValue());
            assertEquals(1, errors.getErrorCount());
        }
        else
        {
            fail("cannot match error field");
        }
        assertFalse(error.isBindingFailure());
    }
}
