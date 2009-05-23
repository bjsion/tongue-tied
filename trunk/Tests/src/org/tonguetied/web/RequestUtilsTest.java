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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Tests for the {@link RequestUtils} class.
 * 
 * @author bsion
 *
 */
public class RequestUtilsTest
{
    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#isGetMethod(javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testIsGetMethodLowerCase()
    {
        final MockHttpServletRequest request = 
            new MockHttpServletRequest("get", "/");
        assertTrue(RequestUtils.isGetMethod(request));
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#isGetMethod(javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testIsGetMethodUpperCase()
    {
        final MockHttpServletRequest request = 
            new MockHttpServletRequest("GET", "/");
        assertTrue(RequestUtils.isGetMethod(request));
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#isGetMethod(javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testIsGetMethodIsFalse()
    {
        final MockHttpServletRequest request = 
            new MockHttpServletRequest("post", "/");
        assertFalse(RequestUtils.isGetMethod(request));
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#getLongParameter(javax.servlet.http.HttpServletRequest, String)}.
     */
    @Test
    public final void testGetLongParameter()
    {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("test", "1");
        final Long value = RequestUtils.getLongParameter(request, "test");
        assertArrayEquals(new Long[] {1L}, new Long[] {value});
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#getLongParameter(javax.servlet.http.HttpServletRequest, String)}.
     */
    @Test
    public final void testGetLongParameterWithEmptyValue()
    {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("test", "");
        final Long value = RequestUtils.getLongParameter(request, "test");
        assertNull(value);
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#getLongParameter(javax.servlet.http.HttpServletRequest, String)}.
     */
    @Test
    public final void testGetLongParameterWithUnknownKey()
    {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("test", "5");
        final Long value = RequestUtils.getLongParameter(request, "different");
        assertNull(value);
    }

    /**
     * Test method for {@link org.tonguetied.web.RequestUtils#getLongParameter(javax.servlet.http.HttpServletRequest, String)}.
     */
    @Test(expected=NumberFormatException.class)
    public final void testGetLongParameterWithInvalidValue()
    {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("test", "adsf");
        RequestUtils.getLongParameter(request, "test");
    }
}
