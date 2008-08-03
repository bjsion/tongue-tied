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
