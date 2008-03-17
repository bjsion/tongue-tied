package org.tonguetied.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tonguetied.domain.User;


/**
 * @author bsion
 *
 */
public class UserServiceStub implements UserService {
    Map<String, User> users = new HashMap<String, User>();

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#saveOrUpdate(org.tonguetied.domain.User)
     */
    public void saveOrUpdate(User user) {
        users.put(user.getUsername(), user);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.String)
     */
    public User getUser(final String username) {
        return users.get(username);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.Long)
     */
    public User getUser(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUsers()
     */
    public List<User> getUsers() {
        return (List<User>) users.values();
    }
}
