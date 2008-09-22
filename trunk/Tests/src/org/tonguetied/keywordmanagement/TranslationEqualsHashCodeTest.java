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
package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class TranslationEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public TranslationEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied");
        
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
        keyword.setContext("context");

        Translation translation = new Translation(bundle, country, language,
                "translated value", TranslationState.UNVERIFIED);
        translation.setKeyword(keyword);
        
        return translation;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied");
        
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");

        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
        keyword.setContext("context");

        Translation translation = new Translation(bundle, country, language,
                "another translated value", TranslationState.UNVERIFIED);
        translation.setKeyword(keyword);
        
        return translation;
    }
}
