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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Translation;


/**
 * Helper class to transform a list {@link Keyword} into a {@link List} of 
 * {@link Translation}s. 
 * 
 * @author bsion
 *
 */
public class TranslationTransformer {

    /**
     * Transform a list {@link Keyword} into a {@link List} of 
     * {@link Translation}s. If the key word does not contain any 
     * {@link Translation}s, then an empty {@link Translation} is created and
     * added to the list as a place holder for the {@link Keyword}.
     * 
     * @param keywords the list of {@link Keyword}s to transform
     * @return a list of {@link Translation}s
     */
    public static List<Translation> transform(final List<Keyword> keywords) 
    {
        List<Translation> translations = new ArrayList<Translation>();
  
        if (keywords != null) {
            Translation translation;
            for (Keyword keyword: keywords) {
                if (CollectionUtils.isEmpty(keyword.getTranslations())) {
                    translation = new Translation();
                    translation.setKeyword(keyword.clone());
                    translations.add(translation);
                }
                else {
                    translations.addAll(keyword.getTranslations());
                }
            }
        }
        
        return translations;
    }

}
