package org.tonguetied.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.domain.Bundle;
import org.tonguetied.service.ApplicationService;


/**
 * Validator for the {@link Bundle} object.
 * 
 * @author bsion
 *
 */
public class BundleValidator implements Validator {

    private ApplicationService appService;

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return Bundle.class.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        validateMandatoryFields((Bundle) target, errors);
        validateDuplicates((Bundle) target, errors);
    }
    
    /**
     * This validation method check if the all mandatory fields on a 
     * {@link Bundle} object have been set.
     * 
     * @param bundle the {@link Bundle} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateMandatoryFields(Bundle bundle, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "name", "errorBundleNameRequired", null, "default");
    }
    
    /**
     * This validation method checks if a {@link Bundle} object already exists 
     * in persistence with the same business key ({@link Bundle#getName()}).
     * 
     * @param bundle the {@link Bundle} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(Bundle bundle, Errors errors) {
        // check for duplicates of new records only
        if (bundle.getId() == null) {
            Bundle other = appService.getBundle(bundle.getName());
            if (other != null) {
                errors.rejectValue(
                        "name", 
                        "errorBundleAlreadyExists",
                        new String[] {bundle.getName()},
                        "default");
            }
        }
    }

    /**
     * Set the {@link ApplicationService} instance.
     * 
     * @param appService the appService instance.
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }
}
