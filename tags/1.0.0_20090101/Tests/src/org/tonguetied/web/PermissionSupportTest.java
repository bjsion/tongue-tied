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
import org.tonguetied.usermanagement.UserRight.Permission;


/**
 * @author bsion
 *
 */
public class PermissionSupportTest {

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        PermissionSupport support = new PermissionSupport();
        support.setValue(Permission.ROLE_ADMIN);
        
        assertEquals("ROLE_ADMIN", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        PermissionSupport support = new PermissionSupport();
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("ROLE_ADMIN");
        
        assertEquals(Permission.ROLE_ADMIN, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("INVALID");
    }
}
