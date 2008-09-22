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
import static org.junit.Assert.assertTrue;
import static org.tonguetied.usermanagement.User.FIELD_EMAIL;
import static org.tonguetied.usermanagement.User.FIELD_FIRSTNAME;
import static org.tonguetied.usermanagement.User.FIELD_LASTNAME;
import static org.tonguetied.usermanagement.User.FIELD_PASSWORD;
import static org.tonguetied.usermanagement.User.FIELD_REPEATED_PASSWORD;
import static org.tonguetied.usermanagement.User.FIELD_USERNAME;

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
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;
import org.tonguetied.usermanagement.UserServiceStub;


/**
 * Unit tests for the {@link UserValidator} class.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class UserValidatorTest
{
    private UserService userService;
    private User user;
    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
//                {null, "username", "password", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {null, "", "password", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {null, "   ", "password", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {null, null, "password", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {null, "username", "password", "password", "", "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {null, "username", "password", "password", "   ", "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {null, "username", "password", "password", null, "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {null, "username", "password", "password", "firstName", "", "test@test.com", true, FIELD_LASTNAME},
                {null, "username", "password", "password", "firstName", "   ", "test@test.com", true, FIELD_LASTNAME},
                {null, "username", "password", "password", "firstName", null, "test@test.com", true, FIELD_LASTNAME},
                {null, "username", "password", "password", "firstName", "lastName", "", true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", "\t\t", true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", null, true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", "email", true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", "test@email", true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", "test.com", true, FIELD_EMAIL},
                {null, "username", "password", "password", "firstName", "lastName", "@.com", true, FIELD_EMAIL},
                {null, "existing", "existing", "existing", "existing", "existing", "test@test.com", true, FIELD_USERNAME},
                {1L, "existing", "existing", "existing", "existing", "existing", "test@test.com", true, null},
                {null, "username", null, "password", "firstName", "lastName", "test@test.com", true, FIELD_PASSWORD},
                {null, "username", null, null, "firstName", "lastName", "test@test.com", true, FIELD_PASSWORD},
                {null, "username", "password", "different", "firstName", "lastName", "test@test.com", true, FIELD_REPEATED_PASSWORD},
                {1L, "username", "password", "different", "firstName", "lastName", "test@test.com", true, null},
                {1L, "username", null, "password", "firstName", "lastName", "test@test.com", true, null},
                });
    }
    
    public UserValidatorTest(final Long id,
                            final String username,
                            final String password,
                            final String repeatedPassword,
                            final String firstName, 
                            final String lastName, 
                            final String email, 
                            final boolean isEnabled,
                            final String fieldName)
    {
        this.user = new User(username, password, firstName, lastName, email, isEnabled, true, true, true);
        this.user.setId(id);
        this.user.setRepeatedPassword(repeatedPassword);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup()
    {
        this.userService = new UserServiceStub();
        User existing = new User("existing", "existing", "existing", "existing", "test@test.com", true, true, true, true);
        this.userService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.UserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject()
    {
        UserValidator validator = new UserValidator();
        validator.setUserService(userService);
        Errors errors = new BindException(this.user, "user");
        validator.validate(this.user, errors);
        
        if (fieldName == null)
        {
            assertTrue(errors.getAllErrors().isEmpty());
        }
        else
        {
            assertFalse(errors.getAllErrors().isEmpty());
            FieldError error = errors.getFieldError(fieldName);
            if (FIELD_USERNAME.equals(fieldName)) {
                assertEquals(this.user.getUsername(), error.getRejectedValue());
            }
            if (FIELD_FIRSTNAME.equals(fieldName)) {
                assertEquals(this.user.getFirstName(), error.getRejectedValue());
            }
            if (FIELD_LASTNAME.equals(fieldName)) {
                assertEquals(this.user.getLastName(), error.getRejectedValue());
            }
            if (FIELD_EMAIL.equals(fieldName)) {
                assertEquals(this.user.getEmail(), error.getRejectedValue());
            }
            if (FIELD_PASSWORD.equals(fieldName)) {
                assertEquals(this.user.getPassword(), error.getRejectedValue());
            }
            if (FIELD_REPEATED_PASSWORD.equals(fieldName)) {
                assertEquals(this.user.getRepeatedPassword(), error.getRejectedValue());
            }
            assertFalse(error.isBindingFailure());
        }
    }
}
