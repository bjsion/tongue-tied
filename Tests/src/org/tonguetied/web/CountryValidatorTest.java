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
import static org.tonguetied.web.Constants.FIELD_CODE;
import static org.tonguetied.web.Constants.FIELD_NAME;

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
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * Test the input validation of the {@link Country} object.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class CountryValidatorTest
{
    private KeywordService keywordService;
    private Country country;
    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {null, CountryCode.FJ, null, FIELD_NAME},
                {null, CountryCode.RE, "", FIELD_NAME},
                {null, CountryCode.RE, "    ", FIELD_NAME},
                {null, CountryCode.PL, "Poland", FIELD_CODE},
                {1257L, CountryCode.PL, "Austria", FIELD_CODE},
                {null, null, "Korea", FIELD_CODE}
                });
    }
    
    public CountryValidatorTest(final Long id,
            final CountryCode code, 
            final String name, 
            final String fieldName) 
    {
        this.country = new Country();
        this.country.setId(id);
        this.country.setCode(code);
        this.country.setName(name);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup() {
        this.keywordService = new KeywordServiceStub();
        Country existing = new Country();
        existing.setId(1256L);
        existing.setCode(CountryCode.PL);
        existing.setName("Poland");
        this.keywordService.saveOrUpdate(existing);
        existing = new Country();
        existing.setId(1257L);
        existing.setCode(CountryCode.AT);
        existing.setName("Austria");
        this.keywordService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.CountryValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        CountryValidator validator = new CountryValidator();
        validator.setKeywordService(keywordService);
        Errors errors = new BindException(this.country, "country");
        validator.validate(this.country, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NAME.equals(fieldName)) {
            assertEquals(this.country.getName(), error.getRejectedValue());
        }
        else if (FIELD_CODE.equals(fieldName)) {
            assertEquals(this.country.getCode(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
