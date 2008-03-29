package org.tonguetied.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;


/**
 * Validator for the {@link Keyword} object.
 * 
 * @author bsion
 *
 */
public class KeywordValidator implements Validator {
    private KeywordService keywordService;

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return Keyword.class.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "keyword", "errorKeywordRequired", null, "default");
        validateDuplicates((Keyword) target, errors);
    }

    /**
     * This validation method checks if a {@link Keyword} object already exists 
     * in persistence with the same business key (<code>keyword</code>).
     * 
     * @param keyword the {@link Keyword} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(Keyword keyword, Errors errors) {
        // check for duplicates of new records only
        if (keyword.getId() == null) {
            Keyword other = keywordService.getKeyword(keyword.getKeyword());
            if (other != null) {
                errors.rejectValue(
                        "keyword", 
                        "errorKeywordAlreadyExists", 
                        new String[] {keyword.getKeyword()}, 
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
