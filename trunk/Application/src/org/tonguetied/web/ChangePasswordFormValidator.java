package org.tonguetied.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for the {@link ChangePasswordForm}.
 * 
 * @author bsion
 *
 */
public class ChangePasswordFormValidator implements Validator 
{
    static final String FIELD_OLD_PASSWORD = "oldPassword";
    static final String FIELD_NEW_PASSWORD = "newPassword";
    static final String FIELD_NEW_REPEATED_PASSWORD = "newRepeatedPassword";

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class klass) {
        return ChangePasswordForm.class.isAssignableFrom(klass);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, FIELD_NEW_PASSWORD, "error.password.empty");
        validateNewPassword((ChangePasswordForm) target, errors);
    }
    
    /**
     * Checks that the retyped new password matches the first value.
     * 
     * @param form the {@link ChangePasswordForm} to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateNewPassword(ChangePasswordForm form, Errors errors)
    {
        if (form.getNewPassword() != null)
        {
            if (!form.getNewPassword().equals(form.getNewRepeatedPassword()))
                errors.rejectValue(FIELD_NEW_REPEATED_PASSWORD, "error.password.mismatch");
        }
    }
}
