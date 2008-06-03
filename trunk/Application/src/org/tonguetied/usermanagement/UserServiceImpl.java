package org.tonguetied.usermanagement;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.providers.encoding.PasswordEncoder;


/**
 * @author bsion
 *
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    private static final Logger logger = 
        Logger.getLogger(UserServiceImpl.class);

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


    /**
     * {@inheritDoc}
     * When the user is saved then a unidirectional encryption of the password 
     * is made. This involves making a hash of the password.
     */
    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            if (logger.isDebugEnabled()) logger.debug("endcoding user password");
            final String encoded = 
                passwordEncoder.encodePassword(user.getPassword(), user.getUsername());
            user.setPassword(encoded);
        }
        userRepository.saveOrUpdate(user);
    }

    /**
     * 
     * @param userRepository the DAO used interact with the storage mechanism 
     * for user data
     */
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param passwordEncoder the encoder used to encrypt the user's password
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
