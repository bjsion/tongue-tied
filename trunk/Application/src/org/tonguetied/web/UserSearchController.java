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

import static org.tonguetied.usermanagement.User.FIELD_EMAIL;
import static org.tonguetied.usermanagement.User.FIELD_FIRSTNAME;
import static org.tonguetied.usermanagement.User.FIELD_LASTNAME;
import static org.tonguetied.usermanagement.User.FIELD_USERNAME;
import static org.tonguetied.web.Constants.DEFAULT_USER_PAGE_SIZE;
import static org.tonguetied.web.Constants.MAX_LIST_SIZE;
import static org.tonguetied.web.Constants.BTN_SEARCH;
import static org.tonguetied.web.Constants.USER;
import static org.tonguetied.web.Constants.USERS;
import static org.tonguetied.web.Constants.USER_SIZE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;
import org.tonguetied.utils.pagination.PaginatedList;

/**
 * Controller for processing user search requests.
 * 
 * @author bsion
 * 
 */
public class UserSearchController extends SimpleFormController
{
    private UserService userService;

    private static final Logger logger = Logger
            .getLogger(UserSearchController.class);

    /**
     * Create new instance of KeywordSearchController
     */
    public UserSearchController()
    {
        setCommandClass(User.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        return new User();
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        ModelAndView mav = null;
        // Need to intercept get requests to this controller in the scenario 
        // when sent from a paginated link, so need to display the search 
        // results
        if (RequestUtils.isGetMethod(request))
        {
            if (logger.isDebugEnabled())
                logger.debug("evaluating if this a search request");
            
            final String searchBtn = request.getParameter(BTN_SEARCH);
            if (searchBtn != null)
            {
                User user = new User();
                user.setEmail(request.getParameter(FIELD_EMAIL));
                user.setFirstName(request.getParameter(FIELD_FIRSTNAME));
                user.setLastName(request.getParameter(FIELD_LASTNAME));
                user.setUsername(request.getParameter(FIELD_USERNAME));
                mav = onSubmit(request, response, user, super.getErrorsForNewForm(request));
            }
        }
        
        if (mav == null)
            mav = super.handleRequest(request, response);
        
        return mav;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        if (logger.isDebugEnabled()) logger.debug("searching for users");
        final int firstResult = PaginationUtils.calculateFirstResult(
                "user", DEFAULT_USER_PAGE_SIZE, request);

        final User user = (User) command;
        final PaginatedList<User> users = 
            userService.findUsers(user, firstResult, DEFAULT_USER_PAGE_SIZE);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(USERS, users);
        model.put(USER_SIZE, DEFAULT_USER_PAGE_SIZE);
        model.put(MAX_LIST_SIZE, users.getMaxListSize());
        model.put(USER, user);

        return new ModelAndView(getSuccessView(), model);
    }

    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception
    {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(
                        true));
    }

    /**
     * Assign the {@link UserService}.
     * 
     * @param userService the {@link UserService} to set.
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }
}
