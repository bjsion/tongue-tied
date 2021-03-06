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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class LanguageSupportTest {
    private List<Language> languages;
    private Language tagalog;
    private Language malay;
    private Language uzbek;

    @Before
    public void setUp() {
        languages = new ArrayList<Language>();
        tagalog = new Language();
        tagalog.setId(4L);
        tagalog.setCode(LanguageCode.tl);
        tagalog.setName("Tagalog");
        
        malay = new Language();
        malay.setId(58L);
        malay.setCode(LanguageCode.ms);
        malay.setName("Malay");
        
        uzbek = new Language();
        uzbek.setId(87L);
        uzbek.setCode(LanguageCode.uz);
        uzbek.setName("Uzbek");
        
        languages.add(tagalog);
        languages.add(malay);
        languages.add(uzbek);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setValue(tagalog);
        
        assertEquals("4", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setAsText("87");
        
        assertEquals(uzbek, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        LanguageSupport support = new LanguageSupport(languages);
        support.setAsText("INVALID");
    }
}
