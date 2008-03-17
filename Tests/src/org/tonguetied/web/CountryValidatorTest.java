package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.service.ApplicationService;
import org.tonguetied.service.ApplicationServiceStub;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class CountryValidatorTest {
    private ApplicationService appService;
    private Country country;
    private String fieldName;

    private static final String FIELD_CODE = "code";
    private static final String FIELD_NAME = "name";
    
    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {CountryCode.FJ, null, FIELD_NAME},
                {CountryCode.RE, "", FIELD_NAME},
                {CountryCode.RE, "    ", FIELD_NAME},
                {CountryCode.PL, "Poland", FIELD_CODE},
                {CountryCode.PL, null, FIELD_CODE},
                {null, "Korea", FIELD_CODE}
                });
    }
    
    public CountryValidatorTest(CountryCode code, String name, String fieldName) {
        this.country = new Country();
        this.country.setCode(code);
        this.country.setName(name);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup() {
        this.appService = new ApplicationServiceStub();
        Country existing = new Country();
        existing.setId(1256L);
        existing.setCode(CountryCode.PL);
        existing.setName("Poland");
        this.appService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.CountryValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        CountryValidator validator = new CountryValidator();
        validator.setAppService(appService);
        Errors errors = new BindException(this.country, "country");
        validator.validate(this.country, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NAME.equals(fieldName)) {
            assertEquals(this.country.getName(), error.getRejectedValue());
        }
        else if (FIELD_CODE.equals(fieldName)) {
            assertEquals(this.country.getCode(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
