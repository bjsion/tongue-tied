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

import org.junit.Test;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class LanguageCodeSupportTest {

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setValue(LanguageCode.pi);
        
        assertEquals("pi", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setAsText("ja");
        
        assertEquals(LanguageCode.ja, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.LanguageCodeSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        LanguageCodeSupport support = new LanguageCodeSupport();
        support.setAsText("INVALID");
    }
}
