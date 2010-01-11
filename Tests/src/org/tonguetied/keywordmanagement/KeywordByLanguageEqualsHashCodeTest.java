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
import org.tonguetied.keywordmanagement.KeywordByLanguage;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

import junitx.extensions.EqualsHashCodeTestCase;

/**
 * @author bsion
 *
 */
public class KeywordByLanguageEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public KeywordByLanguageEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("test");
        bundle.setDescription("resources");
        bundle.setResourceName("test");
        
        Country country = new Country();
        country.setCode(CountryCode.IN);
        country.setName("India");

        Language english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        Language tamil = new Language();
        tamil.setCode(LanguageCode.ta);
        tamil.setName("Tamil");
        
        Language defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        
        KeywordByLanguage item = 
            new KeywordByLanguage("keyword", "context", bundle, country);
        item.addTranslation(english.getCode(), "en translation");
        item.addTranslation(tamil.getCode(), "ta translation");
        item.addTranslation(defaultLanguage.getCode(), "default translation");

        return item;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("test");
        bundle.setDescription("resources");
        bundle.setResourceName("test");
        
        Country country = new Country();
        country.setCode(CountryCode.IN);
        country.setName("India");

        Language english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        Language hindi = new Language();
        hindi.setCode(LanguageCode.hi);
        hindi.setName("Hindi");
        
        Language defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        
        KeywordByLanguage item = 
            new KeywordByLanguage("keyword", "context", bundle, country);
        item.addTranslation(english.getCode(), "en translation");
        item.addTranslation(hindi.getCode(), "hi translation");
        item.addTranslation(defaultLanguage.getCode(), "default translation");

        return item;
    }
}
