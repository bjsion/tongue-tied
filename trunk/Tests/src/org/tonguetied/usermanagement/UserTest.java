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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.usermanagement.UserRight.Permission;


/**
 * @author bsion
 *
 */
public class UserTest {
    private User user1;
    private Language tamil;
    private Country india;
    private Bundle bundle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        user1 = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        tamil = new Language();
        tamil.setCode(LanguageCode.ta);
        tamil.setName("Tamil");
        india = new Country();
        india.setCode(CountryCode.IN);
        india.setName("India");
        bundle = new Bundle();
        bundle.setName("test");
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#setUserRights(SortedSet)}.
     */
    @Test
    public final void testSetUserRights() {
        UserRight userRight1 = new UserRight(Permission.ROLE_USER, tamil, india, bundle);
        UserRight userRight2 = new UserRight(Permission.ROLE_ADMIN, tamil, india, bundle);
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(userRight1);
        userRights.add(userRight2);
        
        user1.setUserRights(userRights);
        
        GrantedAuthority[] authorities = user1.getAuthorities();
        assertEquals(2, authorities.length);
        GrantedAuthority[] expectedAuthorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl(Permission.ROLE_ADMIN.name()),
                new GrantedAuthorityImpl(Permission.ROLE_USER.name())
        };
        assertArrayEquals(expectedAuthorities, authorities);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#setUserRights(SortedSet)}.
     */
    @Test
    public final void testSetUserRightsToNull() {
        user1.setUserRights(null);
        
        GrantedAuthority[] authorities = user1.getAuthorities();
        assertNull(authorities);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#addUserRight(org.tonguetied.usermanagement.UserRight)}.
     */
    @Test
    public final void testAddNewUserRightToEmptyList() {
        UserRight userRight1 = new UserRight(Permission.ROLE_USER, tamil, india, bundle);
        user1.addUserRight(userRight1);
        
        GrantedAuthority[] authorities = user1.getAuthorities();
        assertEquals(1, authorities.length);
        GrantedAuthority[] expectedAuthorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl(Permission.ROLE_USER.name())
        };
        assertArrayEquals(expectedAuthorities, authorities);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#addUserRight(org.tonguetied.usermanagement.UserRight)}.
     */
    @Test
    public final void testAddNewUserRightToExistingList() {
        UserRight userRight1 = new UserRight(Permission.ROLE_USER, tamil, india, bundle);
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(userRight1);
        user1.setUserRights(userRights);
        
        UserRight userRight2 = new UserRight(Permission.ROLE_ADMIN, tamil, india, bundle);
        user1.addUserRight(userRight2);
        
        GrantedAuthority[] authorities = user1.getAuthorities();
        assertEquals(2, authorities.length);
        GrantedAuthority[] expectedAuthorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl(Permission.ROLE_ADMIN.name()),
                new GrantedAuthorityImpl(Permission.ROLE_USER.name())
        };
        assertArrayEquals(expectedAuthorities, authorities);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#addUserRight(org.tonguetied.usermanagement.UserRight)}.
     */
    @Test
    public final void testAddExistingUserRight() {
        UserRight userRight1 = new UserRight(Permission.ROLE_USER, tamil, india, bundle);
        UserRight userRight2 = new UserRight(Permission.ROLE_ADMIN, tamil, india, bundle);
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(userRight1);
        userRights.add(userRight2);
        user1.setUserRights(userRights);

        user1.addUserRight(userRight1);
        
        GrantedAuthority[] authorities = user1.getAuthorities();
        assertEquals(2, authorities.length);
        GrantedAuthority[] expectedAuthorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl(Permission.ROLE_ADMIN.name()),
                new GrantedAuthorityImpl(Permission.ROLE_USER.name())
        };
        assertArrayEquals(expectedAuthorities, authorities);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.User#addUserRight(org.tonguetied.usermanagement.UserRight)}.
     */
    @Test
    public final void testAddNullUserRightToEmptyUserRights() {
        try {
            user1.addUserRight(null);
            fail("adding null userright should throw exception");
        }
        catch (IllegalArgumentException iae) {
            assertNull(user1.getAuthorities());
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.usermanagement.User#addUserRight(org.tonguetied.usermanagement.UserRight)}.
     */
    @Test
    public final void testAddNullUserRightToExistingUserRights() {
        UserRight userRight1 = new UserRight(Permission.ROLE_USER, tamil, india, bundle);
        UserRight userRight2 = new UserRight(Permission.ROLE_ADMIN, tamil, india, bundle);
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(userRight1);
        userRights.add(userRight2);
        user1.setUserRights(userRights);

        try {
            user1.addUserRight(null);
            fail("adding null userright should throw exception");
        }
        catch (IllegalArgumentException iae) {
            GrantedAuthority[] authorities = user1.getAuthorities();
            assertEquals(2, authorities.length);
            GrantedAuthority[] expectedAuthorities = new GrantedAuthority[] {
                    new GrantedAuthorityImpl(Permission.ROLE_ADMIN.name()),
                    new GrantedAuthorityImpl(Permission.ROLE_USER.name())
            };
            assertArrayEquals(expectedAuthorities, authorities);
        }
    }
}
