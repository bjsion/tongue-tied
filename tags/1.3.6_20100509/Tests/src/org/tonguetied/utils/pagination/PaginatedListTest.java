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
package org.tonguetied.utils.pagination;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.usermanagement.User;


/**
 * Test cases for methods of class {@link PaginatedList}.
 * 
 * @author bsion
 *
 */
public class PaginatedListTest
{
    private Language japanese;
    private Language vietnamese;
    private Language french;
    private Country japan;
    private Country vietnam;
    private List<Language> languages;
    private List<Country> countries;
    private Bundle bundle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.japan = new Country();
        this.japan.setCode(CountryCode.JP);
        this.japan.setName("Japan");
        
        this.vietnam = new Country();
        this.vietnam.setCode(CountryCode.VI);
        this.vietnam.setName("Vietnam");
        
        this.countries = new ArrayList<Country>();
        this.countries.add(this.japan);
        this.countries.add(this.vietnam);
        
        this.japanese = new Language();
        this.japanese.setCode(LanguageCode.ja);
        this.japanese.setName("Japanese");
        
        this.vietnamese = new Language();
        this.vietnamese.setCode(LanguageCode.vi);
        this.vietnamese.setName("Vietnamese");
        
        this.french = new Language();
        this.french.setCode(LanguageCode.fr);
        this.french.setName("French");
        
        this.bundle = new Bundle();
        this.bundle.setName("bundle name");
        this.bundle.setResourceName("resourceName");
        
        this.languages = new ArrayList<Language>();
        this.languages.add(japanese);
        this.languages.add(vietnamese);
        this.languages.add(french);
    }

    /**
     * Test method for {@link PaginatedList#clone()}.
     */
    @Test
    public final void testCloneWithEmptyValues() throws Exception
    {
        final PaginatedList<User> users = 
            new PaginatedList<User>(new ArrayList<User>(), 5);
        
        final PaginatedList<User> clone = users.deepClone();
        
        assertFalse(users == clone);
        assertTrue(users.getClass() == clone.getClass());
        assertEquals(users, clone);
    }
    
    /**
     * Test method for {@link PaginatedList#clone()}.
     */
    @Test(expected=CloneNotSupportedException.class)
    public final void testCloneWithUncloneableObject() throws Exception
    {
        final PaginatedList<Language> testLanguages = 
            new PaginatedList<Language>(this.languages, 10);
        
        testLanguages.deepClone();
    }
    
    /**
     * Test method for {@link PaginatedList#clone()}.
     */
    @Test
    public final void testCloneWithSimpleKeyword() throws Exception
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword(null);
        keyword.setContext(null);
        keyword.setTranslations(null);
        
        List<Keyword> temp = new ArrayList<Keyword>();
        temp.add(keyword);
        PaginatedList<Keyword> keywords = new PaginatedList<Keyword>(temp, 15);
        
        final PaginatedList<Keyword> clone = keywords.deepClone();
        
        assertFalse(keywords == clone);
        assertTrue(keywords.getClass() == clone.getClass());
        assertEquals(keywords.getMaxListSize(), clone.getMaxListSize());
        assertEquals(keywords, clone);
        assertFalse(keywords.get(0) == clone.get(0));
        assertEquals(keyword, clone.get(0));
    }
    
    /**
     * Test method for {@link PaginatedList#clone()}.
     */
    @Test
    public final void testCloneWithTranslation() throws Exception
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("a keyword");
        keyword.setContext("context");
        Translation translation = new Translation();
        translation.setBundle(bundle);
        translation.setCountry(vietnam);
        translation.setLanguage(vietnamese);
        translation.setValue("the value");
        keyword.addTranslation(translation);
        
        List<Keyword> temp = new ArrayList<Keyword>();
        temp.add(keyword);
        PaginatedList<Keyword> keywords = new PaginatedList<Keyword>(temp, 10);
        
        final PaginatedList<Keyword> clone = keywords.deepClone();
        
        assertFalse(keywords == clone);
        assertTrue(keywords.getClass() == clone.getClass());
        assertEquals(keywords.getMaxListSize(), clone.getMaxListSize());
        assertEquals(keywords, clone);
        assertFalse(keywords.get(0) == clone.get(0));
        assertEquals(keyword, clone.get(0));
        assertEquals(translation, clone.get(0).getTranslations().first());
        assertFalse(keywords.get(0).getTranslations().first() == 
            clone.get(0).getTranslations().first());
    }
}
