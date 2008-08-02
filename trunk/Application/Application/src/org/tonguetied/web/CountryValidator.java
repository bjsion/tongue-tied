package org.tonguetied.web;

import static org.tonguetied.web.Constants.FIELD_CODE;
import static org.tonguetied.web.Constants.FIELD_NAME;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * Validator for the {@link Country} object.
 * 
 * @author bsion
 *
 */
public class CountryValidator implements Validator {
    
    private KeywordService keywordService;

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return Country.class.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        validateMandatoryFields((Country) target, errors);
        validateDuplicates((Country) target, errors);
    }
    
    /**
     * This validation method check if the all mandatory fields on a 
     * {@link Country} object have been set.
     * 
     * @param country the {@link Country} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateMandatoryFields(Country country, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_NAME, "error.country.name.required", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_CODE, "error.country.code.required", null, "default");
    }
    
    /**
     * This validation method checks if a {@link Country} object already exists 
     * in persistence with the same business key ({@link CountryCode}).
     * 
     * @param country the {@link Country} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(Country country, Errors errors) {
        // check for duplicates of new records only
        if (country.getId() == null) {
            Country other = keywordService.getCountry(country.getCode());
            if (other != null) {
                errors.rejectValue(
                        FIELD_CODE, 
                        "error.country.already.exists", 
                        new String[] {country.getCode().name()}, 
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
