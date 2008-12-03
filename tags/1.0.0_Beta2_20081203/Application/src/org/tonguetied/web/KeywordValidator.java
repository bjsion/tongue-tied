/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
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
    
    static final String FIELD_KEYWORD = "keyword";

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
                errors, FIELD_KEYWORD, "error.keyword.required", null, "default");
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
                        FIELD_KEYWORD, 
                        "error.keyword.already.exists", 
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
