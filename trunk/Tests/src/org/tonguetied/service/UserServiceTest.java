package org.tonguetied.service;

import java.util.List;

import org.junit.Test;
import org.tonguetied.domain.User;


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
     * Test method for {@link org.tonguetied.service.UserService#getUser(String)}.
     */
    public final void testGetUserByNameWithNoMatch() {
        User actual = userService.getUser("unknown");
        
        assertNull(actual);
    }

    /**
     * Test method for {@link org.tonguetied.service.UserService#getUser(Long)}.
     */
    public final void testGetUserByIdWithNoMatch() {
        User actual = userService.getUser(-1L);
        
        assertNull(actual);
    }

    /**
     * Test method for {@link org.tonguetied.service.UserService#getUser(String)}.
     */
    public final void testGetUserByUserName() {
        User actual = userService.getUser(user1.getUsername());
        
        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link org.tonguetied.service.UserService#getUser(Long)}.
     */
    public final void testGetUserById() {
        User actual = userService.getUser(user1.getId());
        
        assertEquals(user1, actual);
    }

    /**
     * Test method for {@link org.tonguetied.service.UserServiceImpl#getUsers()}.
     */
    @Test
    public final void testGetUsers() {
        List<User> users = userService.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user1));
    }
    
    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
