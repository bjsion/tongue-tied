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

import static org.tonguetied.web.Constants.USER;
import static org.tonguetied.web.Constants.USERS;

import java.util.HashMap;
import java.util.List;
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
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        if (logger.isDebugEnabled()) logger.debug("searching for users");
        User user = (User) command;
        List<User> users = userService.findUsers(user);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(USERS, users);
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
