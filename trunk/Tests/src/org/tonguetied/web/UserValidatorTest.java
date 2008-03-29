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
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;
import org.tonguetied.usermanagement.UserServiceStub;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class UserValidatorTest {
    private UserService userService;
    private User user;
    private String fieldName;

    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_FIRSTNAME = "firstName";
    private static final String FIELD_LASTNAME = "lastName";
    private static final String FIELD_USERNAME = "username";
    
    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
//                {"username", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {"", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {"   ", "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {null, "password", "firstName", "lastName", "test@test.com", true, FIELD_USERNAME},
                {"username", "password", "", "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {"username", "password", "   ", "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {"username", "password", null, "lastName", "test@test.com", true, FIELD_FIRSTNAME},
                {"username", "password", "firstName", "", "test@test.com", true, FIELD_LASTNAME},
                {"username", "password", "firstName", "   ", "test@test.com", true, FIELD_LASTNAME},
                {"username", "password", "firstName", null, "test@test.com", true, FIELD_LASTNAME},
                {"username", "password", "firstName", "lastName", "", true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", "\t\t", true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", null, true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", "email", true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", "test@email", true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", "test.com", true, FIELD_EMAIL},
                {"username", "password", "firstName", "lastName", "@.com", true, FIELD_EMAIL},
                {"existing", "existing", "existing", "existing", "test@test.com", true, FIELD_USERNAME},
                });
    }
    
    public UserValidatorTest(String username,
                            String password, 
                            String firstName, 
                            String lastName, 
                            String email, 
                            boolean isEnabled,
                            String fieldName) {
        this.user = new User(username, password, firstName, lastName, email, isEnabled, true, true, true);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup() {
        this.userService = new UserServiceStub();
        User existing = new User("existing", "existing", "existing", "existing", "test@test.com", true, true, true, true);
        this.userService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.UserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        UserValidator validator = new UserValidator();
        validator.setUserService(userService);
        Errors errors = new BindException(this.user, "user");
        validator.validate(this.user, errors);
        
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
        assertFalse(error.isBindingFailure());
    }

}
