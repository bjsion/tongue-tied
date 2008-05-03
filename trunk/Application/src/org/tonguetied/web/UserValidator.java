package org.tonguetied.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;


/**
 * Validator for the {@link User} object.
 * 
 * @author bsion
 *
 */
public class UserValidator implements Validator {

    private UserService userService;

    //Set the email pattern string
    private static final Pattern emailPattern = 
        Pattern.compile(".+@.+\\.[a-z]+");
    
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        validateMandatoryFields(user, errors);
        validateDuplicates(user, errors);
        validateEmail(user.getEmail(), errors);
    }
    
    /**
     * This validation method check if the all mandatory fields on a 
     * {@link User} object have been set.
     * 
     * @param user the {@link User} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateMandatoryFields(final User user, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "username", "error.username.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "firstName", "error.first.name.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "lastName", "error.last.name.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "email", "error.email.required", null, "default");
    }
    
    /**
     * This validation method checks if a {@link User} object already exists 
     * in persistence with the same business key ({@link User#getUserName()}).
     * 
     * @param user the {@link User} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(final User user, Errors errors) {
        // check for duplicates of new records only
        if (user.getId() == null) {
            User other = userService.getUser(user.getUsername());
            if (other != null) {
                errors.rejectValue(
                        "username", 
                        "error.user.already.exists",
                        new String[] {user.getUsername()},
                        "default");
            }
        }
    }
    
    /**
     * This validation method checks that an email string confirms to a basic
     * structure or format. The basic format is "xxx@yyy.zzz"
     *  
     * @param email the email string to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateEmail(final String email, Errors errors) {
        if (email != null) {
            //Match the given string with the pattern
            Matcher m = emailPattern.matcher(email);
            if (!m.matches())
                errors.rejectValue("email", "error.invalid.email");
        }
    }

    /**
     * Set the {@link UserService} instance.
     * 
     * @param userService the {@link UserService} instance.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
