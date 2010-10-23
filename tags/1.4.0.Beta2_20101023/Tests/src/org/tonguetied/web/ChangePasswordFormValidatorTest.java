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
import static org.tonguetied.web.ChangePasswordFormValidator.FIELD_NEW_PASSWORD;
import static org.tonguetied.web.ChangePasswordFormValidator.FIELD_NEW_REPEATED_PASSWORD;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ChangePasswordFormValidatorTest {
    private ChangePasswordForm form;
    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"valid", "newpassword", "different", FIELD_NEW_REPEATED_PASSWORD},
                {"valid", null, null, FIELD_NEW_PASSWORD},
                {"valid", "", "", FIELD_NEW_PASSWORD},
                });
    }
    
    public ChangePasswordFormValidatorTest(final String oldPassword,
                            final String newPassword, 
                            final String newRepeatedPassword, 
                            final String fieldName) 
    {
        this.form = new ChangePasswordForm();
        this.form.setNewPassword(newPassword);
        this.form.setNewRepeatedPassword(newRepeatedPassword);
        this.form.setOldPassword(oldPassword);
        this.fieldName = fieldName;
    }
    
    /**
     * Test method for {@link org.tonguetied.web.ChangePasswordFormValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        ChangePasswordFormValidator validator = new ChangePasswordFormValidator();
        Errors errors = new BindException(this.form, "changePasswordForm");
        validator.validate(this.form, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NEW_PASSWORD.equals(fieldName)) {
            assertEquals(this.form.getNewPassword(), error.getRejectedValue());
        }
        if (FIELD_NEW_REPEATED_PASSWORD.equals(fieldName)) {
            assertEquals(this.form.getNewRepeatedPassword(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
