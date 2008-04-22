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
            errors.rejectValue("formatType", "errorExportTypeRequired");
        }
        if (CollectionUtils.isEmpty(parameters.getLanguages())) {
            errors.rejectValue("languages", "errorLanguageSelection");
        }
        if (CollectionUtils.isEmpty(parameters.getBundles())) {
            errors.rejectValue("bundles", "errorBundleSelection");
        }
        if (CollectionUtils.isEmpty(parameters.getCountries())) {
            errors.rejectValue("countries", "errorCountrySelection");
        }
    }
}
