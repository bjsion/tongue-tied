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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.TranslationPredicate;

/**
 * Validator for the {@link Keyword} object.
 * 
 * @author bsion
 * 
 */
public class KeywordValidator implements Validator
{
    private KeywordService keywordService;

    static final String FIELD_KEYWORD = "keyword";
    static final String FIELD_TRANSLATIONS = "translations";

    public boolean supports(Class clazz)
    {
        return Keyword.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors)
    {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_KEYWORD,
                "error.keyword.required", null, "default");
        final Keyword keyword = (Keyword) target;
        validateDuplicates(keyword, errors);
        validateDuplicates(keyword.getTranslations(), errors);
    }

    /**
     * This validation method checks if a {@link Keyword} object already exists
     * in persistence with the same business key (<code>keyword</code>).
     * 
     * @param keyword the {@link Keyword} object to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(Keyword keyword, Errors errors)
    {
        // check for duplicates of new records only
        if (keyword.getId() == null)
        {
            Keyword other = keywordService.getKeyword(keyword.getKeyword());
            if (other != null)
            {
                errors.rejectValue(FIELD_KEYWORD,
                        "error.keyword.already.exists", 
                        new String[] {keyword.getKeyword() },
                        "default");
            }
        }
    }

    /**
     * This validation method checks if the set of {@link Translation}s for a 
     * {@link Keyword} contains duplicate entries of the business key.
     * 
     * @param translations the set of {@link Translation}s to validate
     * @param errors contextual state about the validation process (never null)
     */
    private void validateDuplicates(SortedSet<Translation> translations, Errors errors)
    {
        Collection<Translation> output;
        TranslationPredicate predicate;
        List<FieldError> fieldErrors;
        if (translations.size() > 1)
        {
            for (Translation translation : translations)
            {
                predicate = new TranslationPredicate(translation.getBundle(), 
                        translation.getCountry(), translation.getLanguage());
                output = CollectionUtils.select(translations, predicate);
                if (output.size() > 1)
                {
                    final String[] errorArgs = new String[] {
                            translation.getLanguage().getName(), 
                            translation.getCountry().getName(), 
                            translation.getBundle().getName()};
                    fieldErrors = errors.getFieldErrors(FIELD_TRANSLATIONS);
                    boolean containsError = false;
                    for (FieldError error : fieldErrors)
                    {
                        containsError = containsError ||
                            Arrays.equals(error.getArguments(), errorArgs);
                    }
                    if (!containsError)
                    {
                        errors.rejectValue(FIELD_TRANSLATIONS,
                            "error.duplicate.translations",
                            errorArgs,
                            "default");
                    }
                }
            }
        }
    }
    
    /**
     * Set the {@link KeywordService} instance.
     * 
     * @param keywordService the keywordService instance.
     */
    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }
}
