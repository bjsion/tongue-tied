package org.tonguetied.web;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tonguetied.datatransfer.common.ExportParameters;


/**
 * Validator for the {@link ExportParameters} object.
 * 
 * @author bsion
 *
 */
public class ExportParametersValidator implements Validator {

    static final String FIELD_COUNTRIES = "countries";
    static final String FIELD_BUNDLES = "bundles";
    static final String FIELD_LANGUAGES = "languages";
    static final String FIELD_FORMAT_TYPE = "formatType";

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class klass) {
        return ExportParameters.class.isAssignableFrom(klass);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        ExportParameters parameters = (ExportParameters) target;
        if (parameters.getFormatType() == null) {
            errors.rejectValue(FIELD_FORMAT_TYPE, "error.export.type.required");
        }
        if (CollectionUtils.isEmpty(parameters.getLanguages())) {
            errors.rejectValue(FIELD_LANGUAGES, "error.language.selection");
        }
        if (CollectionUtils.isEmpty(parameters.getBundles())) {
            errors.rejectValue(FIELD_BUNDLES, "error.bundle.selection");
        }
        if (CollectionUtils.isEmpty(parameters.getCountries())) {
            errors.rejectValue(FIELD_COUNTRIES, "error.country.selection");
        }
    }
}
