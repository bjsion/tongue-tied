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
import static org.tonguetied.web.Constants.FIELD_NAME;
import static org.tonguetied.web.Constants.FIELD_RESOURCE_NAME;

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
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;


/**
 * Test the input validation of the {@link Bundle} object.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class BundleValidatorTest {
    private KeywordService keywordService;
    private Bundle bundle;
    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {null, null, null, "resource", true, false, FIELD_NAME},
                {null, "", null, "resource", false, false, FIELD_NAME},
                {null, "   ", null, "resource", true, true, FIELD_NAME},
                {null, "test", "description", "resource", true, false, FIELD_NAME},
                {1257L, "test", "descriptiond", "test2", true, false, FIELD_NAME},
                {null, "new", "description", null, true, false, FIELD_RESOURCE_NAME},
                {null, "new", "description", "", true, false, FIELD_RESOURCE_NAME},
                {null, "new", "description", "    ", true, false, FIELD_RESOURCE_NAME},
                {null, "new", "description", "test", true, false, FIELD_RESOURCE_NAME},
                {1257L, "test2", "description", "test", true, false, FIELD_RESOURCE_NAME},
                {null, "new", "description", "has whitespace", true, false, FIELD_RESOURCE_NAME},
//              {"new", "description", "has\nwhitespace", true, false, FIELD_RESOURCE_NAME},
                {null, "new", "description", "has\twhitespace", true, false, FIELD_RESOURCE_NAME}
                });
    }
    
    public BundleValidatorTest(final Long id,
                               final String name,
                               final String description,
                               final String resourceName,
                               final boolean isDefault,
                               final boolean isGlobal,
                               final String fieldName) 
    {
        this.bundle = new Bundle();
        this.bundle.setId(id);
        this.bundle.setName(name);
        this.bundle.setDescription(description);
        this.bundle.setResourceName(resourceName);
        this.bundle.setDefault(isDefault);
        this.bundle.setGlobal(isGlobal);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setUp() {
        this.keywordService = new KeywordServiceStub();
        Bundle existing = new Bundle();
        existing.setId(1256L);
        existing.setName("test");
        existing.setResourceName("test");
        existing.setDescription("description");
        this.keywordService.saveOrUpdate(existing);
        existing = new Bundle();
        existing.setId(1257L);
        existing.setName("test2");
        existing.setResourceName("test2");
        existing.setDescription("description2");
        this.keywordService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.BundleValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        BundleValidator validator = new BundleValidator();
        validator.setKeywordService(keywordService);
        Errors errors = new BindException(this.bundle, "bundle");
        validator.validate(this.bundle, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NAME.equals(fieldName)) {
            assertEquals(this.bundle.getName(), error.getRejectedValue());
        }
        else if (FIELD_RESOURCE_NAME.equals(fieldName)) {
            assertEquals(this.bundle.getResourceName(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
