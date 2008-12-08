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
package org.tonguetied.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stub used to simulate UserService methods for unit tests.
 * 
 * @author bsion
 * 
 */
public class UserServiceStub implements UserService
{
    Map<String, User> users = new HashMap<String, User>();

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.service.UserService#saveOrUpdate(org.tonguetied.domain.User)
     */
    public void saveOrUpdate(User user)
    {
        saveOrUpdate(user, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.service.UserService#saveOrUpdate(org.tonguetied.domain.User)
     */
    public void saveOrUpdate(User user, boolean encodePassword)
    {
        users.put(user.getUsername(), user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.service.UserService#getUser(java.lang.String)
     */
    public User getUser(final String username)
    {
        return users.get(username);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.service.UserService#getUser(java.lang.Long)
     */
    public User getUser(Long id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.usermanagement.UserService#changePassword(org.tonguetied.usermanagement.User,
     *      java.lang.String, java.lang.String)
     */
    public void changePassword(User user, String oldPassword, String newPassword)
            throws AuthenticationException
    {
        user.setPassword(newPassword);
        saveOrUpdate(user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.usermanagement.UserService#encodePassword(org.tonguetied.usermanagement.User,
     *      java.lang.String)
     */
    public void encodePassword(User user, String newPassword)
    {
        user.setPassword(newPassword);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.service.UserService#getUsers()
     */
    public List<User> getUsers()
    {
        return new ArrayList<User>(users.values());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tonguetied.usermanagement.UserService#findUsers(org.tonguetied.usermanagement.User)
     */
    public List<User> findUsers(User user)
    {
        return new ArrayList<User>(users.values());
    }
}
