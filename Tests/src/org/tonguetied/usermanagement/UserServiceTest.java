package org.tonguetied.usermanagement;

import java.util.List;

import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * @author bsion
 *
 */
public class UserServiceTest extends AbstractServiceTest {
    private User user1;
    private UserService userService;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        user1 = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true); 

        userService.saveOrUpdate(user1);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.UserService#getUser(String)}.
     */
    public final void testGetUserByNameWithNoMatch() {
        User actual = userService.getUser("unknown");
        
        assertNull(actual);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.UserService#getUser(Long)}.
     */
    public final void testGetUserByIdWithNoMatch() {
        User actual = userService.getUser(-1L);
        
        assertNull(actual);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.UserService#getUser(String)}.
     */
    public final void testGetUserByUserName() {
        User actual = userService.getUser(user1.getUsername());
        
        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.UserService#getUser(Long)}.
     */
    public final void testGetUserById() {
        User actual = userService.getUser(user1.getId());
        
        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link org.tonguetied.usermanagement.UserServiceImpl#getUsers()}.
     */
    @Test
    public final void testGetUsers() {
        List<User> users = userService.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user1));
    }
    
    @Test(expected=AuthenticationException.class)
    @ExpectedException(value=AuthenticationException.class)
    public final void testChangePasswordWithInvalidOldPassword()
    {
        userService.changePassword(user1, "invalid", "newPassword");
    }
    
    @Test
    public final void testChangePassword()
    {
        final String newPassword = "new";
        final String oldPassword = "password";
        
        userService.changePassword(user1, oldPassword, newPassword);
        
        User actual = userService.getUser(user1.getId());
        final String[] components = 
            UserTestUtils.demergePasswordAndSalt(actual.getPassword());
        assertEquals(newPassword, components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }
    
    
    @Test
    public final void testChangePasswordToNull()
    {
        final String oldPassword = "password";
        
        userService.changePassword(user1, oldPassword, null);
        
        User actual = userService.getUser(user1.getId());
        final String[] components = 
            UserTestUtils.demergePasswordAndSalt(actual.getPassword());
        assertEquals("", components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }
    
    @Test
    public final void testEncodePassword()
    {
        final String newPassword = "new";
        userService.encodePassword(user1, newPassword);
        final String[] components = 
            UserTestUtils.demergePasswordAndSalt(user1.getPassword());
        assertEquals(newPassword, components[0]);
        assertEquals(user1.getUsername(), components[1]);
    }
    
    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
