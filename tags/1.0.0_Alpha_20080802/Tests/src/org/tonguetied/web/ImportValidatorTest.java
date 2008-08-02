package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.tonguetied.web.ImportValidator.*;
import static org.tonguetied.web.ImportValidator.FIELD_TRANSLATION_STATE;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * @author bsion
 * 
 */
@RunWith(value = Parameterized.class)
public class ImportValidatorTest
{
    private ImportBean importBean;

    private String fieldName;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {"fileName", null, TranslationState.UNVERIFIED, new byte[] {},
                        FIELD_FORMAT_TYPE },
                {"fileName", FormatType.properties, null, new byte[] {},
                        FIELD_TRANSLATION_STATE },
                {"   ", FormatType.properties, null, new byte[] {},
                    FIELD_TRANSLATION_STATE },
                });
    }

    /**
     * Create a new instance of ImportValidatorTest.
     * 
     * @param fileName
     * @param formatType
     * @param translationState
     * @param data
     * @param fieldName
     */
    public ImportValidatorTest(final String fileName,
            final FormatType formatType,
            final TranslationState translationState, final byte[] data,
            final String fieldName)
    {
        ImportParameters parameters = new ImportParameters();
        parameters.setFileName(fileName);
        parameters.setFormatType(formatType);
        parameters.setTranslationState(translationState);
        this.importBean = new ImportBean();
        this.importBean.setParameters(parameters);
        MultipartFile file = new MockMultipartFile(fileName, data);
        FileUploadBean fileUploadBean = new FileUploadBean();
        fileUploadBean.setFile(file);
        this.importBean.setFileUploadBean(fileUploadBean);
        this.fieldName = fieldName;
    }

    @Before
    public void setup()
    {
    }

    /**
     * Test method for
     * {@link org.tonguetied.web.ImportValidator#validate(Object, Errors)}.
     */
    @Test
    public final void testValidateInvalidObject()
    {
        ImportValidator validator = new ImportValidator();
        Errors errors = new BindException(this.importBean, "importBean");
        validator.validate(this.importBean, errors);

        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_FORMAT_TYPE.equals(fieldName))
        {
            assertEquals(this.importBean.getParameters().getFormatType(), error
                    .getRejectedValue());
        }
        if (FIELD_TRANSLATION_STATE.equals(fieldName))
        {
            assertEquals(this.importBean.getParameters().getTranslationState(),
                    error.getRejectedValue());
        }
        if (FIELD_FILE_NAME.equals(fieldName))
        {
            assertEquals(this.importBean.getFileUploadBean().getFile().getName(),
                    error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
