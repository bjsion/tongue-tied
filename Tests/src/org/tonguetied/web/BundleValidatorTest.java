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
import org.tonguetied.domain.Bundle;
import org.tonguetied.service.ApplicationService;
import org.tonguetied.service.ApplicationServiceStub;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class BundleValidatorTest {
    private ApplicationService appService;
    private Bundle bundle;
    private String fieldName;

    private static final String FIELD_NAME = "name";
    
    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {null, null, null, null, FIELD_NAME},
                {"", null, null, null, FIELD_NAME},
                {"   ", null, null, null, FIELD_NAME},
                {"test", "description", null, null, FIELD_NAME}
                });
    }
    
    public BundleValidatorTest(String name,
                               String description,
                               String resourceName,
                               String resourceDestination,
                               String fieldName) {
        this.bundle = new Bundle();
        this.bundle.setName(name);
        this.bundle.setDescription(description);
        this.bundle.setResourceName(resourceName);
        this.bundle.setResourceDestination(resourceDestination);
        this.fieldName = fieldName;
    }
    
    @Before
    public void setup() {
        this.appService = new ApplicationServiceStub();
        Bundle existing = new Bundle();
        existing.setId(1256L);
        existing.setName("test");
        existing.setDescription("description");
        this.appService.saveOrUpdate(existing);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.BundleValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidateInvalidObject() {
        BundleValidator validator = new BundleValidator();
        validator.setAppService(appService);
        Errors errors = new BindException(this.bundle, "bundle");
        validator.validate(this.bundle, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_NAME.equals(fieldName)) {
            assertEquals(this.bundle.getName(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
