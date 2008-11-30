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

import static org.tonguetied.test.common.Constants.TABLE_AUTHORITIES;
import static org.tonguetied.test.common.Constants.TABLE_USER;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.encoding.PlaintextPasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.usermanagement.UserRight.Permission;

/**
 * Unit tests for methods of the {@link UserDetailsServiceImpl} implementation
 * of the {@link UserDetailsService}.
 * 
 * @author bsion
 * 
 */
public class UserDetailsServiceTest extends AbstractServiceTest
{
    private User user1;
    private User expired;
    private User badCred;
    private User noAuth;
    private UserDetailsService userDetailsService;
    private PlaintextPasswordEncoder passwordEncoder;

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        user1 = new User("username", "password", "firstName", "lastName",
                "test@test.com", true, true, true, true);
        user1.addUserRight(new UserRight(Permission.ROLE_USER, null, null,
                        null));

        expired = new User("expired", "password", "firstName", "lastName",
                "test@test.com", true, false, true, true);
        expired.addUserRight(new UserRight(Permission.ROLE_ADMIN, null, null,
                null));

        badCred = new User("badCred", "password", "firstName", "lastName",
                "test@test.com", true, true, true, false);
        badCred.addUserRight(new UserRight(Permission.ROLE_USER, null, null,
                null));

        noAuth = new User("noAuth", "password", "firstName", "lastName",
                "test@test.com", true, true, true, false);

        getUserRepository().saveOrUpdate(user1);
        getUserRepository().saveOrUpdate(expired);
        getUserRepository().saveOrUpdate(badCred);
        getUserRepository().saveOrUpdate(noAuth);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    public final void testLoadUserByUsername()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user1
                .getUsername());
        assertTrue("User account should be enabled", userDetails.isEnabled());
        assertTrue("User account should not be expired", userDetails
                .isAccountNonExpired());
        assertTrue("User account should not be locked", userDetails
                .isAccountNonLocked());
        assertTrue("User account should have valid credentials", userDetails
                .isCredentialsNonExpired());
        assertEquals("username", userDetails.getUsername());
        this.vaildatePassword(userDetails.getPassword(), "password");

        assertEquals(1, userDetails.getAuthorities().length);
        GrantedAuthority expectedAuthority = new GrantedAuthorityImpl(
                Permission.ROLE_USER.name());
        assertEquals(expectedAuthority, userDetails.getAuthorities()[0]);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    public final void testLoadUserByUsernameWithExpiredAccount()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(expired
                .getUsername());
        assertTrue("User account should be enabled", userDetails.isEnabled());
        assertFalse("User account should not be expired", userDetails
                .isAccountNonExpired());
        assertTrue("User account should not be locked", userDetails
                .isAccountNonLocked());
        assertTrue("User account should have valid credentials", userDetails
                .isCredentialsNonExpired());
        assertEquals("expired", userDetails.getUsername());
        this.vaildatePassword(userDetails.getPassword(), "password");
        assertEquals(1, userDetails.getAuthorities().length);
        GrantedAuthority expectedAuthority = new GrantedAuthorityImpl(
                Permission.ROLE_ADMIN.name());
        assertEquals(expectedAuthority, userDetails.getAuthorities()[0]);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    public final void testLoadUserByUsernameWithInvalidCred()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(badCred
                .getUsername());
        assertTrue("User account should be enabled", userDetails.isEnabled());
        assertTrue("User account should not be expired", userDetails
                .isAccountNonExpired());
        assertTrue("User account should not be locked", userDetails
                .isAccountNonLocked());
        assertFalse("User account should have valid credentials", userDetails
                .isCredentialsNonExpired());
        assertEquals("badCred", userDetails.getUsername());
        this.vaildatePassword(userDetails.getPassword(), "password");
        assertEquals(1, userDetails.getAuthorities().length);
        GrantedAuthority expectedAuthority = new GrantedAuthorityImpl(
                Permission.ROLE_USER.name());
        assertEquals(expectedAuthority, userDetails.getAuthorities()[0]);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    @ExpectedException(UsernameNotFoundException.class)
    public final void testLoadUserByUsernameNull() throws Exception
    {
        userDetailsService.loadUserByUsername(null);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    @ExpectedException(UsernameNotFoundException.class)
    public final void testLoadUserByUsernameBlank()
    {
        userDetailsService.loadUserByUsername("");
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    @ExpectedException(UsernameNotFoundException.class)
    public final void testLoadUserByUsernameUnknown()
    {
        userDetailsService.loadUserByUsername("unknown");
    }

    private final void vaildatePassword(final String encoded,
            final String expected)
    {
        String[] passwordSalt = passwordEncoder.obtainPasswordAndSalt(encoded);
        assertEquals(expected, passwordSalt[0]);
    }

    /**
     * Test method for {@link UserDetailsService#loadUserByUsername(String)}.
     */
    @ExpectedException(UsernameNotFoundException.class)
    public final void testLoadUserWithNoAuthorizations()
    {
        userDetailsService.loadUserByUsername(noAuth.getUsername());
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_AUTHORITIES, TABLE_USER};
    }

    /**
     * @param userDetailsService the {@link UserDetailsService} to set
     */
    public void setUserDetailsService(UserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }

    /**
     * @param passwordEncoder the encoder used to encrypt the user's password
     */
    public void setPasswordEncoder(PlaintextPasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }
}
