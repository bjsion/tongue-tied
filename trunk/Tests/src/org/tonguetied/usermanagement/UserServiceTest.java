package org.tonguetied.usermanagement;

import java.util.List;

import org.junit.Test;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.tonguetied.test.common.AbstractServiceTest;

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

    private UserService userService;
    private PasswordEncoder encoder;

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        final String password = encoder.encodePassword("password", "username");
        user1 = new User("username", password, "firstName", "lastName",
                "test@test.com", true, true, true, true);

        getUserRepository().saveOrUpdate(user1);
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
        assertEquals(1, users.size());
        assertTrue(users.contains(user1));
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
        userService.encodePassword(user1, newPassword);
        final String[] components = UserTestUtils.demergePasswordAndSalt(user1
                .getPassword());
        assertEquals(newPassword, components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }

    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when there are no matching users.
     */
    @Test
    public final void testFindUsersNoMatches()
    {
        User criteria = new User();
        criteria.setUsername("nonexistant");
        final List<User> users = userService.findUsers(criteria);
        assertTrue(users.isEmpty());
    }

    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when the user criteria is all empty.
     */
    @Test
    public final void testFindUsersWithEmptyCriteria()
    {
        User criteria = new User();
        final List<User> users = userService.findUsers(criteria);
        assertEquals(1, users.size());
        assertTrue(users.contains(user1));
    }

    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when there is more than one matching user
     */
    @Test
//    @Rollback
    public final void testFindUsersWithMutipleMatches()
    {
        final User user2 = new User("username2", "password", "firstName", "lastName",
                "test2@test.com", true, true, true, true);
        userService.saveOrUpdate(user2);
        final User user3 = new User("username3", "password", "Different", "lastName",
                "test3@test.com", true, true, true, true);
        userService.saveOrUpdate(user3);
        
        User criteria = new User();
        criteria.setFirstName("firstName");
        final List<User> users = userService.findUsers(criteria);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when there is more than one matching user using a wild card matching at
     * the start
     */
    @Test
//    @Rollback
    public final void testFindUsersWithWildcardMatchesStart()
    {
        final User user2 = new User("username2", "password", "firstName", "lastName",
                "test2@test.com", true, true, true, true);
        userService.saveOrUpdate(user2);
        final User user3 = new User("username3", "password", "firstName", "Different",
                "test3@test.com", true, true, true, true);
        userService.saveOrUpdate(user3);
        
        User criteria = new User();
        criteria.setLastName("Name");
        final List<User> users = userService.findUsers(criteria);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when there is more than one matching user using a wild card matching at
     * the end
     */
    @Test
//    @Rollback
    public final void testFindUsersWithWildcardMatchesEnd()
    {
        User user2 = new User("username2", "password", "firstName", "lastName",
                "test2@test.com", true, true, true, true);
        userService.saveOrUpdate(user2);
        User user3 = new User("username3", "password", "firstName", "lastName",
                "different@different.com", true, true, true, true);
        userService.saveOrUpdate(user3);
        
        User criteria = new User();
        criteria.setEmail("test");
        final List<User> users = userService.findUsers(criteria);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
    
    /**
     * Test the {@link UserService#findUsers(User)} method for the scenario 
     * when there is more than one matching user but the caps are different
     */
    @Test
    @Rollback
    public final void testFindUsersIgnoreCaps()
    {
        User user2 = new User("username2", "password", "firstName", "lastName",
                "test2@test.com", true, true, true, true);
        userService.saveOrUpdate(user2);
        
        User criteria = new User();
        criteria.setUsername("USERNAME2");
        final List<User> users = userService.findUsers(criteria);
        assertEquals(1, users.size());
        assertTrue(users.contains(user2));
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
