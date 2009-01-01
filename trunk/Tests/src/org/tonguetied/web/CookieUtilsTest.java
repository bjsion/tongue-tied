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

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

/**
 * @author bsion
 *
 */
public class CookieUtilsTest
{
    MockHttpServletRequest request;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        ServletContext context = new MockServletContext();
        request = new MockHttpServletRequest(context);
        request.setContextPath("/test");
    }

    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#getCookie(javax.servlet.http.HttpServletRequest, java.lang.String, boolean)}.
     */
    @Test
    public final void testGetCookieWithNullCookies()
    {
        request.setCookies(null);
        Cookie cookie = CookieUtils.getCookie(request, "name");
        assertNull(cookie);
    }

    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#getCookie(javax.servlet.http.HttpServletRequest, java.lang.String, boolean)}.
     */
    @Test
    public final void testGetCookieWithEmptyCookies()
    {
        Cookie[] cookies = new Cookie[] {};
        request.setCookies(cookies);
        Cookie cookie = CookieUtils.getCookie(request, "name");
        assertNull(cookie);
    }

    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#getCookie(javax.servlet.http.HttpServletRequest, java.lang.String, boolean)}.
     */
    @Test
    public final void testGetCookieWhenCookieDoesNotExist()
    {
        Cookie[] cookies = new Cookie[] {new Cookie("name", "value")};
        request.setCookies(cookies);
        Cookie cookie = CookieUtils.getCookie(request, "name2");
        assertNull(cookie);
    }

    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#getCookie(javax.servlet.http.HttpServletRequest, java.lang.String, boolean)}.
     */
    @Test
    public final void testGetCookieWithNullName()
    {
        Cookie[] cookies = new Cookie[] {new Cookie("name", "value")};
        request.setCookies(cookies);
        Cookie cookie = CookieUtils.getCookie(request, null);
        assertNull(cookie);
    }
    
    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#getCookie(javax.servlet.http.HttpServletRequest, java.lang.String, boolean)}.
     */
    @Test
    public final void testGetCookieWhenCookieExists()
    {
        Cookie[] cookies = new Cookie[] {
                new Cookie("name", "value"),
                new Cookie("other1", "value"),
                new Cookie("other2", "value"),
                new Cookie("other3", "value"),
                new Cookie("other4", "value"),
                new Cookie("other5", "value")
                };
        request.setCookies(cookies);
        Cookie cookie = CookieUtils.getCookie(request, "name");
        assertEquals("name", cookie.getName());
        assertEquals("value", cookie.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CookieUtils#createCookie(HttpServletRequest, String, String)}.
     */
    @Test
    public final void testCreateCookie()
    {
        Cookie cookie = CookieUtils.createCookie(request, "name", "value");
        assertEquals("name", cookie.getName());
        assertEquals("value", cookie.getValue());
        assertEquals("/test", cookie.getPath());
        assertEquals(-1, cookie.getMaxAge());
    }
}
