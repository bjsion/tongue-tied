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
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class LanguageValidatorTest {
    private KeywordService keywordService;
    private Language language;
    private String fieldName;

    private static final String FIELD_CODE = "code";
    private static final String FIELD_NAME = "name";
    
    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {LanguageCode.an, null, FIELD_NAME},
                {LanguageCode.oj, "", FIELD_NAME},
                {LanguageCode.ur, "    ", FIELD_NAME},
                {LanguageCode.fi, "Finnish", FIELD_CODE},
                {LanguageCode.fi, "", FIELD_CODE},
                {null, "Korean", FIELD_CODE}
                });
    }
    
    public LanguageValidatorTest(final LanguageCode code, 
            final String name, 
            final String fieldName) 
    {
        this.language = new Language();
        this.language.setCode(code);
        this.language.setName(name);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup() {
        this.keywordService = new KeywordServiceStub();
        Language existing = new Language();
        existing.setId(1256L);
        existing.setCode(LanguageCode.fi);
        existing.setName("Finnish");
        this.keywordService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.LanguageValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        LanguageValidator validator = new LanguageValidator();
        validator.setKeywordService(keywordService);
        Errors errors = new BindException(this.language, "language");
        validator.validate(this.language, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NAME.equals(fieldName)) {
            assertEquals(this.language.getName(), error.getRejectedValue());
        }
        else if (FIELD_CODE.equals(fieldName)) {
            assertEquals(this.language.getCode(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
