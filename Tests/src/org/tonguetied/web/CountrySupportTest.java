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
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * @author bsion
 *
 */
public class CountrySupportTest {
    private List<Country> countries;
    private Country taiwan;
    private Country turkey;
    private Country ecuador;

    @Before
    public void setUp() {
        countries = new ArrayList<Country>();
        taiwan = new Country();
        taiwan.setId(4L);
        taiwan.setCode(CountryCode.TW);
        taiwan.setName("Taiwan");
        
        turkey = new Country();
        turkey.setId(58L);
        turkey.setCode(CountryCode.TR);
        turkey.setName("Turkey");
        
        ecuador = new Country();
        ecuador.setId(87L);
        ecuador.setCode(CountryCode.EC);
        ecuador.setName("Ecuador");
        
        countries.add(taiwan);
        countries.add(turkey);
        countries.add(ecuador);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        CountrySupport support = new CountrySupport(countries);
        support.setValue(taiwan);
        
        assertEquals("4", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        CountrySupport support = new CountrySupport(countries);
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        CountrySupport support = new CountrySupport(countries);
        support.setAsText("87");
        
        assertEquals(ecuador, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        CountrySupport support = new CountrySupport(countries);
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        CountrySupport support = new CountrySupport(countries);
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountrySupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        CountrySupport support = new CountrySupport(countries);
        support.setAsText("INVALID");
    }
}
