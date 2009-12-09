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

import static org.displaytag.tags.TableTagParameters.PARAMETER_ORDER;
import static org.displaytag.tags.TableTagParameters.PARAMETER_PAGE;
import static org.displaytag.tags.TableTagParameters.PARAMETER_SORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;

import org.displaytag.util.ParamEncoder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.tonguetied.utils.pagination.Order;
import org.tonguetied.web.PaginationUtils.KeyValue;

/**
 * Test the utility methods of the class {@link PaginationUtils}.
 * 
 * @author bsion
 *
 */
public class PaginationUtilsTest
{

    private static final String VALID_TABLE_ID = "testRequest";
    private static final String VALID_SESSION_TABLE_ID = "testSession";
    private MockHttpServletRequest request;
    private static final String PAGE_PARAM = 
        new ParamEncoder(VALID_TABLE_ID).encodeParameterName(PARAMETER_PAGE);
    private static final String PAGE_SORT = 
        new ParamEncoder(VALID_TABLE_ID).encodeParameterName(PARAMETER_SORT);
    private static final String PAGE_ORDER = 
        new ParamEncoder(VALID_TABLE_ID).encodeParameterName(PARAMETER_ORDER);
    private final String PAGE_PARAM_SESSION = 
        new ParamEncoder(VALID_SESSION_TABLE_ID).encodeParameterName(PARAMETER_PAGE);
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        request = new MockHttpServletRequest();
        request.setParameter(PAGE_PARAM, "3");
        request.setParameter(PAGE_SORT, "test");
        request.setParameter(PAGE_ORDER, "2");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(PAGE_PARAM_SESSION, Integer.valueOf("9"));
        request.setSession(session);
    }

    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResult()
    {
        final int result = 
            PaginationUtils.calculateFirstResult(VALID_TABLE_ID, 20, request);
        assertEquals(40, result);
    }

    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResultStoredInSession()
    {
        final int result = 
            PaginationUtils.calculateFirstResult(VALID_SESSION_TABLE_ID, 10, request);
        assertEquals(80, result);
    }

    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResultWithUnknownTableId()
    {
        final int result = 
            PaginationUtils.calculateFirstResult("unknown", 20, request);
        assertEquals(0, result);
    }
    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResultWithZeroMax()
    {
        final int result = 
            PaginationUtils.calculateFirstResult(VALID_TABLE_ID, 0, request);
        assertEquals(0, result);
    }

    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResultWithZeroPage()
    {
        request.setParameter(PAGE_PARAM, "0");
        final int result = 
            PaginationUtils.calculateFirstResult(VALID_TABLE_ID, 20, request);
        assertEquals(0, result);
    }

    /**
     * Test method for {@link org.tonguetied.web.PaginationUtils#calculateFirstResult(java.lang.String, int, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public final void testCalculateFirstResultWithNegativePage()
    {
        request.setParameter(PAGE_PARAM, "-1");
        final int result = 
            PaginationUtils.calculateFirstResult(VALID_TABLE_ID, 20, request);
        assertEquals(0, result);
    }
    
    /**
     * Test method for {@link PaginationUtils#remove(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testRemove()
    {
        // test preconditions
        assertTrue(containsValue(PAGE_PARAM_SESSION));
        
        PaginationUtils.remove(VALID_SESSION_TABLE_ID, request);
        assertFalse(containsValue(PAGE_PARAM_SESSION));
    }

    /**
     * Test method for {@link PaginationUtils#remove(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testRemoveUnknown()
    {
        // test preconditions
        final String unknown = 
            new ParamEncoder("unknown").encodeParameterName(PARAMETER_PAGE);
        assertFalse(containsValue(unknown));
        
        PaginationUtils.remove("unknown", request);
        assertFalse(containsValue(unknown));
    }

    /**
     * Test method for {@link PaginationUtils#remove(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testRemoveNull()
    {
        // test preconditions
        final String nullParam = 
            new ParamEncoder(null).encodeParameterName(PARAMETER_PAGE);
        assertFalse(containsValue(nullParam));
        
        PaginationUtils.remove(null, request);
        assertFalse(containsValue(nullParam));
    }

    /**
     * @param parameter the parameter name
     * @return <code>true</code> if the parameter is in the session attributes,
     * <code>false</code> otherwise
     */
    private boolean containsValue(final String parameter)
    {
        Enumeration<String> names = request.getSession().getAttributeNames();
        String name;
        boolean containsValue = false;
        while (names.hasMoreElements())
        {
            name = names.nextElement();
            if (parameter.equals(name))
            {
                containsValue = true;
                break;
            }
        }
        return containsValue;
    }
    
    /**
     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testGetOrder()
    {
        final KeyValue<String, Order> keyValue = 
            PaginationUtils.getOrder(VALID_TABLE_ID, request);
        assertEquals("test", keyValue.getKey());
        assertEquals(Order.desc, keyValue.getValue());
    }

    /**
     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testGetOrderForUnknownKey()
    {
        final KeyValue<String, Order> keyValue = 
            PaginationUtils.getOrder("unknown", request);
        assertNull(keyValue);
        
    }

    /**
     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testGetOrderForUnknownValue()
    {
        request.setParameter(PAGE_ORDER, "6");
        final KeyValue<String, Order> keyValue = 
            PaginationUtils.getOrder(VALID_TABLE_ID, request);
        assertEquals("test", keyValue.getKey());
        assertEquals(Order.asc, keyValue.getValue());
    }

    /**
     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
     */
    @Test
    public final void testGetOrderForInvalidValue()
    {
        request.setParameter(PAGE_ORDER, "asdf");
        final KeyValue<String, Order> keyValue = 
            PaginationUtils.getOrder(VALID_TABLE_ID, request);
        assertEquals("test", keyValue.getKey());
        assertEquals(Order.asc, keyValue.getValue());
    }
//
//    /**
//     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
//     */
//    @Test
//    public final void testGetOrderForNullKey()
//    {
//        
//    }
//
//    /**
//     * Test method for {@link PaginationUtils#getOrder(String, javax.servlet.http.HttpServletRequest)}
//     */
//    @Test
//    public final void testGetOrderForNullValue()
//    {
//        
//    }
}
