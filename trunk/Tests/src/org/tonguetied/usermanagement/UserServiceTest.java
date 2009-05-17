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

import static org.tonguetied.usermanagement.User.TABLE_AUTHORITIES;
import static org.tonguetied.usermanagement.User.TABLE_USER;

import java.util.List;

import org.junit.Test;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.utils.pagination.PaginatedList;

/**
 * Unit tests for methods of the {@link UserServiceImpl} implementation of
 * the {@link UserService}.
 * 
 * @author bsion
 * 
 */
public class UserServiceTest extends AbstractServiceTest
{
    private User user1;
    private User user2;
    private User user3;

    private UserService userService;
    private PasswordEncoder encoder;

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        String password = encoder.encodePassword("password", "username1");
        user1 = new User("username1", password, "firstName", "lastName",
                "test@test.com", true, true, true, true);
        password = encoder.encodePassword("password", "username2");
        user2 = new User("username2", password, "firstName", "lastName",
                "test@test.com", true, true, true, true);
        password = encoder.encodePassword("password", "username3");
        user3 = new User("username3", password, "anotherName", "different",
                "other@other.com", true, true, true, true);

        getUserRepository().saveOrUpdate(user1);
        getUserRepository().saveOrUpdate(user2);
        getUserRepository().saveOrUpdate(user3);
    }
    
    public final void testSaveUserEncodePassword()
    {
        final String rawPassword = "password";
        User user = new User("test", rawPassword, "test", "test",
                "test@test.com", true, true, true, true);
        
        userService.saveOrUpdate(user, true);
        
        User actual = getUserRepository().getUser("test");
        assertNotNull(actual.getId());
        assertEquals(user, actual);
        assertFalse(rawPassword.equals(actual.getPassword()));
    }

    public final void testSaveUserDoNotEncodePassword()
    {
        final String rawPassword = "password";
        User user = new User("test", rawPassword, "test", "test",
                "test@test.com", true, true, true, true);
        
        userService.saveOrUpdate(user, false);
        
        User actual = getUserRepository().getUser("test");
        assertNotNull(actual.getId());
        assertEquals(user, actual);
        assertEquals(rawPassword, actual.getPassword());
    }

    /**
     * Test method for {@link UserService#getUser(String)}.
     */
    public final void testGetUserByNameWithNoMatch()
    {
        final User actual = userService.getUser("unknown");

        assertNull(actual);
    }

    /**
     * Test method for {@link UserService#getUser(Long)}.
     */
    public final void testGetUserByIdWithNoMatch()
    {
        final User actual = userService.getUser(-1L);

        assertNull(actual);
    }

    /**
     * Test method for {@link UserService#getUser(String)}.
     */
    public final void testGetUserByUserName()
    {
        final User actual = userService.getUser(user1.getUsername());

        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link UserService#getUser(Long)}.
     */
    public final void testGetUserById()
    {
        final User actual = userService.getUser(user1.getId());

        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link UserServiceImpl#getUsers()}.
     */
    @Test
    public final void testGetUsers()
    {
        final List<User> users = userService.getUsers();
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }

    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPagination()
    {
        final PaginatedList<User> list = userService.getUsers(0, 2);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithInvalidFirstPositionGreaterThanSize()
    {
        final PaginatedList<User> list = userService.getUsers(7, 2);
        assertEquals(0, list.getMaxListSize());
        assertTrue(list.isEmpty());
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithNegativeFirstPosition()
    {
        final PaginatedList<User> list = userService.getUsers(-1, 2);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithNullFirstPosition()
    {
        final PaginatedList<User> list = userService.getUsers(null, 2);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithZeroMax()
    {
        final PaginatedList<User> list = userService.getUsers(0, 0);
        assertEquals(0, list.getMaxListSize());
        assertTrue(list.isEmpty());
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithNullMax()
    {
        final PaginatedList<User> list = userService.getUsers(0, null);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }
    
    /**
     * Test method for {@link UserServiceImpl#getUsers(Integer, Integer))}.
     */
    @Test
    public final void testGetUsersWithPaginationWithGreaterThanSize()
    {
        final PaginatedList<User> list = userService.getUsers(0, 45);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }
    
    @Test(expected = AuthenticationException.class)
    @ExpectedException(value = AuthenticationException.class)
    public final void testChangePasswordWithInvalidOldPassword()
    {
        userService.changePassword(user1, "invalid", "newPassword");
    }

    @Test
//    @Rollback
    public final void testChangePassword()
    {
        final String newPassword = "new";
        final String oldPassword = "password";

        userService.changePassword(user1, oldPassword, newPassword);

        final User actual = userService.getUser(user1.getId());
        final String[] components = UserTestUtils.demergePasswordAndSalt(actual
                .getPassword());
        assertEquals(newPassword, components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    @ExpectedException(value = IllegalArgumentException.class)
    public final void testChangePasswordToNull()
    {
        final String oldPassword = "password";

        userService.changePassword(user1, oldPassword, null);
    }

    @Test
    public final void testEncodePassword()
    {
        final String newPassword = "new";
        ((UserServiceImpl)userService).encodePassword(user1, newPassword);
        final String[] components = UserTestUtils.demergePasswordAndSalt(user1
                .getPassword());
        assertEquals(newPassword, components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }

    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when there are no matching users.
     */
    @Test
    public final void testFindUsersNoMatches()
    {
        User criteria = new User();
        criteria.setUsername("nonexistant");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(0, list.getMaxListSize());
        final List<User> users = list;
        assertTrue(users.isEmpty());
    }

    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when the user criteria is all empty.
     */
    @Test
    public final void testFindUsersWithEmptyCriteria()
    {
        User criteria = new User();
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }

    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when there is more than one matching user
     */
    @Test
//    @Rollback
    public final void testFindUsersWithMutipleMatches()
    {
        User criteria = new User();
        criteria.setFirstName("firstName");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(2, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when there is more than one matching user using a wild 
     * card matching at the start.
     */
    @Test
//    @Rollback
    public final void testFindUsersWithWildcardMatchesStart()
    {
        User criteria = new User();
        criteria.setLastName("Name");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(2, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when there is more than one matching user using a wild 
     * card matching at the end.
     */
    @Test
//    @Rollback
    public final void testFindUsersWithWildcardMatchesEnd()
    {
        User criteria = new User();
        criteria.setEmail("test");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(2, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test the {@link UserService#findUsers(User, Integer, Integer))} method 
     * for the scenario when there is more than one matching user but the caps 
     * are different.
     */
    @Test
    @Rollback
    public final void testFindUsersIgnoreCaps()
    {
        final User user4 = new User("username4", "password", "firstName", "lastName",
                "test2@test.com", true, true, true, true);
        userService.saveOrUpdate(user4);
        
        User criteria = new User();
        criteria.setUsername("USERNAME4");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, null);
        assertEquals(1, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(1, users.size());
        assertTrue(users.contains(user4));
    }
    
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListInvalidFirstPositionGreaterThanSize()
    {
        User criteria = new User();
        criteria.setUsername("username");
        final PaginatedList<User> list = userService.findUsers(criteria, 22, 2);
        assertEquals(0, list.getMaxListSize());
        final List<User> users = list;
        assertTrue(users.isEmpty());
    }

    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListInvalidFirstPositionNegative()
    {
        User criteria = new User();
        criteria.setEmail("test");
        final PaginatedList<User> list = userService.findUsers(criteria, -1, 2);
        assertEquals(2, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
  
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListNullFirstPosition()
    {
        User criteria = new User();
        criteria.setEmail("test");
        final PaginatedList<User> list = userService.findUsers(criteria, null, 2);
        assertEquals(2, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
  
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListNullMax()
    {
        User criteria = new User();
        criteria.setEmail("com");
        final PaginatedList<User> list = userService.findUsers(criteria, 2, null);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(1, users.size());
        assertTrue(users.contains(user3));
    }
  
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListZeroMax()
    {
        User criteria = new User();
        criteria.setEmail("test");
        final PaginatedList<User> list = userService.findUsers(criteria, null, 0);
        assertEquals(0, list.getMaxListSize());
        final List<User> users = list;
        assertTrue(users.isEmpty());
    }
  
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedListMaxGreaterThanSize()
    {
        User criteria = new User();
        criteria.setUsername("username");
        final PaginatedList<User> list = userService.findUsers(criteria, 0, 100);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }
  
    /**
     * Test method for {@link UserService#findUsers(User, Integer, Integer)}.
     */
    @Test
    public final void testFindUsersWithPagedList()
    {
        User criteria = new User();
        criteria.setUsername("username");
        final PaginatedList<User> list = userService.findUsers(criteria, 1, 2);
        assertEquals(3, list.getMaxListSize());
        final List<User> users = list;
        assertEquals(2, users.size());
        assertTrue(users.contains(user3));
        assertTrue(users.contains(user2));
    }
  
    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_AUTHORITIES, TABLE_USER};
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Assign the encoder.
     *
     * @param encoder the encoder to set
     */
    public void setEncoder(PasswordEncoder encoder)
    {
        this.encoder = encoder;
    }
}
