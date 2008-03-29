package org.tonguetied.web;

import static org.tonguetied.web.Constants.FIELD_NAME;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.KeywordService;


/**
 * Validator for the {@link Bundle} object.
 * 
 * @author bsion
 *
 */
public class BundleValidator implements Validator {

    private KeywordService keywordService;

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
                errors, FIELD_NAME, "errorBundleNameRequired", null, "default");
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
            Bundle other = keywordService.getBundle(bundle.getName());
            if (other != null) {
                errors.rejectValue(
                        FIELD_NAME, 
                        "errorBundleAlreadyExists",
                        new String[] {bundle.getName()},
                        "default");
            }
        }
    }

    /**
     * Set the {@link KeywordService} instance.
     * 
     * @param keywordService the keywordService instance.
     */
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }
}
