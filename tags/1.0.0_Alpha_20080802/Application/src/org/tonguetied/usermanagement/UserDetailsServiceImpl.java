package org.tonguetied.usermanagement;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;


/**
 * @author bsion
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        if (StringUtils.isBlank(username))
            throw new UsernameNotFoundException("unknown username " + username);
        
        User user = userRepository.getUser(username);
        
        if (user == null)
            throw new UsernameNotFoundException("unknown username " + username);
        
        if (user.getUserRights() == null || user.getUserRights().isEmpty())
            throw new UsernameNotFoundException("User has no authorities");
        
        return user;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
