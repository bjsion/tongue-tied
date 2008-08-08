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
