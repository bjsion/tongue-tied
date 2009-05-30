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

import static org.displaytag.tags.TableTagParameters.PARAMETER_PAGE;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.util.ParamEncoder;

/**
 * Utility class of common methods for paginated display.
 * 
 * @author bsion
 *
 */
public final class PaginationUtils
{

    /**
     * Determine the value for the first result. The value is calculated as:
     * (selectedPage - 1) * {@link PreferenceForm#getMaxResults()}
     * 
     * If the start point cannot be calculated then a value of 0 is returned.
     * 
     * @param tableId the table tag id of the table
     * @param maxResults the size of the results
     * @param request the HTTP request 
     * @return the value to be used as the first result of a paginated query.
     */
    static int calculateFirstResult(final String tableId, final int maxResults, 
            HttpServletRequest request)
    {
        final String pageParam = 
            new ParamEncoder(tableId).encodeParameterName(PARAMETER_PAGE);

        Integer selectedPage = RequestUtils.getIntegerParameter(request, pageParam);
        if (selectedPage == null)
        {
            final Object attr = request.getSession().getAttribute(pageParam);
            if (attr != null)
                selectedPage = (Integer) attr;
            if (selectedPage == null)
                selectedPage = 0;
        }
        
        int firstResult = 0;
        if (selectedPage > 0)
            firstResult = (selectedPage-1) * maxResults;
        
        return firstResult;
    }
    
    /**
     * Remove the page parameter for a table from the session.
     * 
     * @param tableId the table tag id of the table
     * @param request the HTTP request 
     */
    static void remove(final String tableId, HttpServletRequest request)
    {
        final String pageParam = 
            new ParamEncoder(tableId).encodeParameterName(PARAMETER_PAGE);

        request.getSession().removeAttribute(pageParam);
    }
}
