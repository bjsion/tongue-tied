package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.FormatType;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ExportParametersValidatorTest {

    private ExportParameters parameters;
    private String fieldName;
    
    private static final String FIELD_BUNDLES = "bundles";
    private static final String FIELD_COUNTRIES = "countries";
    private static final String FIELD_LANGUAGES = "languages";
    private static final String FIELD_FORMAT_TYPE = "formatType";
    
    @Parameters
    public static final Collection<Object[]> data() {
        Country japan = new Country();
        japan.setCode(CountryCode.JP);
        japan.setName("Japan");
        
        Country china = new Country();
        china.setCode(CountryCode.CN);
        china.setName("China");
        
        Country hongKong = new Country();
        hongKong.setCode(CountryCode.HK);
        hongKong.setName("Hong Kong");
        
        Language japanese = new Language();
        japanese.setCode(LanguageCode.ja);
        japanese.setName("Japanese");
        
        Language chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Chinese");
        
        Bundle bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setResourceName("bundle1");
        
        Bundle bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
        
        return Arrays.asList(new Object[][] {
            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, null, FIELD_FORMAT_TYPE},
            {new Language[] {}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, FormatType.csv, FIELD_LANGUAGES},
            {null, new Country[] {china, hongKong}, new Bundle[] {bundle1}, FormatType.resources, FIELD_LANGUAGES},
            {new Language[] {chinese, japanese}, new Country[] {}, new Bundle[] {bundle1}, FormatType.xls, FIELD_COUNTRIES},
            {new Language[] {chinese, japanese}, null, new Bundle[] {bundle1, bundle2}, FormatType.resources, FIELD_COUNTRIES},
            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {}, FormatType.xls, FIELD_BUNDLES},
            {new Language[] {chinese, japanese}, new Country[] {china, hongKong, japan}, null, FormatType.properties, FIELD_BUNDLES},
//            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, ExportType.xls, FIELD_FORMAT_TYPE},
//            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, ExportType.xls, FIELD_FORMAT_TYPE},
//            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, ExportType.xls, FIELD_FORMAT_TYPE},
//            {new Language[] {chinese}, new Country[] {china, hongKong}, new Bundle[] {bundle1}, ExportType.xls, FIELD_FORMAT_TYPE},
        });
    }
    
    public ExportParametersValidatorTest(Language[] langauges,
            Country[] countries, Bundle[] bundles, FormatType formatType, String fieldName)
    {
        parameters = new ExportParameters();
        if (langauges == null) {
            parameters.setLanguages(null);
        }
        else {
            for (Language language: langauges) {
                parameters.addLanguage(language);
            }
        }
        if (countries == null) {
            parameters.setCountries(null);
        } else {
            for (Country country : countries) {
                parameters.addCountry(country);
            }
        }
        if (bundles == null) {
            parameters.setBundles(null);
        } else {
            for (Bundle bundle : bundles) {
                parameters.addBundle(bundle);
            }
        }
        parameters.setFormatType(formatType);
        
        this.fieldName = fieldName;
    }

    /**
     * Test method for {@link org.tonguetied.web.ExportParametersValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public void testValidate() {
        ExportParametersValidator validator = new ExportParametersValidator();
        Errors errors = new BindException(this.parameters, "parameters");
        validator.validate(parameters, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_BUNDLES.equals(fieldName)) {
            assertEquals(this.parameters.getBundles(), error.getRejectedValue());
        }
        else if (FIELD_COUNTRIES.equals(fieldName)) {
            assertEquals(this.parameters.getCountries(), error.getRejectedValue());
        }
        else if (FIELD_LANGUAGES.equals(fieldName)) {
            assertEquals(this.parameters.getLanguages(), error.getRejectedValue());
        }
        else if (FIELD_FORMAT_TYPE.equals(fieldName)) {
            assertEquals(this.parameters.getFormatType(), error.getRejectedValue());
        }
        
        assertFalse(error.isBindingFailure());
    }

}
