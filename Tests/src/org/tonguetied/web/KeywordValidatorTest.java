package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.tonguetied.web.KeywordValidator.FIELD_KEYWORD;

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
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class KeywordValidatorTest {
    private KeywordService keywordService;
    private Keyword keyword;
    private String fieldName;
    
    private static Translation translation;

    @Parameters
    public static final Collection<Object[]> data() {
        Country country = new Country();
        country.setCode(CountryCode.JP);
        country.setName("Japan");
        
        Language language = new Language();
        language.setCode(LanguageCode.ja);
        language.setName("Japanese");
        
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        
        Translation expectedTranslation = new Translation();
        expectedTranslation.setCountry(country);
        expectedTranslation.setLanguage(language);
        expectedTranslation.setBundle(bundle);
        
        return Arrays.asList(new Object[][] {
                {null, "context", null, FIELD_KEYWORD},
                {"", "context", translation, FIELD_KEYWORD},
                {"   ", null, translation, FIELD_KEYWORD},
                {"keyword", "context", translation, FIELD_KEYWORD},
                {"keyword", "", translation, FIELD_KEYWORD}
                });
    }
    
    public KeywordValidatorTest(String keywordStr, String context, Translation translation, String fieldName) {
        this.keyword = new Keyword();
        this.keyword.setKeyword(keywordStr);
        this.keyword.setContext(context);
        this.keyword.addTranslation(translation);
        this.fieldName = fieldName;
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.keywordService = new KeywordServiceStub();
        Keyword existing = new Keyword();
        existing.setId(1487L);
        existing.setKeyword("keyword");
        existing.setContext("context");
        existing.addTranslation(translation);
        this.keywordService.saveOrUpdate(existing);
    }

    /**
     * Test method for {@link org.tonguetied.web.KeywordValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    public final void testValidate() {
        KeywordValidator validator = new KeywordValidator();
        validator.setKeywordService(keywordService);
        Errors errors = new BindException(this.keyword, "keyword");
        validator.validate(this.keyword, errors);
        
        assertFalse(errors.getAllErrors().isEmpty());
        FieldError error = errors.getFieldError(fieldName);
        if (FIELD_KEYWORD.equals(fieldName)) {
            assertEquals(this.keyword.getKeyword(), error.getRejectedValue());
        }
        assertFalse(error.isBindingFailure());
    }

}
