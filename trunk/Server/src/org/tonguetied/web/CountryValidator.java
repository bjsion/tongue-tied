package org.tonguetied.web;

import static org.tonguetied.web.Constants.FIELD_CODE;
import static org.tonguetied.web.Constants.FIELD_NAME;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.service.ApplicationService;


/**
 * Validator for the {@link Country} object.
 * 
 * @author bsion
 *
 */
public class CountryValidator implements Validator {
    
    private ApplicationService appService;

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
                errors, FIELD_NAME, "errorCountryNameRequired", null, "default");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, FIELD_CODE, "errorCountryCodeRequired", null, "default");
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
            Country other = appService.getCountry(country.getCode());
            if (other != null) {
                errors.rejectValue(
                        FIELD_CODE, 
                        "errorCountryAlreadyExists", 
                        new String[] {country.getCode().name()}, 
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
