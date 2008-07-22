package org.tonguetied.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for the {@link AccountForm} object.
 * 
 * @author bsion
 *
 */
public class AccountFormValidator implements Validator
{
    static final String FIELD_EMAIL = "email";
    static final String FIELD_FIRSTNAME = "firstName";
    static final String FIELD_LASTNAME = "lastName";

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class klass)
    {
        return AccountForm.class.isAssignableFrom(klass);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors)
    {
        final AccountForm form = (AccountForm) target;
        validateMandatoryFields(form, errors);
        validateEmail(form.getEmail(), errors);
    }

    /**
     * This validation method check if the all mandatory fields on a 
     * {@link AccountForm} object have been set.
     * 
     * @param form the {@link AccountForm} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateMandatoryFields(final AccountForm form, Errors errors)
    {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_FIRSTNAME, "error.first.name.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_LASTNAME, "error.last.name.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_EMAIL, "error.email.required", null, "default");
    }
    
    /**
     * This validation method checks that an email string confirms to a basic
     * structure or format. The basic format is "xxx@yyy.zzz"
     *  
     * @param email the email string to validate
     * @param errors contextual state about the validation process (never null)
     * @see WebValidationUtils#isEmailValid(String)
     */
    private void validateEmail(final String email, Errors errors)
    {
        if (!WebValidationUtils.isEmailValid(email))
        {
            errors.rejectValue(FIELD_EMAIL, "error.invalid.email");
        }
    }
}
