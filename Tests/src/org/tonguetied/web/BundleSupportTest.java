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
import org.tonguetied.keywordmanagement.Bundle;


/**
 * @author bsion
 *
 */
public class BundleSupportTest {
    private List<Bundle> bundles;
    private Bundle bundle1;
    private Bundle bundle2;

    @Before
    public void setUp() {
        bundles = new ArrayList<Bundle>();
        bundle1 = new Bundle();
        bundle1.setId(4L);
        bundle1.setName("Bundle One");
        
        bundle2 = new Bundle();
        bundle2.setId(58L);
        bundle2.setName("Bundle Two");
        
        bundles.add(bundle1);
        bundles.add(bundle2);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        BundleSupport support = new BundleSupport(bundles);
        support.setValue(bundle1);
        
        assertEquals("4", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        BundleSupport support = new BundleSupport(bundles);
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        BundleSupport support = new BundleSupport(bundles);
        support.setAsText("58");
        
        assertEquals(bundle2, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        BundleSupport support = new BundleSupport(bundles);
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        BundleSupport support = new BundleSupport(bundles);
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.BundleSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        BundleSupport support = new BundleSupport(bundles);
        support.setAsText("INVALID");
    }
}
