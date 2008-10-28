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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;
import org.tonguetied.test.common.PersistenceTestBase;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;



/**
 * @author bsion
 *
 */
public class UserPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        session.saveOrUpdate(user);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        User reloaded = 
            (User) session.get(User.class, user.getId());
        assertEquals(user, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Ignore("test not working for some reason")
    public final void persistUserWithAuthorities() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        session.saveOrUpdate(user);
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        user.addUserRight(userRight);//"ROLE_USER");
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        User reloaded = 
            (User) session.get(User.class, user.getId());
        assertEquals(user, reloaded);
        assertEquals(1, reloaded.getUserRights().size());
        assertTrue(user.getUserRights().contains("ROLE_USER"));
        tx.rollback();
        session.close();
    }
}
