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
import static org.tonguetied.web.KeywordValidator.FIELD_KEYWORD;
import static org.tonguetied.web.KeywordValidator.FIELD_TRANSLATIONS;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
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
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
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
public class KeywordValidatorTest
{
    private KeywordService keywordService;
    private Keyword keyword;
    private String fieldName;

    private static Country country;
    private static Language language;
    private static Bundle bundle;
    
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
    }
    
    @Parameters
    public static final Collection<Object[]> data()
    {
        Translation translation1 = new Translation();
        translation1.setBundle(bundle);
        translation1.setCountry(country);
        translation1.setLanguage(language);
        translation1.setState(TranslationState.QUERIED);
        translation1.setValue(null);

        Translation translation2 = new Translation();
        translation2.setBundle(bundle);
        translation2.setCountry(country);
        translation2.setLanguage(language);
        translation2.setState(TranslationState.VERIFIED);
        translation2.setValue("value");

        Translation translation3 = new Translation();
        translation3.setState(TranslationState.VERIFIED);
        Translation translation4 = new Translation();
        
        return Arrays.asList(new Object[][] {
                {null, null, "context", null, FIELD_KEYWORD},
                {null, "", "context", new Translation[] {translation1}, FIELD_KEYWORD},
                {null, "   ", null, new Translation[] {translation1}, FIELD_KEYWORD},
                {null, "keyword", "context", new Translation[] {translation1}, FIELD_KEYWORD},
                {null, "keyword2", "", new Translation[] {translation1, translation2}, FIELD_TRANSLATIONS},
                // This tests tests for the case when trying to change an existing keyword to the value of another existing keyword
                {1000L, "keyword", "context", new Translation[] {translation1}, FIELD_KEYWORD},
                // This test tests for null values in use in the bundle, country and language
                {null, "keyword3", null, new Translation[] {translation3, translation4}, FIELD_TRANSLATIONS},
                });
    }
    
    public KeywordValidatorTest(final Long id,
            final String keywordStr, 
            final String context, 
            final Translation[] translations, 
            final String fieldName)
    {
        this.keyword = new Keyword();
        this.keyword.setId(id);
        this.keyword.setKeyword(keywordStr);
        this.keyword.setContext(context);
        if (translations != null)
        {
            for (Translation translation : translations)
            {
                this.keyword.addTranslation(translation);
            }
        }
        this.fieldName = fieldName;
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.keywordService = new KeywordServiceStub();
        
        Translation expectedTranslation = new Translation();
        expectedTranslation.setCountry(country);
        expectedTranslation.setLanguage(language);
        expectedTranslation.setBundle(bundle);
        
        Keyword existing = new Keyword();
        existing.setId(1487L);
        existing.setKeyword("keyword");
        existing.setContext("context");
        existing.addTranslation(expectedTranslation);
        this.keywordService.saveOrUpdate(existing);
    }

    /**
     * Test method for {@link org.tonguetied.web.KeywordValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidate()
    {
        KeywordValidator validator = new KeywordValidator();
        validator.setKeywordService(keywordService);
        Errors errors = new BindException(this.keyword, "keyword");
        validator.validate(this.keyword, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_KEYWORD.equals(fieldName))
        {
            assertEquals(this.keyword.getKeyword(), error.getRejectedValue());
        }
        else if (FIELD_TRANSLATIONS.equals(fieldName))
        {
            assertEquals(this.keyword.getTranslations(), error.getRejectedValue());
            assertEquals(1, errors.getErrorCount());
        }
        else
        {
            fail("cannot match error field");
        }
        assertFalse(error.isBindingFailure());
    }
}
