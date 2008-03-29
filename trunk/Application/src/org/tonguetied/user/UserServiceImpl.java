package org.tonguetied.usermanagement;

import java.util.List;


/**
 * @author bsion
 *
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.String)
     */
    public User getUser(final String username) {
        return userRepository.getUser(username);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.Long)
     */
    public User getUser(final Long id) {
        return userRepository.getUser(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUsers()
     */
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#saveOrUpdate(org.tonguetied.domain.User)
     */
    public void saveOrUpdate(User user) {
        userRepository.saveOrUpdate(user);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
