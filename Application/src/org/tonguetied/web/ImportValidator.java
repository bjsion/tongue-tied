package org.tonguetied.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.datatransfer.common.ImportParameters;

/**
 * Class used to verify the user input of data entered for the import feature.
 * 
 * @author bsion
 * 
 */
public class ImportValidator implements Validator
{

    static final String FIELD_FILE_NAME = "fileUploadBean.file.name";
    static final String FIELD_FORMAT_TYPE = "parameters.formatType";
    static final String FIELD_TRANSLATION_STATE = "parameters.translationState";

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class klass)
    {
        return ImportBean.class.isAssignableFrom(klass);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors)
    {
        ImportBean importBean = (ImportBean) target;
        validate(importBean.getFileUploadBean(), errors);
        validate(importBean.getParameters(), errors);
    }

    /**
     * Validate the {@link ImportParameters} of the {@link ImportBean} for
     * erroneous input.
     * 
     * @param parameters the import parameters to validate
     * @param errors the errors object
     */
    private void validate(ImportParameters parameters, Errors errors)
    {
        ValidationUtils.rejectIfEmpty(errors, FIELD_FORMAT_TYPE,
                "error.import.type.required");
        ValidationUtils.rejectIfEmpty(errors, FIELD_TRANSLATION_STATE,
                "error.import.state.required");
    }

    /**
     * Validate the {@link FileUploadBean} of the {@link ImportBean} for
     * erroneous input.
     * 
     * @param fileUploadBean the object container of the file to validate
     * @param errors the errors object
     */
    private void validate(FileUploadBean fileUploadBean, Errors errors)
    {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                FIELD_FILE_NAME, fileUploadBean.getFile().getName());
    }
}
