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
import static org.tonguetied.usermanagement.User.FIELD_EMAIL;
import static org.tonguetied.usermanagement.User.FIELD_FIRSTNAME;
import static org.tonguetied.usermanagement.User.FIELD_LASTNAME;

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
 * Unit tests for the {@link org.tonguetied.web.AccountFormValidator} class.
 * 
 * @author bsion
 * 
 */
@RunWith(value = Parameterized.class)
public class AccountFormValidatorTest
{
    private AccountForm user;
    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                // {"firstName", "lastName", "test@test.com", FIELD_USERNAME},
                { "", "lastName", "test@test.com", FIELD_FIRSTNAME },
                { "   ", "lastName", "test@test.com", FIELD_FIRSTNAME },
                { null, "lastName", "test@test.com", FIELD_FIRSTNAME },
                { "firstName", "", "test@test.com", FIELD_LASTNAME },
                { "firstName", "   ", "test@test.com", FIELD_LASTNAME },
                { "firstName", null, "test@test.com", FIELD_LASTNAME },
                { "firstName", "lastName", "", FIELD_EMAIL },
                { "firstName", "lastName", "\t\t", FIELD_EMAIL },
                { "firstName", "lastName", null, FIELD_EMAIL },
                { "firstName", "lastName", "email", FIELD_EMAIL },
                { "firstName", "lastName", "test@email", FIELD_EMAIL },
                { "firstName", "lastName", "test.com", FIELD_EMAIL },
                { "firstName", "lastName", "@.com", FIELD_EMAIL }, });
    }

    public AccountFormValidatorTest(final String firstName,
            final String lastName, final String email, final String fieldName)
    {
        this.user = new AccountForm(firstName, lastName, email);
        this.fieldName = fieldName;
    }

    /**
     * Test method for
     * {@link org.tonguetied.web.AccountFormValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject()
    {
        AccountFormValidator validator = new AccountFormValidator();
        Errors errors = new BindException(this.user, "user");
        validator.validate(this.user, errors);

        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_FIRSTNAME.equals(fieldName))
        {
            assertEquals(this.user.getFirstName(), error.getRejectedValue());
        }
        if (FIELD_LASTNAME.equals(fieldName))
        {
            assertEquals(this.user.getLastName(), error.getRejectedValue());
        }
        if (FIELD_EMAIL.equals(fieldName))
        {
            assertEquals(this.user.getEmail(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
