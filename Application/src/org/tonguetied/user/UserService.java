package org.tonguetied.usermanagement;

import java.util.List;



/**
 * This interface defines the events used for a user management.
 * 
 * @author bsion
 *
 */
public interface UserService {

    /**
     * Create or update a new user in the system.
     * 
     * @param user the {@link User} to persist
     */
    void saveOrUpdate(User user);
    
    /**
     * Retrieve the user by business key, ie the {@link User#getUsername()}.
     * 
     * @param username the business key to search for
     * @return the {@link User} matching the <code>username</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final String username);
    
    /**
     * Retrieve the user by the unique identifier.
     * 
     * @param id the unique key to search for
     * @return the {@link User} matching the <code>id</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final Long id);
    
    /**
     * Retrieve a list of all {@link User}s.
     * 
     * @return a list of all {@link User}s in the system
     */
    List<User> getUsers();
}
